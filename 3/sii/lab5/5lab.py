import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

# Определение класса узла дерева
class DecisionTreeNode:
    def __init__(self, feature=None, children=None, *, value=None, prob=None):
        self.feature = feature          # Признак для разделения
        self.children = children or {}  # Словарь: значение признака -> дочерний узел
        self.value = value              # Классовое значение (для листа)
        self.prob = prob                # Вероятность класса 1 (для листа)

# Функция для вычисления энтропии
def entropy(y):
    proportions = np.bincount(y) / len(y)
    return -np.sum([p * np.log2(p) for p in proportions if p > 0])

# Функция для поиска лучшего разделения (многовариантное)
def best_split(X, y):
    best_feature = None
    best_gain = -1
    current_entropy = entropy(y)
    
    for feature in X.columns:
        unique_values = X[feature].unique()
        # Для многовариантного разделения
        subsets = [y[X[feature] == val] for val in unique_values]
        # Вычисляем энтропию после разделения
        weighted_entropy = sum((len(subset) / len(y)) * entropy(subset) for subset in subsets)
        gain = current_entropy - weighted_entropy
        if gain > best_gain:
            best_gain = gain
            best_feature = feature
    
    return best_feature

# Функция для построения дерева рекурсивно
def build_tree(X, y, depth=0, max_depth=10):
    if depth >= max_depth or len(set(y)) == 1:
        leaf_value = np.bincount(y).argmax()
        leaf_prob = np.mean(y)
        return DecisionTreeNode(value=leaf_value, prob=leaf_prob)
    
    # Найти лучшее разделение
    feature = best_split(X, y)
    if feature is None:
        leaf_value = np.bincount(y).argmax()
        leaf_prob = np.mean(y)
        return DecisionTreeNode(value=leaf_value, prob=leaf_prob)
    
    # Создать узел и разделить данные на основе уникальных значений признака
    unique_values = X[feature].unique()
    children = {}
    
    for val in unique_values:
        subset_X = X[X[feature] == val].drop(columns=[feature])
        subset_y = y[X[feature] == val]
        # Рекурсивное построение поддеревьев
        child_node = build_tree(subset_X, subset_y, depth + 1, max_depth)
        children[val] = child_node
    
    return DecisionTreeNode(feature=feature, children=children)

# Функция для предсказания на одном примере
def predict_tree(node, x):
    if node.value is not None:
        return node.value
    feature_val = x.get(node.feature)
    child_node = node.children.get(feature_val)
    if child_node:
        return predict_tree(child_node, x)
    else:
        # Если значение признака не встречалось в обучении, вернуть наиболее частый класс
        return 1 if node.prob >= 0.5 else 0

# Функция для предсказания на наборе данных
def predict(X, tree):
    return X.apply(lambda x: predict_tree(tree, x), axis=1).astype(int)

# Функция для получения вероятностей
def predict_proba(node, x):
    if node.prob is not None:
        return node.prob
    feature_val = x.get(node.feature)
    child_node = node.children.get(feature_val)
    if child_node:
        return predict_proba(child_node, x)
    else:
        # Если значение признака не встречалось в обучении, вернуть среднюю вероятность
        return node.prob

def get_probabilities(X, tree):
    return X.apply(lambda x: predict_proba(tree, x), axis=1)

# Функция для оценки модели
def evaluate(y_true, y_pred):
    TP = np.sum((y_true == 1) & (y_pred == 1))
    TN = np.sum((y_true == 0) & (y_pred == 0))
    FP = np.sum((y_true == 0) & (y_pred == 1))
    FN = np.sum((y_true == 1) & (y_pred == 0))
    
    accuracy = (TP + TN) / (TP + TN + FP + FN)
    precision = TP / (TP + FP) if (TP + FP) != 0 else 0
    recall = TP / (TP + FN) if (TP + FN) != 0 else 0
    
    return accuracy, precision, recall

# Функция для расчета AUC с использованием метода трапеций
def calculate_auc(x, y):
    # Сортируем точки по x
    sorted_indices = np.argsort(x)
    x_sorted = np.array(x)[sorted_indices]
    y_sorted = np.array(y)[sorted_indices]
    auc = 0.0
    for i in range(1, len(x_sorted)):
        auc += (x_sorted[i] - x_sorted[i - 1]) * (y_sorted[i] + y_sorted[i - 1]) / 2
    return auc

# Подготовка данных
df = pd.read_csv('DATA.csv')
threshold = 3
df['is_successful'] = df['GRADE'].apply(lambda x: 1 if x >= threshold else 0)

# Отбор признаков
feature_columns = [str(i) for i in range(1, 31)]
n = len(feature_columns)
num_features = int(np.sqrt(n))  # 5
np.random.seed(42)  # Для воспроизводимости
selected_features = np.random.choice(feature_columns, size=num_features, replace=False)
print("Выбранные признаки:", selected_features)

# Создание выбранного набора данных
selected_data = df[list(selected_features) + ['is_successful']]

# Построение дерева
tree = build_tree(selected_data[selected_features], selected_data['is_successful'], max_depth=5)

# Предсказание
predictions = predict(selected_data[selected_features], tree)

# Оценка модели
y_true = selected_data['is_successful']
accuracy, precision, recall = evaluate(y_true, predictions)
print(f"\nAccuracy: {accuracy:.2f}, Precision: {precision:.2f}, Recall: {recall:.2f}")

# Получение вероятностей
probabilities = get_probabilities(selected_data[selected_features], tree)
print("\nВероятности:\n", probabilities)

# Определение порогов
thresholds = np.linspace(0, 1, 100)

# Инициализация списков для ROC и PR
tpr_list = []
fpr_list = []
precision_list = []
recall_list = []

for threshold_val in thresholds:
    y_pred_thresh = (probabilities >= threshold_val).astype(int)
    TP = np.sum((y_true == 1) & (y_pred_thresh == 1))
    TN = np.sum((y_true == 0) & (y_pred_thresh == 0))
    FP = np.sum((y_true == 0) & (y_pred_thresh == 1))
    FN = np.sum((y_true == 1) & (y_pred_thresh == 0))
    
    TPR = TP / (TP + FN) if (TP + FN) != 0 else 0  # Recall
    FPR = FP / (FP + TN) if (FP + TN) != 0 else 0
    Precision_val = TP / (TP + FP) if (TP + FP) != 0 else 0
    Recall_val = TPR
    
    tpr_list.append(TPR)
    fpr_list.append(FPR)
    precision_list.append(Precision_val)
    recall_list.append(Recall_val)

# Построение ROC-Кривой
plt.figure(figsize=(12, 5))
plt.subplot(1, 2, 1)
plt.plot(fpr_list, tpr_list, label='ROC Curve')
plt.plot([0, 1], [0, 1], 'k--', label='Random Classifier')
plt.xlabel('False Positive Rate')
plt.ylabel('True Positive Rate')
plt.title('ROC-Кривая')
plt.legend()

# Построение PR-Кривой
plt.subplot(1, 2, 2)
plt.plot(recall_list, precision_list, label='PR Curve')
plt.xlabel('Recall')
plt.ylabel('Precision')
plt.title('Precision-Recall Кривая')
plt.legend()

plt.tight_layout()
plt.show()

# Расчет AUC
auc_roc = calculate_auc(fpr_list, tpr_list)
auc_pr = calculate_auc(recall_list, precision_list)
print(f"\nAUC-ROC: {auc_roc:.2f}, AUC-PR: {auc_pr:.2f}")
