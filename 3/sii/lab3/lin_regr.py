
import csv
import numpy as np
import matplotlib.pyplot as plt
import math
import random

data = []
with open('student_performance.csv', 'r') as csvfile:
    csvreader = csv.reader(csvfile)
    headers = next(csvreader)
    for row in csvreader:
        data.append(row)

print("Заголовки столбцов:")
print(headers)

print("\nПервые 5 строк данных:")
for row in data[:5]:
    print(row)


data_dict = {header: [] for header in headers}

for row in data:
    for i, value in enumerate(row):
        data_dict[headers[i]].append(value)

numeric_columns = ['Hours Studied', 'Previous Scores', 'Sleep Hours', 'Sample Question Papers Practiced', 'Performance Index']

for col in numeric_columns:
    data_dict[col] = [float(x) if x != '' else None for x in data_dict[col]]

categorical_columns = [col for col in headers if col not in numeric_columns]

def compute_statistics(values):

    values = [x for x in values if x is not None]
    count = len(values)
    mean = sum(values) / count
    variance = sum((x - mean) ** 2 for x in values) / count
    std_dev = math.sqrt(variance)
    min_value = min(values)
    max_value = max(values)
    sorted_values = sorted(values)

    def percentile(p):
        k = (count - 1) * p / 100
        f = math.floor(k)
        c = math.ceil(k)
        if f == c:
            return sorted_values[int(k)]
        else:
            return sorted_values[f] * (c - k) + sorted_values[c] * (k - f)

    quantiles = {
        '25%': percentile(25),
        '50%': percentile(50),
        '75%': percentile(75)
    }
    return {
        'count': count,
        'mean': mean,
        'std_dev': std_dev,
        'min': min_value,
        'max': max_value,
        'quantiles': quantiles
    }

statistics = {}
for col in numeric_columns:
    stats = compute_statistics(data_dict[col])
    statistics[col] = stats
    print(f"Статистика для {col}:")
    print(stats)
    print()

for col in numeric_columns:
    values = [x for x in data_dict[col] if x is not None]
    if col == 'Hours Studied':
        bins = 9
    elif col == 'Previous Scores' or col == 'Sample Question Papers Practiced' or col == 'Performance Index':
        bins = 10 
    elif col == 'Sleep Hours':
        bins = 6

    plt.hist(values, bins, edgecolor='black', alpha=0.7)
    plt.title(f'Гистограмма для {col}')
    plt.xlabel(col)
    plt.ylabel('Частота')
    plt.grid(axis='y', linestyle='--', alpha=0.7)
    plt.show()

for col in numeric_columns:
    values = [x for x in data_dict[col] if x is not None]
    mean_value = sum(values) / len(values)
    data_dict[col] = [x if x is not None else mean_value for x in data_dict[col]]

data_dict['Extracurricular Activities'] = [1 if x == 'Yes' else 0 for x in data_dict['Extracurricular Activities']]

for col in numeric_columns:
    if col != 'Performance Index':
        values = data_dict[col]
        mean = sum(values) / len(values)
        std_dev = math.sqrt(sum((x - mean) ** 2 for x in values) / len(values))
        data_dict[col] = [(x - mean) / std_dev for x in values]

dataset = []
num_samples = len(data_dict['Performance Index'])
for i in range(num_samples):
    record = {col: data_dict[col][i] for col in headers}
    dataset.append(record)

random.shuffle(dataset)

train_size = int(0.7 * num_samples)
train_set = dataset[:train_size]
test_set = dataset[train_size:]

def get_features_targets(data, feature_names):
    X = []
    y = []
    for record in data:
        features = [1.0] + [record[feature] for feature in feature_names]
        X.append(features)
        y.append(record['Performance Index'])
    return X, y


for record in train_set + test_set:
    record['Study_Papers_Product'] = record['Hours Studied'] * record['Sample Question Papers Practiced']

features_model1 = ['Hours Studied', 'Previous Scores', 'Extracurricular Activities', 'Sleep Hours', 'Sample Question Papers Practiced']
features_model2 = ['Hours Studied', 'Previous Scores']
features_model3 = ['Hours Studied', 'Sample Question Papers Practiced', 'Study_Papers_Product']

def train_linear_regression(X, y):
    X = np.array(X)
    y = np.array(y)
    theta = np.linalg.inv(X.T @ X) @ X.T @ y
    return theta


X_train1, y_train1 = get_features_targets(train_set, features_model1)
theta1 = train_linear_regression(X_train1, y_train1)

X_train2, y_train2 = get_features_targets(train_set, features_model2)
theta2 = train_linear_regression(X_train2, y_train2)

X_train3, y_train3 = get_features_targets(train_set, features_model3)
theta3 = train_linear_regression(X_train3, y_train3)

def predict(X, theta):
    X = np.array(X)
    y_pred = X @ theta
    return y_pred

def compute_r2_score(y_true, y_pred):
    y_mean = sum(y_true) / len(y_true)
    ss_total = sum((y_i - y_mean) ** 2 for y_i in y_true)
    ss_res = sum((y_i - y_hat_i) ** 2 for y_i, y_hat_i in zip(y_true, y_pred))
    r2 = 1 - (ss_res / ss_total)
    return r2

X_test1, y_test1 = get_features_targets(test_set, features_model1)
y_pred1 = predict(X_test1, theta1)
r2_model1 = compute_r2_score(y_test1, y_pred1)
print(f"R² для Модели 1: {r2_model1:.4f}")

X_test2, y_test2 = get_features_targets(test_set, features_model2)
y_pred2 = predict(X_test2, theta2)
r2_model2 = compute_r2_score(y_test2, y_pred2)
print(f"R² для Модели 2: {r2_model2:.4f}")

X_test3, y_test3 = get_features_targets(test_set, features_model3)
y_pred3 = predict(X_test3, theta3)
r2_model3 = compute_r2_score(y_test3, y_pred3)
print(f"R² для Модели 3: {r2_model3:.4f}")

models_r2 = {
    'Модель 1': r2_model1,
    'Модель 2': r2_model2,
    'Модель 3': r2_model3
}

best_model_name = max(models_r2, key=models_r2.get)
best_r2 = models_r2[best_model_name]
print(f"\nЛучшая модель: {best_model_name} с R² = {best_r2:.4f}")

print("\nВыводы:")
if best_model_name == 'Модель 3':
    print("Введение синтетического признака улучшило производительность модели.")
else:
    print(f"{best_model_name} показала наилучший результат без синтетического признака.")

print("Это указывает на то, что признаки, использованные в этой модели, наиболее значимы для предсказания 'Performance Index'.")


if best_model_name == 'Модель 1':
    y_test = y_test1
    y_pred = y_pred1
elif best_model_name == 'Модель 2':
    y_test = y_test2
    y_pred = y_pred2
else:
    y_test = y_test3
    y_pred = y_pred3

plt.scatter(y_test, y_pred)
plt.xlabel('Фактический Performance Index')
plt.ylabel('Предсказанный Performance Index')
plt.title(f'Фактический vs Предсказанный Performance Index ({best_model_name})')
plt.plot([min(y_test), max(y_test)], [min(y_test), max(y_test)], 'r--') 
plt.show()