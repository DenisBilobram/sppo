import math

# Данные
events = [0, 1, 2, 3, 4, 5, 6, 7]
frequencies = [9, 8, 27, 35, 34, 24, 9, 4]
alpha = 0.02

# Вычисление среднего числа событий (λ)
total_count = sum(frequencies)
avg = sum(x * f for x, f in zip(events, frequencies)) / total_count

# Вычисление ожидаемых частот
expected_frequencies = []
for x in events:
    p = (avg ** x * math.exp(-avg)) / math.factorial(x)
    expected_frequencies.append(total_count * p)

# Вычисление статистики хи-квадрат
chi_squared = sum((o - e) ** 2 / e for o, e in zip(frequencies, expected_frequencies))

# Критическое значение хи-квадрат для уровня значимости alpha и степеней свободы df = k - 1 - 1
degrees_of_freedom = len(events) - 1 - 1
chi_squared_critical = 15.507  # Табличное значение для alpha=0.02 и df=6

# Вывод результатов
print(f"Среднее число событий (λ): {avg:.2f}")
print(f"Наблюдаемое значение хи-квадрат: {chi_squared:.2f}")
print(f"Критическое значение хи-квадрат: {chi_squared_critical:.2f}")

if chi_squared < chi_squared_critical:
    print("Гипотеза о распределении Пуассона не отвергается.")
else:
    print("Гипотеза о распределении Пуассона отвергается.")
