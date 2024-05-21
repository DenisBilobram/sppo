import numpy as np
import math
import matplotlib.pyplot as plt

def request_input():
    choice = input("Выберите метод ввода данных: 1) вручную, 2) из файла, 3) функция. Введите номер: ")
    if choice == '1':
        return manual_input()
    elif choice == '2':
        return file_input()
    elif choice == '3':
        return function_input()
    else:
        print("Неверный выбор. Пожалуйста, попробуйте еще раз.")
        return request_input()

def manual_input():
    while True:
        try:
            x_values = list(map(float, input("Введите значения x через пробел: ").replace(",", '.').split()))
            while list(set(x_values)) != x_values:
                print("Все x должны быть уникальными.")
                x_values = list(map(float, input("Введите значения x через пробел: ").replace(",", '.').split()))
            y_values = list(map(float, input("Введите значения y через пробел: ").replace(",", '.').split()))
            if len(x_values) != len(y_values) or len(x_values) < 2:
                raise ValueError("Количество точек должно быть равно и не менее двух.")
            return x_values, y_values
        except ValueError as e:
            print(f"Некорректный ввод: {e}. Пожалуйста, повторите.")

def file_input():
    filepath = input("Введите путь к файлу с данными: ")
    try:
        with open(filepath, 'r') as file:
            x_values = []
            y_values = []
            for line in file:
                x, y = map(float, line.split())
                x_values.append(x)
                y_values.append(y)
            if len(x_values) < 2:
                raise ValueError("Данные в файле недостаточны.")
            elif list(set(x_values)) != x_values:
                raise ValueError("Все x должны быть уникальными.")
            return x_values, y_values
    except Exception as e:
            print(f"Ошибка при чтении файла: {e}")
            return file_input()

def function_input():
    print("Доступные функции: 1) sin(x), 2) cos(x), 3) x^2")
    while True:
        try:
            func_choice = input("Выберите функцию (1-3): ")
            if func_choice not in ['1', '2', '3']:
                raise ValueError("Выбор функции должен быть числом от 1 до 3.")
            x_start = float(input("Введите начало интервала: "))
            x_end = float(input("Введите конец интервала: "))
            if x_end <= x_start:
                raise ValueError("Конец интервала должен быть больше начала интервала.")
            num_points = int(input("Введите количество точек: "))
            if num_points < 2:
                raise ValueError("Количество точек должно быть не менее двух.")
            x_values = np.linspace(x_start, x_end, num_points)
            break
        except ValueError as e:
            print(f"Некорректный ввод: {e}")

    if func_choice == '1':
        y_values = np.sin(x_values)
        func = np.sin
    elif func_choice == '2':
        y_values = np.cos(x_values)
        func = np.cos
    elif func_choice == '3':
        y_values = x_values**2
        func = lambda x: x**2

    return x_values.tolist(), y_values.tolist(), func


def calculate_differences(x, y):
    n = len(x)
    dy = np.zeros((n, n))
    dy[:, 0] = y
    for j in range(1, n):
        for i in range(n - j):
            dy[i][j] = dy[i + 1][j - 1] - dy[i][j - 1]
    return dy

def print_differences_table(dy):
    n = len(dy)
    print("Таблица конечных разностей:")
    for row in dy[:n]:
        print(", ".join(f"{v:.4f}" for v in row if v != 0))

def interpolate_lagrange(x, y, x_target):
    result = 0
    n = len(x)
    for i in range(n):
        term = y[i]
        for j in range(n):
            if i != j:
                if (x[i] - x[j]) == 0:
                    return None
                term = term * (x_target - x[j]) / (x[i] - x[j])
        result += term
    return result

