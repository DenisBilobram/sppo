import math
import matplotlib.pyplot as plt

data = [111, 85, 85, 91, 101, 109, 86, 102, 111, 98, 105, 85, 112, 109, 115, 99, 105, 111, 94, 107, 99, 107, 125, 89, 104, 113, 105, 88, 103, 97, 115, 109, 89, 108, 107, 97, 106, 107, 96, 108, 109, 139, 116, 117, 103, 127, 119, 118, 125, 105]

# 1. Интервальный вариационный ряд
min_value = min(data)
max_value = max(data)
n_intervals = round(1 + 3.322 * math.log10(len(data)))  # Формула Стерджесса
bin_width = (max_value - min_value) / n_intervals
bins = [min_value + i * bin_width for i in range(n_intervals + 1)]

# Расчет частот для интервалов
hist = [0] * n_intervals
for d in data:
    for i in range(n_intervals):
        if d >= bins[i] and d < bins[i + 1]:
            hist[i] += 1
            break

plt.figure(figsize=(12, 6))
plt.subplot(1, 2, 1)
# 2. Гистограмма частот
plt.bar(range(n_intervals), hist, width=1, edgecolor='black', tick_label=[f"{bins[i]:.2f}-{bins[i+1]:.2f}" for i in range(n_intervals)])
plt.xticks(rotation=45)
plt.xlabel('Интервалы')
plt.ylabel('Частота')
plt.title('Гистограмма частот')
plt.grid(True)

# 3. Кумулятивный ряд и 4. Эмпирическая функция распределения
plt.subplot(1, 2, 2)
cumulative_hist = [sum(hist[:i+1]) for i in range(n_intervals)]
cumulative_relative_hist = [ch / len(data) for ch in cumulative_hist]
plt.step(range(n_intervals), cumulative_relative_hist, where='post', label='Эмпирическая функция распределения')
plt.xticks(range(n_intervals), [f"{bins[i]:.2f}" for i in range(n_intervals)], rotation=45)
plt.xlabel('Интервалы')
plt.ylabel('Кумулятивная относительная частота')
plt.title('Кумулятивный ряд и ЭФР')
plt.legend()
plt.grid(True)

plt.show()

# 5. Найти числовые характеристики
mean = sum(data) / len(data)
variance = sum([(x - mean) ** 2 for x in data]) / len(data)
std_deviation = math.sqrt(variance)

# коэф асимметрии
skewness = (sum((x - mean) ** 3 for x in data) / len(data)) / std_deviation ** 3

# эксцесс
kurtosis = (sum((x - mean) ** 4 for x in data) / len(data)) / std_deviation ** 4 - 3

print(mean, variance, std_deviation, skewness, kurtosis)