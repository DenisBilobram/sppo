import math

# Данные
intervals = [(83, 108.5), (108.5, 134), (134, 159.5), (159.5, 185), (185, 210.5), 
             (210.5, 236), (236, 261.5), (261.5, 287)]
frequencies = [1, 4, 26, 34, 61, 50, 20, 4]
alpha = 0.02

# Вычисление среднего значения
total_count = sum(frequencies)
mid_points = [(a + b) / 2 for a, b in intervals]
mean = sum(f * m for f, m in zip(frequencies, mid_points)) / total_count

# Вычисление стандартного отклонения
variance = sum(f * (m - mean) ** 2 for f, m in zip(frequencies, mid_points)) / (total_count - 1)
std_dev = math.sqrt(variance)

# Функция стандартного нормального распределения
def standard_normal_cdf(x):
    return (1 + math.erf(x / math.sqrt(2))) / 2

# Вычисление ожидаемых частот
expected_frequencies = []
for a, b in intervals:
    z1 = (a - mean) / std_dev
    z2 = (b - mean) / std_dev
    p = standard_normal_cdf(z2) - standard_normal_cdf(z1)
    expected_frequencies.append(total_count * p)

# Вычисление статистики хи-квадрат
chi_squared = sum((o - e) ** 2 / e for o, e in zip(frequencies, expected_frequencies))

# Критическое значение хи-квадрат для уровня значимости alpha и степеней свободы df = k - 1 - 2
degrees_of_freedom = len(intervals) - 1 - 2
chi_squared_critical = 16.812  # Табличное значение для alpha=0.02 и df=5

# Вывод результатов
print(f"Среднее значение: {mean:.2f}")
print(f"Стандартное отклонение: {std_dev:.2f}")
print(f"Наблюдаемое значение хи-квадрат: {chi_squared:.2f}")
print(f"Критическое значение хи-квадрат: {chi_squared_critical:.2f}")

if chi_squared < chi_squared_critical:
    print("Гипотеза о нормальности распределения не отвергается.")
else:
    print("Гипотеза о нормальности распределения отвергается.")
