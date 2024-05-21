import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from scipy import stats

# Данные
data = np.array([
    8, 10, 11, 17, 15, 12, 15, 14, 16, 18,
    15, 18, 13, 14, 16, 17, 8, 11, 14, 10,
    16, 14, 18, 11, 11, 15, 15, 10, 12, 14,
    17, 12, 14, 14, 9, 12, 9, 13, 14, 10,
    12, 10, 11, 13, 11, 13, 14, 14, 12, 13,
    12, 14, 14, 12, 16, 15, 13, 13, 15, 12,
    13, 10, 12, 14, 15, 15, 19, 11, 14, 11,
    11, 11, 12, 14, 15, 13, 11, 17, 18, 13,
    12, 12, 12, 9, 10, 13, 9, 16, 14, 13,
    16, 13, 15, 12, 12, 17, 13, 15, 16, 14
])

# Шаг 1: Размах варьирования
R = np.max(data) - np.min(data)

# Шаг 2: Частоты
values, frequencies = np.unique(data, return_counts=True)

freq_series = pd.Series(frequencies, index=values)

# Шаг 3: Интервальные ряды
intervals = np.histogram_bin_edges(data, bins='sturges')
freq_intervals, _ = np.histogram(data, bins=intervals)
relative_freq_intervals = freq_intervals / len(data)

print(intervals, freq_intervals, relative_freq_intervals)

# Шаг 4: Полигон и гистограмма относительных частот
plt.figure(figsize=(10, 6))
plt.hist(data, bins=intervals, density=True, alpha=0.6, color='g', edgecolor='black')
plt.title('Гистограмма относительных частот')
plt.xlabel('Интервалы')
plt.ylabel('Относительная частота')
plt.grid(True)
plt.savefig('histogram.png')
plt.show()

# Полигон
plt.figure(figsize=(10, 6))
plt.plot(intervals[:-1], relative_freq_intervals, marker='o', linestyle='-', color='b')
plt.title('Полигон относительных частот')
plt.xlabel('Интервалы')
plt.ylabel('Относительная частота')
plt.grid(True)
plt.savefig('polygon.png')
plt.show()

# Шаг 5: Эмпирическая функция распределения
ecdf = stats.cumfreq(data, numbins=len(values))

plt.figure(figsize=(10, 6))
plt.step(ecdf.lowerlimit + np.linspace(0, ecdf.binsize * ecdf.cumcount.size, ecdf.cumcount.size), ecdf.cumcount / len(data), where='mid', color='r')
plt.title('Эмпирическая функция распределения')
plt.xlabel('Значения')
plt.ylabel('ЭФР')
plt.grid(True)
plt.savefig('ecdf.png')
plt.show()

# Шаг 6: Математическое ожидание, дисперсия, СКО
mean_X = np.mean(data)
var_X = np.var(data, ddof=1)
std_X = np.std(data, ddof=1)

# Подготовим данные для вставки в Word
result = f"""
Размах варьирования R: {R}

Статистический ряд распределения частот:
{freq_series.to_string()}

Интервальные статистические ряды частот и относительных частот:
Интервалы: {intervals}
Частоты: {freq_intervals}
Относительные частоты: {relative_freq_intervals}

Математическое ожидание M(X): {mean_X}
Дисперсия D(X): {var_X}
Среднее квадратическое отклонение σ(X): {std_X}
"""

mean_X = np.mean(data)
var_X = np.var(data, ddof=1)
std_X = np.std(data, ddof=1)

result += f"""

Точечные оценки параметров нормального распределения:

Математическое ожидание (оценка): \\[
\\hat{{\\mu}} = \\frac{{1}}{{n}} \\sum_{{i=1}}^n X_i = {mean_X}
\\]

Дисперсия (оценка): \\[
\\hat{{\\sigma^2}} = \\frac{{1}}{{n-1}} \\sum_{{i=1}}^n (X_i - \\hat{{\\mu}})^2 = {var_X}
\\]

Среднеквадратическое отклонение (оценка): \\[
\\hat{{\\sigma}} = \\sqrt{{\\hat{{\\sigma^2}}}} = {std_X}
\\]

Функция распределения: \\[
F(x) = \\frac{{1}}{{\\sqrt{{2\\pi\\sigma^2}}}} \\exp\\left(-\\frac{{(x - \\mu)^2}}{{2\\sigma^2}}\\right)
\\]

Плотность вероятности: \\[
f(x) = \\frac{{1}}{{\\sigma\\sqrt{{2\\pi}}}} \\exp\\left(-\\frac{{(x - \\mu)^2}}{{2\\sigma^2}}\\right)
\\]
"""

# Количество наблюдений
n = len(data)

# Выборочная дисперсия
D_v_X = np.var(data, ddof=1)

# Вычисление σδ
sigma_delta = np.sqrt((n / (n - 1)) * D_v_X)
sigma_delta

# Добавление результата в текстовый файл
result = f"\n\nВычисление σδ с использованием выборочной дисперсии:\n"
result += f"σδ = √((n / (n - 1)) * D(X)) = √(({n} / ({n} - 1)) * {D_v_X}) = {sigma_delta}"

# Сохранение результата в текстовый файл с кодировкой utf-8
with open('result.txt', 'w', encoding='utf-8') as file:
    file.write(result)


