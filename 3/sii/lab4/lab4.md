## 1. Загрузка и Исследование Данных
``` python
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

ax.set_xlabel('Glucose')
ax.set_ylabel('BMI')
ax.set_zlabel('Age')
plt.legend()
plt.show()
```
```
Статистические показатели:
       Pregnancies     Glucose  BloodPressure  SkinThickness     Insulin         BMI    Pedigree         Age     Outcome
count   768.000000  768.000000     768.000000     768.000000  768.000000  768.000000  768.000000  768.000000  768.000000
mean      3.845052  120.894531      69.105469      20.536458   79.799479   31.992578    0.471876   33.240885    0.348958
std       3.369578   31.972618      19.355807      15.952218  115.244002    7.884160    0.331329   11.760232    0.476951
min       0.000000    0.000000       0.000000       0.000000    0.000000    0.000000    0.078000   21.000000    0.000000
25%       1.000000   99.000000      62.000000       0.000000    0.000000   27.300000    0.243750   24.000000    0.000000
50%       3.000000  117.000000      72.000000      23.000000   30.500000   32.000000    0.372500   29.000000    0.000000
75%       6.000000  140.250000      80.000000      32.000000  127.250000   36.600000    0.626250   41.000000    1.000000
max      17.000000  199.000000     122.000000      99.000000  846.000000   67.100000    2.420000   81.000000    1.000000
```
![](/img/Figure_2.png)

## 2. Предварительная Обработка Данных
``` python
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
```
```
Количество пропущенных значений до заполнения:
Pregnancies        0
Glucose            5
BloodPressure     35
SkinThickness    227
Insulin          374
BMI               11
Pedigree           0
Age                0
Outcome            0
dtype: int64

Количество пропущенных значений после заполнения:
Pregnancies      0
Glucose          0
BloodPressure    0
SkinThickness    0
Insulin          0
BMI              0
Pedigree         0
Age              0
Outcome          0
dtype: int64
```

## 3. 3D Визуализация Признаков
```
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
```
![](/img/Figure_1.png)

## 4. Реализация Метода k-Ближайших Соседей
``` python
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
```

## 5. Построение Двух Моделей с Различными Наборами Признаков
``` python
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
```
```
Случайно выбранные индексы признаков: [5 2 1 7 0]
Индексы выбранных признаков: [1, 5, 7]
```

## 6. Оценка Моделей на Тестовом Наборе при Различных Значениях k
``` python
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
```
```
Модель 1: k = 3
Точность: 0.7446
Матрица ошибок:
[[119  32]
 [ 27  53]]

Модель 1: k = 5
Точность: 0.7619
Матрица ошибок:
[[121  30]
 [ 25  55]]

Модель 1: k = 10
Точность: 0.7316
Матрица ошибок:
[[127  24]
 [ 38  42]]

Модель 2: k = 3
Точность: 0.6797
Матрица ошибок:
[[112  39]
 [ 35  45]]

Модель 2: k = 5
Точность: 0.7316
Матрица ошибок:
[[121  30]
 [ 32  48]]

Модель 2: k = 10
Точность: 0.7359
Матрица ошибок:
[[126  25]
 [ 36  44]]
```