import tkinter as tk
from tkinter import ttk, messagebox, simpledialog
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from scipy.integrate import odeint

def euler_method(f, y0, x0, xn, h, epsilon):
    x = [x0]
    y = [y0]
    
    while x[-1] < xn:
        x1 = x[-1]
        y1 = y[-1]
        
        y1_h = y1 + h * f(x1, y1)
        y1_h2 = y1 + (h / 2) * f(x1, y1)
        y1_h2 += (h / 2) * f(x1 + h / 2, y1_h2)

        if np.abs(y1_h - y1_h2) / (2**1 - 1) <= epsilon:
            x.append(x1 + h)
            y.append(y1_h)
        else:
            h /= 2
        
    return np.array(x), np.array(y)

def runge_kutta_fixed_steps(f, y0, x0, h):
    x = [x0]
    y = [y0]
    
    for i in range(3):
        x1 = x[-1]
        y1 = y[-1]
        
        k1 = h * f(x1, y1)
        k2 = h * f(x1 + h / 2, y1 + k1 / 2)
        k3 = h * f(x1 + h / 2, y1 + k2 / 2)
        k4 = h * f(x1 + h, y1 + k3)
        
        y1 += (k1 + 2 * k2 + 2 * k3 + k4) / 6
        x1 += h
        
        x.append(x1)
        y.append(y1)
        
    return np.array(x), np.array(y)

# Метод Рунге-Кутты 4-го порядка
def runge_kutta_4th_method(f, y0, x0, xn, h, epsilon):
    x = [x0]
    y = [y0]

    while x[-1] < xn:
        x1 = x[-1]
        y1 = y[-1]

        k1 = h * f(x1, y1)
        k2 = h * f(x1 + h / 2, y1 + k1 / 2)
        k3 = h * f(x1 + h / 2, y1 + k2 / 2)
        k4 = h * f(x1 + h, y1 + k3)
        y1_h = y1 + (k1 + 2 * k2 + 2 * k3 + k4) / 6

        k1_h2 = (h / 2) * f(x1, y1)
        k2_h2 = (h / 2) * f(x1 + h / 4, y1 + k1_h2 / 2)
        k3_h2 = (h / 2) * f(x1 + h / 4, y1 + k2_h2 / 2)
        k4_h2 = (h / 2) * f(x1 + h / 2, y1 + k3_h2)
        y1_h2 = y1 + (k1_h2 + 2 * k2_h2 + 2 * k3_h2 + k4_h2) / 6

        k1_h4 = (h / 4) * f(x1, y1)
        k2_h4 = (h / 4) * f(x1 + h / 8, y1 + k1_h4 / 2)
        k3_h4 = (h / 4) * f(x1 + h / 8, y1 + k2_h4 / 2)
        k4_h4 = (h / 4) * f(x1 + h / 4, y1 + k3_h4)
        y1_h4 = y1 + (k1_h4 + 2 * k2_h4 + 2 * k3_h4 + k4_h4) / 6

        if np.abs(y1_h2 - y1_h4) / (2**2 - 1) <= epsilon:
            x.append(x1 + h)
            y.append(y1_h)
        else:
            h /= 2

    return np.array(x), np.array(y)

def milne_method(f, y0, x0, xn, h, epsilon):
    x_rk, y_rk = runge_kutta_fixed_steps(f, y0, x0, h)
    x = list(x_rk)
    y = list(y_rk)
    
    i = 3
    while x[-1] < xn:
        y_pred = y[i-3] + 4 * h * (2*f(x[i-2], y[i-2]) - f(x[i-1], y[i-1]) + 2*f(x[i], y[i])) / 3
        while True:
            f_pred = f(x[i] + h, y_pred)
            y_corr = y[i-1] + h * (f(x[i-1], y[i-1]) + 4 * f(x[i], y[i]) + f_pred) / 3
            if np.abs(y_corr - y_pred) < epsilon:
                y_pred = y_corr
                break
            else:
                y_pred = y_corr
        
        y.append(y_corr)
        x.append(x[i] + h)
        i += 1

    return np.array(x), np.array(y)

def diff_eq_1(x, y):
    return x + y

def diff_eq_2(x, y):
    return x**2

def diff_eq_3(x, y):
    return x**2 - y

def exact_solution_1(x, y0):
    C = y0 + 1
    return C * np.exp(x) - x - 1

def exact_solution_2(x, y0):
    return (x**3) / 3 + y0

