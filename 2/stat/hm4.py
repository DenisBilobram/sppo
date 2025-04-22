import math

sr = "20 19  22 24 21 18 23 17 20 16 15 23 21 24 21 18 23 21 19 20 24 21 20 18 17 22 20 16 22 18 20 17 21 17 19 20 20 21 18 22 23 21 25 22 20 19 21 24 23 21 19 22 21 19 20 23 22 25 21 21"
# Вариационный ряд 1
sample = [
    20, 19, 22, 24, 21, 18, 23, 17, 20, 16, 15, 23, 21, 24, 21, 18, 23,
    21, 19, 20, 24, 21, 20, 18, 17, 22, 20, 16, 22, 18, 20, 17, 21, 17,
    19, 20, 20, 21, 18, 22, 23, 21, 25, 22, 20, 19, 21, 24, 23, 21, 19,
    22, 21, 19, 20, 23, 22, 25, 21, 21
]

# Вариационный ряд 2
# sample = [
#   111, 85, 85, 91, 101, 109, 86, 102, 111, 98, 105, 85, 112,
#   109, 115, 99, 105, 111, 94, 107, 99, 107, 125, 89, 104, 113, 105,
#   88, 103, 97, 115, 109, 89, 108, 107, 97, 106, 107, 96, 108, 109, 
#   139, 116, 117, 103, 127, 119, 118, 125, 105
#]

# Объем выборки
n = len(sample)

# Минимальное и максимальное значение
x_min = min(sample)
x_max = max(sample)

# Размах вариации
r = x_max - x_min

# Количество интервалов по формуле Стерджеса
k = math.ceil(1 + 3.322 * math.log10(n))

# Ширина интервалов
h = r / k

# Выбираем значение b из середины вариационного ряда
b = sample[n // 2]

# Функция для вычисления частоты попадания в интервал
def get_frequency(interval, data):
    return sum(1 for x in data if interval[0] <= x < interval[1])

# Создание интервалов
intervals = [(x_min + i * h, x_min + (i + 1) * h) for i in range(k)]

# Вычисление частоты для каждого интервала
frequencies = [get_frequency(interval, sample) for interval in intervals]
frequencies[k-1] += sample.count(intervals[k-1][1])

# Середины интервалов
midpoints = [(interval[0] + interval[1]) / 2 for interval in intervals]

h = 1.43
b = 20

# Значения a, b, d, s из вашего сообщения
a = [50, 24, 6, 0]  # a1, a2, a3, a4
b_values = [28, 13, 3, 0]  # b1, b2, b3, b4
d = [22, 11, 3, 0]  # d1, d2, d3, d4
s = [78, 37, 9, 0]  # s1, s2, s3, s4

n = 60

v1 = -d[0] / n
v2 = (s[0] + 2 * s[1]) / n
v3 = (-d[0] + 6 * d[1] + 6 * d[2]) / n
v4 = (s[0] + 14 * s[1] + 36 * s[2] + 24 * s[3]) / n

# Рассчитываем математическое ожидание и дисперсию
math_expectation = v1 * h + b
dispersion = (v2 - v1**2) * h**2

# Среднее квадратическое отклонение
standard_deviation = math.sqrt(dispersion)

# Центральные моменты
m3 = (v3 - 3 * v1 * v2 + 2 * v1**3) * h**3
m4 = (v4 - 4 * v1 * v3 + 6 * v1**2 * v2 - 3 * v1**4) * h**4

# Выборочные коэффициенты асимметрии и эксцесса
asymmetry_coeff = m3 / (standard_deviation**3) if standard_deviation != 0 else 0
excess_coeff = m4 / (standard_deviation**4) - 3 if standard_deviation != 0 else 0

print(f"Мат. ожидание: {math_expectation}, Дисперсия: {dispersion}, Отклонение: {standard_deviation}, Коэф. асимметрии: {asymmetry_coeff}, Эксцесс: {excess_coeff}")