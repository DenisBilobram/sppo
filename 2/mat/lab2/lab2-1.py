import numpy as np
import matplotlib.pyplot as plt

# Функции уравнений
def f1(x):
    return np.cos(x) - x

def f2(x):
    return x**3 - x

def f3(x):
    return np.exp(x) - 2*x - 5

def f4(x):
    return x**2 + 2*x - 3

# Вторые производные функций уравнений
def f1_p2(x):
    return -np.cos(x)

def f2_p2(x):
    return 6*x

def f3_p2(x):
    return np.exp(x) - 2

def f4_p2(x):
    return 2

# Фи функции для метода простой итерации

def fi1(x):
    return np.cos(x)

def fi2(x):
    return np.cbrt(x)

def fi3(x):
    return (np.exp(x) - 5) / 2

def fi4(x):
    return (3 - x**2) / 2

def fi1_p(x):
    return -np.sin(x)

def fi2_p(x):
    return (1/3) * abs(x)**(-2/3)

def fi3_p(x):
    return np.exp(x) / 2

def fi4_p(x):
    return -x

def bisection_method(f, a, b, epsi):
    c = 0
    while (b - a) / 2.0 > epsi:
        c += 1
        midpoint = (a + b) / 2.0
        if (f(midpoint) == 0):
            break
        elif f(a) * f(midpoint) < 0:
            b = midpoint
        else:
            a = midpoint
    
    print("Метод половинного деления: x = ", round((a + b) / 2.0, 6), "; Число итераций: ", c, ";")

def secant_method(f, f_p2, a, b, epsi):

    fa = f(a)
    fp2a = f_p2(a)

    if fa * fp2a > 0:
        x_0 = a
    else:
        x_0 = b
    
    x_1 = a if x_0 == b else b
    c = 0
    for _ in range(1000):
        c += 1
        fx_0 = f(x_0)
        fx_1 = f(x_1)
        x_new = x_1 - fx_1 * (x_1 - x_0) / (fx_1 - fx_0)
        if abs(x_new - x_1) < epsi:
            break
        x_0, x_1 = x_1, x_new

    print("Метод секущих: x = ", round(x_new, 6), "; Число итераций: ", c, ";")

def simple_iteration_method(f, fi, fi_p, a, b, x0, epsi):
    if abs(fi_p(a)) >= 1 or abs(fi_p(b)) >= 1:
        print("Метод простой итерации расходится на интервале.")
        return
    else:
        x_prev = x0
        x_curr = fi(x_prev)
        c = 0

        while abs(x_curr - x_prev) > epsi and abs(f(x_curr)) > epsi:

            x_prev = x_curr
            x_curr = fi(x_prev)
            c += 1

            if abs(fi_p(x_curr)) >= 1:
                print("Метод может не сойтись на шаге", c)
                return
            if (x_curr < a or x_curr > b):
                print("Новое приближение метода простой итерации выходит за пределы интервала на шаге", c)
                return
            
    print("Метод простой итерации: x = ", round(x_curr, 6), "; Число итераций: ", c, ";")
    

def keyboard_inp():
    try:
        a = float(input("Введите начальную границу интервала a: ").replace(",", "."))
        b = float(input("Введите конечную границу интервала b: ").replace(",", "."))
        x0 = float(input("Введите начальное приближение x0: ").replace(",", "."))
        epsi = float(input("Введите погрешность epsi: ").replace(",", "."))
    except ValueError:
        print("Ошибка в формате данных. Убедитесь, что вводите числа.")
        return keyboard_inp()  
    return (a, b), x0, epsi

def file_inp():
    filename = input("Введите имя файла с данными: ")
    try:
        with open(filename, 'r') as file:
            line = file.readline().strip().replace(",", ".")
            a, b, x0, epsi = map(float, line.split())
            return (a, b), x0, epsi
    except FileNotFoundError:
        print("Файл не найден. Пожалуйста, проверьте имя файла и путь к нему.")
    except ValueError:
        print("Ошибка в формате данных. Убедитесь, что данные в файле представлены четырьмя числами, разделенными пробелами.")
        return file_inp()  
    except Exception as e:
        print(f"Произошла ошибка при чтении файла.")
        return file_inp()

def check_params(f, borders, x0, epsi):
    a, b = borders
    
    if a >= b:
        return "Некорректно задан интервал: начальная граница должна быть меньше конечной."
    
    if epsi <= 0:
        return "Погрешность должна быть положительным числом."
    
    if not (a <= x0 <= b):
        return "Начальное приближение x0 должно находиться внутри интервала."
    
    fa = f(a)
    fb = f(b)
    
    if fa * fb > 0:
        return "Функция не меняет знак на концах интервала - корень может отсутствовать."
    
    steps = 1000
    prev_sign = 0
    c = 0
    for i in range(steps):
        x = a + (b - a) * i / steps
        sign = np.sign(f(x))
        if prev_sign != 0 and prev_sign != sign:
            c += 1
        prev_sign = sign
    if c > 1:
        return "Функция имеет более одного корня на интервале."
    return "OK"

def plot_function(f, x_start, x_end, num_points=1000):
    x = np.linspace(x_start, x_end, num_points)  
    y = f(x) 

    plt.figure(figsize=(10, 6))  
    plt.plot(x, y, label=f'f(x)')
    plt.axhline(0, color='black', lw=1) 
    plt.axvline(0, color='black', lw=1)  
    plt.title('График функции')  
    plt.xlabel('x')  
    plt.ylabel('f(x)')  
    plt.grid(True)  #
    plt.legend()  
    plt.show() 

equations = [
        '1) cos(x) - x = 0\n',
        '2) x^3 - x = 0\n',
        '3) e^x - 2x - 5 = 0\n',
        '4) x^2 + 2x - 3 = 0\n'
    ]
equations_nums = {
    '1': [f1, f1_p2, fi1, fi1_p],
    '2': [f2, f2_p2, fi2, fi2_p],
    '3': [f3, f3_p2, fi3, fi3_p],
    '4': [f4, f4_p2, fi4, fi4_p],
}
inp_modes = {
    '1': keyboard_inp,
    '2': file_inp,
}


def main():

    print("Уравнения:\n", *equations)

    equ_num = ""
    while equ_num not in equations_nums.keys():
        equ_num = input("Введите номер уравнения: ")

    inp_mode = ""
    while inp_mode not in inp_modes.keys():
        inp_mode = input("Введите номер способа ввода начальных данных - 1) Клавиатура 2) Файл : ")

    borders, x0, epsi = inp_modes[inp_mode]()
    
    params_valid = check_params(equations_nums[equ_num][0], borders, x0, epsi)
    if (params_valid != "OK"):
        print(params_valid)
        return 1
    
    bisection_method(equations_nums[equ_num][0], borders[0], borders[1], epsi)
    secant_method(equations_nums[equ_num][0], equations_nums[equ_num][1], borders[0], borders[1], epsi)
    simple_iteration_method(equations_nums[equ_num][0], equations_nums[equ_num][2], equations_nums[equ_num][3], borders[0], borders[1], x0, epsi)

    plot_function(equations_nums[equ_num][0], borders[0]-0.5, borders[1]+0.5)

if __name__ == "__main__":
    main()