def exact_solution_3(x, y0):
    def model(y, x):
        return x**2 - y
    y = odeint(model, y0, x)
    return y.flatten()

def runge_rule(y_half_step, y_full_step, p):
    min_length = min(len(y_half_step), len(y_full_step))
    return np.abs(y_half_step[:min_length] - y_full_step[:min_length]) / (2 ** p - 1)

class ODESolverApp(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Решение ОДУ")
        self.geometry("400x500")
        
        self.selected_methods = {
            "Euler": tk.BooleanVar(value=False),
            "Runge-Kutta 4-го порядка": tk.BooleanVar(value=False),
            "Milne": tk.BooleanVar(value=False)
        }
        self.selected_equation = tk.StringVar(value="y' = x + y")
        self.y0 = tk.DoubleVar()
        self.x0 = tk.DoubleVar()
        self.xn = tk.DoubleVar()
        self.h = tk.DoubleVar()
        self.epsilon = tk.DoubleVar()

        self.update_idletasks()
        self.geometry(f'+{(self.winfo_screenwidth() // 2) - (self.winfo_width() // 2)}+{(self.winfo_screenheight() // 2) - (self.winfo_height() // 2)}')

        
        self.create_widgets()
    
    def create_widgets(self):
        frame = ttk.Frame(self)
        frame.pack(expand=True)

        ttk.Label(frame, text="Выберите метод(ы):").grid(column=0, row=0, padx=10, pady=10, columnspan=2)

        row = 1
        for method, var in self.selected_methods.items():
            ttk.Checkbutton(frame, text=method, variable=var).grid(column=0, row=row, padx=10, pady=5, columnspan=2, sticky=tk.W)
            row += 1

        ttk.Label(frame, text="Выберите уравнение:").grid(column=0, row=row, padx=10, pady=10, columnspan=2)
        row += 1
        ttk.Combobox(frame, textvariable=self.selected_equation, values=["y' = x + y", "y' = x^2", "y' = x^2 - y"]).grid(column=0, row=row, padx=10, pady=10, columnspan=2)

        row += 1
        ttk.Label(frame, text="y0:").grid(column=0, row=row, padx=10, pady=10, sticky=tk.E)
        ttk.Entry(frame, textvariable=self.y0, width=15).grid(column=1, row=row, padx=10, pady=10, sticky=tk.W)
        
        row += 1
        ttk.Label(frame, text="x0:").grid(column=0, row=row, padx=10, pady=10, sticky=tk.E)
        ttk.Entry(frame, textvariable=self.x0, width=15).grid(column=1, row=row, padx=10, pady=10, sticky=tk.W)
        
        row += 1
        ttk.Label(frame, text="xn:").grid(column=0, row=row, padx=10, pady=10, sticky=tk.E)
        ttk.Entry(frame, textvariable=self.xn, width=15).grid(column=1, row=row, padx=10, pady=10, sticky=tk.W)
        
        row += 1
        ttk.Label(frame, text="h:").grid(column=0, row=row, padx=10, pady=10, sticky=tk.E)
        ttk.Entry(frame, textvariable=self.h, width=15).grid(column=1, row=row, padx=10, pady=10, sticky=tk.W)
        
        row += 1
        ttk.Label(frame, text="Точность (ε):").grid(column=0, row=row, padx=10, pady=10, sticky=tk.E)
        ttk.Entry(frame, textvariable=self.epsilon, width=15).grid(column=1, row=row, padx=10, pady=10, sticky=tk.W)
        
        row += 1
        ttk.Button(frame, text="Решить", command=self.solve_ode).grid(column=0, row=row, columnspan=2, padx=10, pady=20)
    
    def solve_ode(self):
        methods = {k: v.get() for k, v in self.selected_methods.items() if v.get()}
        equation = self.selected_equation.get()

        try:
            y0 = self.y0.get()
            x0 = self.x0.get()
            xn = self.xn.get()
            h = self.h.get()
            epsilon = self.epsilon.get()
        except tk.TclError:
            messagebox.showerror("Ошибка ввода", "Пожалуйста, введите числовые значения.")
            return

        if not methods:
            messagebox.showwarning("Ошибка", "Выберите хотя бы один метод.")
            return

        if h <= 0:
            messagebox.showerror("Ошибка ввода", "Шаг h должен быть положительным числом.")
            return

        if xn <= x0:
            messagebox.showerror("Ошибка ввода", "Значение xn должно быть больше x0.")
            return

        if epsilon <= 0:
            messagebox.showerror("Ошибка ввода", "Точность ε должна быть положительным числом.")
            return

        if equation == "y' = x + y":
            f = diff_eq_1
            exact_solution = exact_solution_1
        elif equation == "y' = x^2":
            f = diff_eq_2
            exact_solution = exact_solution_2
        else:
            f = diff_eq_3
            exact_solution = exact_solution_3

        results = {}
        errors = {}
        exact_x = np.linspace(x0, xn, 1000)
        exact_y = exact_solution(exact_x, y0)

        for method in methods:
            if method == "Euler":
                x, y = euler_method(f, y0, x0, xn, h, epsilon)
                x_half, y_half_step = euler_method(f, y0, x0, xn, h/2, epsilon)
                y_full_step = y[:len(y_half_step)]
                errors["Euler"] = runge_rule(y_half_step, y_full_step, 1)
                results["Euler"] = (x, y)
            elif method == "Runge-Kutta 4-го порядка":
                x, y = runge_kutta_4th_method(f, y0, x0, xn, h, epsilon)
                x_half, y_half_step = runge_kutta_4th_method(f, y0, x0, xn, h/2, epsilon)
                y_full_step = y[:len(y_half_step)]
                errors["Runge-Kutta 4-го порядка"] = runge_rule(y_half_step, y_full_step, 4)
                results["Runge-Kutta 4-го порядка"] = (x, y)
            elif method == "Milne":
                x, y = milne_method(f, y0, x0, xn, h, epsilon)
                y_exact = exact_solution(x, y0)
                errors["Milne"] = np.max(np.abs(y_exact - y))
                results["Milne"] = (x, y)

        plt.figure(figsize=(10, 6))
        plt.plot(exact_x, exact_y, label="Точное решение", color="red")
        for method, (x, y) in results.items():
            plt.plot(x, y, label=method)
        
        plt.xlabel('x')
        plt.ylabel('y')
        plt.title('Решения ОДУ')
        plt.legend()
        plt.grid(True)
        plt.show()

        self.show_table(results, errors)

    def show_table(self, results, errors):
        table_window = tk.Toplevel(self)
        table_window.title("Таблица результатов")
        table_window.geometry("600x400")

        table_window.update_idletasks()
        table_window.geometry(f'+{(table_window.winfo_screenwidth() // 2) - (table_window.winfo_width() // 2)}+{(table_window.winfo_screenheight() // 2) - (table_window.winfo_height() // 2)}')


        text = tk.Text(table_window, font=("Courier New", 10))
        text.pack(expand=True, fill=tk.BOTH)

        for method, (x, y) in results.items():
            text.insert(tk.END, f"Метод: {method}\n")
            if method != "Milne":
                text.insert(tk.END, f"{'x':<10}{'y':<20}{'Погрешность':<20}\n")
                for i, (xi, yi) in enumerate(zip(x, y)):
                    error = errors[method][i] if i < len(errors[method]) else ''
                    text.insert(tk.END, f"{xi:<10.5f}{yi:<20.5f}{error:<20.5f}\n")
            else:
                text.insert(tk.END, f"{'x':<10}{'y':<20}\n")
                for xi, yi in zip(x, y):
                    text.insert(tk.END, f"{xi:<10.5f}{yi:<20.5f}\n")
                text.insert(tk.END, f"\nМаксимальная погрешность: {errors[method]:.5f}\n\n")

        save = messagebox.askyesno("Сохранить", "Хотите сохранить таблицу в файл?")
        if save:
            file_name = simpledialog.askstring("Имя файла", "Введите имя файла (без расширения):")
            if file_name:
                table_data = []
                columns = ["Метод", "x", "y"]
                for method, (x, y) in results.items():
                    for i, (xi, yi) in enumerate(zip(x, y)):
                        if method != "Milne":
                            error = errors[method][i] if i < len(errors[method]) else ''
                            table_data.append([method, xi, yi, error])
                            if "Погрешность" not in columns:
                                columns.append("Погрешность")
                        else:
                            table_data.append([method, xi, yi])
                df = pd.DataFrame(table_data, columns=columns)
                df.to_csv(f"{file_name}.csv", index=False)
                messagebox.showinfo("Сохранение", "Таблица сохранена успешно.")


    
if __name__ == "__main__":
    app = ODESolverApp()
    app.mainloop()
