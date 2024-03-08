import matplotlib.pyplot as plt
import math

# Данная выборка
data = [20, 19, 22, 24, 21, 18, 23, 17, 20, 16, 15, 23, 21, 24, 21, 18, 23, 21, 19, 20, 24, 21, 20, 18, 17, 22, 20, 16, 22, 18, 20, 17, 21, 17, 19, 20, 20, 21, 18, 22, 23, 21, 25, 22, 20, 19, 21, 24, 23, 21, 19, 22, 21, 19, 20, 23, 22, 25, 21, 21]

# 1. Дискретный вариационный ряд
# Считаем частоты значений
value_counts = {val: data.count(val) for val in set(data)}

sorted_values = sorted(value_counts.items())

values, frequencies = zip(*sorted_values)

relative_frequencies = [freq / sum(frequencies) for freq in frequencies]

plt.figure(figsize=(14, 10))

plt.subplot(2, 2, 1)
plt.bar(values, frequencies)
plt.xlabel('Значения')
plt.ylabel('Частоты')
plt.title('Дискретный вариационный ряд')

plt.subplot(2, 2, 2)
plt.plot(values, relative_frequencies, marker='o', linestyle='-', color='b')
plt.xlabel('Значения')
plt.ylabel('Относительные частоты')
plt.title('Полигон распределения относительных частот')

# 3. Кумулята и огива
cumulative_frequencies = [sum(relative_frequencies[:i+1]) for i in range(len(relative_frequencies))]

plt.subplot(2, 2, 3)
plt.plot(values, cumulative_frequencies, marker='o', linestyle='-', color='r')
plt.xlabel('Значения')
plt.ylabel('Кумулятивные частоты')
plt.title('Огива')

# 4. Эмпирическая функция распределения
plt.subplot(2, 2, 4)
plt.step(values + (max(values) + 1,), cumulative_frequencies + [1], where='post')
plt.xlabel('Значения')
plt.ylabel('ЭФР')
plt.title('Эмпирическая функция распределения')

plt.tight_layout()
plt.show()

# 5. Найти числовые характеристики
mean = sum([value * freq for value, freq in zip(values, frequencies)]) / sum(frequencies)
variance = sum([((value - mean) ** 2) * freq for value, freq in zip(values, frequencies)]) / sum(frequencies)
std_deviation = math.sqrt(variance)

# коэф асимметрии
skewness = (sum((x - mean) ** 3 for x in data) / len(data)) / std_deviation ** 3

# эксцесс
kurtosis = (sum((x - mean) ** 4 for x in data) / len(data)) / std_deviation ** 4 - 3

print(mean, variance, std_deviation, skewness, kurtosis)