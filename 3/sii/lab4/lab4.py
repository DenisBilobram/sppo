
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

df = pd.read_csv('diabetes.csv')

stats_after = df.describe()
print("\nСтатистические показатели:")
print(stats_after)

numeric_cols = df.select_dtypes(include=[np.number]).columns.tolist()
df[numeric_cols].hist(figsize=(12, 8))
plt.tight_layout()
plt.show()

fig = plt.figure(figsize=(10, 8))
ax = fig.add_subplot(111, projection='3d')

diabetic = df[df['Outcome'] == 1]
non_diabetic = df[df['Outcome'] == 0]

ax.scatter(diabetic['Glucose'], diabetic['BMI'], diabetic['Age'], c='r', marker='o', label='Diabetic')
ax.scatter(non_diabetic['Glucose'], non_diabetic['BMI'], non_diabetic['Age'], c='b', marker='^', label='Non-Diabetic')

ax.set_xlabel('Glucose')
ax.set_ylabel('BMI')
ax.set_zlabel('Age')
plt.legend()
plt.show()

columns_with_zero = ['Glucose', 'BloodPressure', 'SkinThickness', 'Insulin', 'BMI']
df[columns_with_zero] = df[columns_with_zero].replace(0, np.nan)

print("Количество пропущенных значений до заполнения:")
print(df.isnull().sum())


for col in columns_with_zero:
    median = df[col].median()
    df[col] = df[col].fillna(median)


print("\nКоличество пропущенных значений после заполнения:")
print(df.isnull().sum())

features = df.columns.drop('Outcome')

for col in features:
    mean = df[col].mean()
    std = df[col].std()
    df[col] = (df[col] - mean) / std

def euclidean_distance(x1, x2):
    return np.sqrt(np.sum((x1 - x2) ** 2))

def k_nearest_neighbors(X_train, y_train, X_test_point, k):
    distances = []
    for i in range(len(X_train)):
        dist = euclidean_distance(X_train[i], X_test_point)
        distances.append((dist, y_train[i]))

    distances.sort(key=lambda x: x[0])
    neighbors = distances[:k]

    classes = [neighbor[1] for neighbor in neighbors]
    prediction = max(set(classes), key=classes.count)
    return prediction

def predict_knn(X_train, y_train, X_test, k):
    predictions = []
    for x_test_point in X_test:
        prediction = k_nearest_neighbors(X_train, y_train, x_test_point, k)
        predictions.append(prediction)
    return predictions


X = df.drop('Outcome', axis=1).values
y = df['Outcome'].values

from sklearn.model_selection import train_test_split

X_train_full, X_test_full, y_train_full, y_test_full = train_test_split(X, y, test_size=0.3, random_state=42)

random_features_indices = np.random.choice(range(X.shape[1]), size=5, replace=False)
print(f"Случайно выбранные индексы признаков: {random_features_indices}")

X_train_model1 = X_train_full[:, random_features_indices]
X_test_model1 = X_test_full[:, random_features_indices]

fixed_features = ['Glucose', 'BMI', 'Age']
fixed_features_indices = [df.columns.get_loc(col) for col in fixed_features]
print(f"Индексы выбранных признаков: {fixed_features_indices}")

X_train_model2 = X_train_full[:, fixed_features_indices]
X_test_model2 = X_test_full[:, fixed_features_indices]


from sklearn.metrics import confusion_matrix, accuracy_score

def evaluate_model(y_true, y_pred):
    cm = confusion_matrix(y_true, y_pred)
    acc = accuracy_score(y_true, y_pred)
    return cm, acc

k_values = [3, 5, 10]

for k in k_values:
    print(f"\nМодель 1: k = {k}")
    y_pred_model1 = predict_knn(X_train_model1, y_train_full, X_test_model1, k)
    cm, acc = evaluate_model(y_test_full, y_pred_model1)
    print(f"Точность: {acc:.4f}")
    print("Матрица ошибок:")
    print(cm)


for k in k_values:
    print(f"\nМодель 2: k = {k}")
    y_pred_model2 = predict_knn(X_train_model2, y_train_full, X_test_model2, k)
    cm, acc = evaluate_model(y_test_full, y_pred_model2)
    print(f"Точность: {acc:.4f}")
    print("Матрица ошибок:")
    print(cm)
