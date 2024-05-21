import tkinter as tk
from tkinter import filedialog, messagebox, simpledialog
import numpy as np
import math
import matplotlib.pyplot as plt

class InterpolationApp(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Interpolation App")
        self.geometry("400x300")
        self.create_widgets()

    def create_widgets(self):
        tk.Label(self, text="Выберите метод ввода данных:").pack(pady=10)
        tk.Button(self, text="Вручную", command=self.manual_input).pack(pady=5)
        tk.Button(self, text="Из файла", command=self.file_input).pack(pady=5)
        tk.Button(self, text="Функция", command=self.function_input).pack(pady=5)

    def manual_input(self):
        self.input_window = tk.Toplevel(self)
        self.input_window.title("Ввод данных вручную")

        tk.Label(self.input_window, text="Введите значения x через пробел:").pack(pady=5)
        self.x_entry = tk.Entry(self.input_window, width=50)
        self.x_entry.pack(pady=5)

        tk.Label(self.input_window, text="Введите значения y через пробел:").pack(pady=5)
        self.y_entry = tk.Entry(self.input_window, width=50)
        self.y_entry.pack(pady=5)

        tk.Button(self.input_window, text="Сохранить", command=self.save_manual_input).pack(pady=10)

    def save_manual_input(self):
        try:
            x_values = list(map(float, self.x_entry.get().replace(",", '.').split()))
            y_values = list(map(float, self.y_entry.get().replace(",", '.').split()))
            if len(x_values) != len(y_values) or len(x_values) < 2:
                raise ValueError("Количество точек должно быть равно и не менее двух.")
            self.x_values = x_values
            self.y_values = y_values
            self.input_window.destroy()
            self.request_target_input()
        except ValueError as e:
            messagebox.showerror("Ошибка ввода", f"Некорректный ввод: {e}. Пожалуйста, повторите.")

    def file_input(self):
        filepath = filedialog.askopenfilename(title="Выберите файл с данными")
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
                self.x_values = x_values
                self.y_values = y_values
                self.request_target_input()
        except Exception as e:
            messagebox.showerror("Ошибка файла", f"Ошибка при чтении файла: {e}")

    def function_input(self):
        self.input_window = tk.Toplevel(self)
        self.input_window.title("Выбор функции")

        tk.Label(self.input_window, text="Доступные функции: 1) sin(x), 2) cos(x), 3) x^2").pack(pady=5)
        self.func_choice = tk.Entry(self.input_window, width=5)
        self.func_choice.pack(pady=5)

        tk.Label(self.input_window, text="Введите начало интервала:").pack(pady=5)
        self.x_start = tk.Entry(self.input_window, width=10)
        self.x_start.pack(pady=5)

        tk.Label(self.input_window, text="Введите конец интервала:").pack(pady=5)
        self.x_end = tk.Entry(self.input_window, width=10)
        self.x_end.pack(pady=5)

        tk.Label(self.input_window, text="Введите количество точек:").pack(pady=5)
        self.num_points = tk.Entry(self.input_window, width=10)
        self.num_points.pack(pady=5)

        tk.Button(self.input_window, text="Сохранить", command=self.save_function_input).pack(pady=10)

    def save_function_input(self):
        try:
            func_choice = self.func_choice.get()
            if func_choice not in ['1', '2', '3']:
                raise ValueError("Выбор функции должен быть числом от 1 до 3.")
            x_start = float(self.x_start.get())
            x_end = float(self.x_end.get())
            if x_end <= x_start:
                raise ValueError("Конец интервала должен быть больше начала интервала.")
            num_points = int(self.num_points.get())
            if num_points < 2:
                raise ValueError("Количество точек должно быть не менее двух.")
            x_values = np.linspace(x_start, x_end, num_points)
            if func_choice == '1':
                y_values = np.sin(x_values)
                self.func = np.sin
            elif func_choice == '2':
                y_values = np.cos(x_values)
                self.func = np.cos
            elif func_choice == '3':
                y_values = x_values**2
                self.func = lambda x: x**2
            self.x_values = x_values.tolist()
            self.y_values = y_values.tolist()
            self.input_window.destroy()
            self.request_target_input()
        except ValueError as e:
            messagebox.showerror("Ошибка ввода", f"Некорректный ввод: {e}. Пожалуйста, повторите.")

    def request_target_input(self):
        self.target_window = tk.Toplevel(self)
        self.target_window.title("Ввод x для интерполяции")

        tk.Label(self.target_window, text="Введите значение x для интерполяции:").pack(pady=5)
        self.x_target_entry = tk.Entry(self.target_window, width=20)
        self.x_target_entry.pack(pady=5)

        tk.Button(self.target_window, text="Рассчитать", command=self.calculate_interpolation).pack(pady=10)

    def calculate_interpolation(self):
        try:
            x_target = float(self.x_target_entry.get().replace(",", "."))
            self.x_target = x_target
            self.target_window.destroy()

            p_newton_finite = interpolate_newton_finite_diff(self.x_values, self.y_values, self.x_target)
            p_lagrange = interpolate_lagrange(self.x_values, self.y_values, self.x_target)
            p_newton_divided = interpolate_newton_divided_diff(self.x_values, self.y_values, self.x_target)
            results = [p_newton_finite, p_lagrange, p_newton_divided]

            self.show_results(results)
            plot_function(self.x_values, self.y_values, self.x_target, results, self.func if hasattr(self, 'func') else None)
        except ValueError:
            messagebox.showerror("Ошибка ввода", "Некорректное значение x. Пожалуйста, повторите.")

    def show_results(self, results):
        result_window = tk.Toplevel(self)
        result_window.title("Результаты интерполяции")

        differences = calculate_differences(self.x_values, self.y_values)
        tk.Label(result_window, text="Таблица конечных разностей:").pack(pady=5)
        text = tk.Text(result_window, height=10, width=70, wrap=tk.WORD)
        text.pack(pady=5)
        for row in differences:
            text.insert(tk.END, " ".join(f"{item:.4f}" for item in row if item != 0) + "\n")

        if results[0] is not None and valid_for_finite(self.x_values):
            tk.Label(result_window, text=f"Ньютон (конечные разности): {results[0]:.8f}").pack(pady=5)
        else:
            tk.Label(result_window, text="Не удалось посчитать значение функции с помощью метода Ньютона (конечные разности) при данных x.").pack(pady=5)
        
        if results[1] is not None:
            tk.Label(result_window, text=f"Лагранж: {results[1]:.8f}").pack(pady=5)
        
        if results[2] is not None:
            tk.Label(result_window, text=f"Ньютон (разделённые разности): {results[2]:.8f}").pack(pady=5)


# Определение функций интерполяции и расчета конечных разностей

def calculate_differences(x, y):
    n = len(x)
    dy = np.zeros((n, n))
    dy[:, 0] = y
    for j in range(1, n):
        for i in range(n - j):
            dy[i][j] = dy[i + 1][j - 1] - dy[i][j - 1]
    return dy

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

if __name__ == "__main__":
    app = InterpolationApp()
    app.mainloop()
