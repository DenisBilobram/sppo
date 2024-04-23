import numpy as np

def f1(x):
    return x**2

def f2(x):
    return np.sin(x)

def f3(x):
    return np.exp(-x)

def rectangle_method(f, a, b, n, method):
    h = (b - a) / n
    if method == 0:
        x = np.linspace(a, b-h, n)
    elif method == 1:
        x = np.linspace(a+h/2, b-h/2, n)
    elif method == 2:
        x = np.linspace(a+h, b, n)

    return h * np.sum(f(x))

def trapezoid_method(f, a, b, n):
    h = (b - a) / n
    x = np.linspace(a, b, n+1)
    return h * (0.5*f(x[0]) + 0.5*f(x[-1]) + np.sum(f(x[1:-1])))

def simpson_method(f, a, b, n):
    h = (b - a) / n
    x = np.linspace(a, b, n+1)
    return h / 3 * (f(x[0]) + f(x[-1]) + 4*np.sum(f(x[1:-1:2])) + 2*np.sum(f(x[2:-1:2])))

def get_input(prompt, is_num = False):
    while True:
        try:
            value = input(prompt).replace(",", ".")

            if is_num: value = float(value)

            return value
        except ValueError:
            print("Некорректный ввод. Пожалуйста, попробуйте еще раз.")

def integrate():

    functions = [f1, f2, f3]
    functions_text = ['x**2', 'sin(x)', 'e^(-x)']
    methods = ['rectangle', 'trapezoid', 'simpson']

    func_choice = None
    while func_choice not in [0, 1, 2]:
        print("Выберите номер функции:")
        for i, f in enumerate(functions_text, 1):
            print(f"{i}: {f}")
        func_choice = int(get_input("", True)) - 1
    
    a = float(get_input("Введите нижний предел интегрирования (a): ", True))
    b = float(get_input("Введите верхний предел интегрирования (b): ", True))
    while a >= b:
        print("Верхний предел должен быть больше нижнего.")
        a = float(get_input("Введите нижний предел интегрирования (a): ", True))
        b = float(get_input("Введите верхний предел интегрирования (b): ", True))

    eps = float(get_input("Введите точность интегрирования: ", True))
    while (eps <= 0):
        print("Точность должна быть положительой.")
        eps = float(get_input("Введите точность интегрирования: ", True))

    print("Выберите метод интегрирования:")
    for i, m in enumerate(methods, 1):
        print(f"{i}: {m}")
    method_choice = int(get_input("", True)) - 1
    n = 4
    I1 = 0
    submethod_choice = None
    while True:
        if methods[method_choice] == 'rectangle':

            if submethod_choice == None:
                print("Выберите номер подметода (1: left, 2: middle, 3: right):")
                submethod_choice = int(get_input("", True)) - 1
                while submethod_choice not in [0, 1, 2]:
                    print("Такого номера нет.")
                    print("Выберите номер подметода (1: left, 2: middle, 3: right):")
                    submethod_choice = int(get_input("", True)) - 1

            I2 = rectangle_method(functions[func_choice], a, b, n, submethod_choice)
        elif methods[method_choice] == 'trapezoid':
            I2 = trapezoid_method(functions[func_choice], a, b, n)
        elif methods[method_choice] == 'simpson':
            I2 = simpson_method(functions[func_choice], a, b, n)
        if n > 4 and abs(I1 - I2) / (2**4 - 1) < eps:
            break
        I1 = I2
        n += 2

    print(f"Результат интегрирования: {I2};\nЧисло разбиения интервала для достижения точности: {n}")

if __name__ == "__main__":
    integrate()