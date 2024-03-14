# Метод произведений


import math

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
k = 1 + 3.322 * math.log10(n)
k = round(k)

# Ширина интервалов
h = math.ceil(r / k)

# Выбираем значение b из середины вариационного ряда
b = sample[n // 2]

# Функция для вычисления частоты попадания в интервал
def get_frequency(interval, data):
    return sum(1 for x in data if interval[0] <= x < interval[1])

# Создание интервалов
intervals = [(x_min + i * h, x_min + (i + 1) * h) for i in range(k)]

# Вычисление частоты для каждого интервала
frequencies = [get_frequency(interval, sample) for interval in intervals]

# Середины интервалов
midpoints = [(interval[0] + interval[1]) / 2 for interval in intervals]

# Вычисление Ui для каждого интервала
U = [(midpoint - b) / h for midpoint in midpoints]

# Вычисление начальных условных моментов на основе Ui и частот
v1 = sum(f * u for f, u in zip(frequencies, U)) / n
v2 = sum(f * u**2 for f, u in zip(frequencies, U)) / n
v3 = sum(f * u**3 for f, u in zip(frequencies, U)) / n
v4 = sum(f * u**4 for f, u in zip(frequencies, U)) / n

# Расчет математического ожидания
math_expectation = v1 * h + b

# Расчет дисперсии
dispersion = (v2 - v1**2) * h**2

# Среднее квадратическое отклонение
standard_deviation = math.sqrt(dispersion)

# Расчет центральных моментов
m3 = (v3 - 3 * v1 * v2 + 2 * v1**3) * h**3
m4 = (v4 - 4 * v1 * v3 + 6 * v1**2 * v2 - 3 * v1**4) * h**4

# Выборочные коэффициенты асимметрии и эксцесса
asymmetry_coeff = m3 / standard_deviation**3
excess_coeff = m4 / standard_deviation**4 - 3

frequency_table = {'Интервалы': intervals, 'Середины': midpoints, 'Частоты': frequencies, 'U': U}

moment_table = {
    'Мат. ожидание': math_expectation,
    'Дисперсия': dispersion,
    'Стандартное отклонение': standard_deviation,
    'Третий центральный момент': m3,
    'Четвёртый центральный момент': m4,
    'Коэф. асимметрии': asymmetry_coeff,
    'Эксцесс': excess_coeff
}

print("\n", frequency_table, "\n")
print(moment_table, "\n")