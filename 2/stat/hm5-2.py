import math

# Данные выборки
data = [111, 85, 85, 91, 101, 109, 86, 102, 111, 98, 105, 85, 112, 109, 115, 99, 105, 111, 94, 107,
        99, 107, 125, 89, 104, 113, 105, 88, 103, 97, 115, 109, 89, 108, 107, 97, 106, 107, 96, 108,
        109, 139, 116, 117, 103, 127, 119, 118, 125, 105]

# Уровень доверия
confidence = 0.95

# Количество элементов выборки
n = len(data)

# Вычисление среднего значения выборки
mean = sum(data) / n

# Вычисление дисперсии выборки и стандартного отклонения
variance = sum((x - mean) ** 2 for x in data) / (n - 1)
std_dev = math.sqrt(variance)

# Число степеней свободы
df = n - 1

# Критическое значение t для доверительного интервала среднего значения (для уровня доверия 95% и df = 49, t_crit ≈ 2.0096)
t_crit = 2.0096

# Стандартная ошибка среднего значения
SE = std_dev / math.sqrt(n)

# Доверительный интервал для среднего значения
mean_margin_error = t_crit * SE
mean_conf_interval = (mean - mean_margin_error, mean + mean_margin_error)

# Критические значения chi-square для доверительного интервала стандартного отклонения (для уровня доверия 95% и df = 49)
chi2_lower = 32.357
chi2_upper = 71.420

# Доверительный интервал для стандартного отклонения
std_dev_conf_interval = (
    math.sqrt(df * variance / chi2_upper),
    math.sqrt(df * variance / chi2_lower)
)

# Вывод результатов
print(f"Среднее значение: {mean:.2f}")
print(f"Доверительный интервал для среднего значения: ({mean_conf_interval[0]:.2f}, {mean_conf_interval[1]:.2f})")

print(f"\nСтандартное отклонение: {std_dev:.2f}")
print(f"Доверительный интервал для стандартного отклонения: ({std_dev_conf_interval[0]:.2f}, {std_dev_conf_interval[1]:.2f})")