def interpolate_newton_divided_diff(x, y, x_target):
    n = len(x)
    dd_table = np.zeros((n, n))
    dd_table[:, 0] = y
    for j in range(1, n):
        for i in range(n - j):
            dd_table[i][j] = (dd_table[i + 1][j - 1] - dd_table[i][j - 1]) / (x[i + j] - x[i])
    result = dd_table[0][0]
    for i in range(1, n):
        term = dd_table[0][i]
        for j in range(i):
            term *= (x_target - x[j])
        result += term
    return result

def interpolate_newton_finite_diff(x, y, x_target):
    h = x[1] - x[0]
    if (h == 0):
        return None
    t = (x_target - x[0]) / h
    n = len(x)
    dy = calculate_differences(x, y)
    p = y[0]
    for i in range(1, n):
        term = dy[0][i]
        for j in range(i):
            term *= (t - j)
        term /= math.factorial(i)
        p += term
    return p

def plot_function(x, y, x_target, results, func=None):
    x_dense = np.linspace(min(x), max(x), 300)
    plt.figure(figsize=(10, 5))
    plt.plot(x, y, 'o', label='Data points')
    
    if func:
        plt.plot(x_dense, func(x_dense), label='Original function', linestyle='-', color='blue')
    
    methods = ['Newton (finite diff)', 'Lagrange', 'Newton (divided diff)']
    colors = ['red', 'green', 'purple']

    for result, method, color in zip(results, methods, colors):
        y_interp = [interpolate_method(x, y, xi, method) for xi in x_dense]

        if None not in y_interp and (method != 'Newton (finite diff)' or valid_for_finite(x)):
            plt.plot(x_dense, y_interp, label=f'{method}', linestyle='--', color=color)
            plt.plot(x_target, result, 'X', markersize=10, markeredgecolor=color, markerfacecolor='none', label=f'{method} at x={x_target:.2f}')

    plt.legend()
    plt.xlabel('x')
    plt.ylabel('y')
    plt.title('Interpolation Comparison')
    plt.grid(True)
    plt.show()

def interpolate_method(x, y, x_target, method):
    if method == 'Newton (finite diff)':
        return interpolate_newton_finite_diff(x, y, x_target)
    elif method == 'Lagrange':
        return interpolate_lagrange(x, y, x_target)
    elif method == 'Newton (divided diff)':
        return interpolate_newton_divided_diff(x, y, x_target)

def valid_for_finite(x):
    dist = round(x[1] - x[0], 3)
    for i in range(len(x)-1):
        if round(x[i+1] - x[i], 3) != dist:
            return False
    return True



def main():
    data  = request_input()
    x_target = None
    while x_target == None:
        try:
            x_target = float(input("Введите значение x для интерполяции: ").replace(",", "."))
        except Exception:
            print("Неподходящее значение x.")
    if len(data) == 3:
        x, y, func = data
    else:
        x, y = data
        func = None
    
    p_newton_finite = interpolate_newton_finite_diff(x, y, x_target)
    p_lagrange = interpolate_lagrange(x, y, x_target)
    p_newton_divided = interpolate_newton_divided_diff(x, y, x_target)
    results = [p_newton_finite, p_lagrange, p_newton_divided]

    differences = calculate_differences(x, y)
    print("Таблица конечных разностей:")
    for row in differences:
        print(" ".join(f"{item:.4f}" for item in row if item != 0))

    if (p_newton_finite != None and valid_for_finite(x)):
        print(f"Значение функции в точке {x_target} с помощью метода Ньютона (конечные разности): {p_newton_finite:.8f}")
    else:
        print(f"Не удалось посчитать значение функции с помощью метода Ньютона (конечные разности) при данных x.")
    if (p_lagrange != None):
        print(f"Значение функции в точке {x_target} с помощью метода Лагранжа: {p_lagrange:.8f}")
    if (p_newton_divided != None):
        print(f"Значение функции в точке {x_target} с помощью метода Ньютона (разделённые разности): {p_newton_divided:.8f}")

    plot_function(x, y, x_target, results, func)

if __name__ == "__main__":
    main()
