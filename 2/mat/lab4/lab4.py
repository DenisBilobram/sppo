import numpy as np
from numpy import exp
import matplotlib.pyplot as plt
import math

def lin_approx(x_i, y_i):
    A = np.vstack([x_i, np.ones(len(x_i))]).T
    m, c = np.linalg.lstsq(A, y_i, rcond=None)[0]
    return f"{round(m, 3)}*x+{round(c, 3)}"

def quad_approx(x_i, y_i):
    A = np.vstack([x_i**2, x_i, np.ones(len(x_i))]).T
    a, b, c = np.linalg.lstsq(A, y_i, rcond=None)[0]
    return f"{round(a, 3)}*x**2+{round(b, 3)}*x+{round(c, 3)}"

def cub_approx(x_i, y_i):
    A = np.vstack([x_i**3, x_i**2, x_i, np.ones(len(x_i))]).T
    b = y_i
    a, b, c, d = np.linalg.lstsq(A, b, rcond=None)[0]
    return f"{round(a, 3)}*x**3+{round(b, 3)}*x**2+{round(c, 3)}*x+{round(d, 3)}"

def exp_approx(x_i, y_i):
    if np.any(y_i <= 0):
        raise ValueError("y_i must be positive for exponential approximation")
    A = np.vstack([x_i, np.ones(len(x_i))]).T
    b = np.log(y_i)
    b, ln_a = np.linalg.lstsq(A, b, rcond=None)[0]
    a = np.exp(ln_a)
    return f"{round(a, 3)}*ezp({round(b, 6)}*x)"

def log_approx(x_i, y_i):
    if np.any(x_i <= 0):
        raise ValueError("x_i must be positive for logarithmic approximation")
    A = np.vstack([np.log(x_i), np.ones(len(x_i))]).T
    b, a = np.linalg.lstsq(A, y_i, rcond=None)[0]
    return f"{round(a, 3)}+{round(b, 3)}*log(x)"

def pow_approx(x_i, y_i):
    if np.any(x_i <= 0) or np.any(y_i <= 0):
        raise ValueError("x_i and y_i must be positive for power approximation")
    A = np.vstack([np.log(x_i), np.ones(len(x_i))]).T
    b, ln_a = np.linalg.lstsq(A, np.log(y_i), rcond=None)[0]
    a = np.exp(ln_a)
    return f"{round(a, 3)}*x**{round(b, 3)}"

def write_results_to_file(filename, results):
    try:
        with open(filename, 'w', encoding="utf-8") as f:
            for line in results:
                f.write(line + '\n')
    except:
        print("Ошибка при открытии файла вывода.")

def main():
    input_mode = input("Выберите тип ввода (консоль/файл): ")

    while input_mode not in ["консоль", "файл"]:
        input_mode = input("Неверный тип, выберите из предложенных (консоль/файл): ")

    file = None
    if (input_mode == "файл"):
        path_to_file = input("Введите название файла: ")
        try:
            file = open(path_to_file, "r")
        except Exception:
            print("Не удалось открыть файл, тип ввода изменён на консольный.")
            input_mode = "консоль"

    x_i = []
    y_i = []

    if input_mode == "консоль":
        for _ in range(10):
            while True:
                try:
                    x, y = map(float, input("Введите пару чисел x и y через пробел: ").replace(",", ".").split())
                    if (x, y) not in zip(x_i, y_i):
                        x_i.append(x)
                        y_i.append(y)
                        break
                    else:
                        print("Пара чисел (x, y) уже была введена ранее. Пожалуйста, введите другую пару.")
                except ValueError:
                    print("x и y должны быть числами через пробел. Пожалуйста, попробуйте снова.")
    else:
        for line in file:
            try:
                x, y = map(float, line.split())
                if (x, y) not in zip(x_i, y_i):
                    x_i.append(x)
                    y_i.append(y)
                else:
                    print("Пара чисел (x, y) уже была введена ранее.")
                    exit()
            except ValueError:
                print("Данные в файле неверного формата.")
                exit()


    x_i = np.array(x_i)
    y_i = np.array(y_i)

    approximations = [lin_approx, quad_approx, cub_approx, exp_approx, log_approx, pow_approx]
    approximations_names = ["Линейная", "Квадратичная", "Кубическая", "Экспоненциальная", "Логарифмическая", "Степенная"]
    best_approximation = None
    best_error = float('inf')

    results = []

    for approx, name in zip(approximations, approximations_names):
        try:
            approximation = approx(x_i, y_i)
            fi_xi = np.array([eval(approximation.replace('x', str(x))) for x in x_i])
            epsi_i = y_i - fi_xi
            error = np.mean((y_i - fi_xi)**2)
            result_line = f"{name} аппроксимация: {approximation}, среднеквадратичное отклонение: {round(error, 4)}".replace("ezp", "exp")
            results.append(result_line)
            results.append(f"x_i: {list(x_i)}")
            results.append(f"y_i: {list(y_i)}")
            results.append(f"fi(x_i): {[round(val, 4) for val in fi_xi]}")
            results.append(f"epsi_i: {[round(val, 4) for val in epsi_i]}")
            results.append("\n")
            if error < best_error:
                best_error = error
                best_approximation = approximation
            x = np.linspace(min(x_i), max(x_i), 1000)
            y = [eval(approximation.replace('x', str(xi))) for xi in x]
            plt.plot(x, y, label=f'Аппроксимация {name}')
        except Exception as e:
            results.append(f"Ошибка при аппроксимации {name}: {e}")

    results.append(f"Наилучшая аппроксимация: {best_approximation}")

    mean_x = sum(x_i) / len(x_i)
    mean_y = sum(y_i) / len(y_i)
    top_corr = sum((x - mean_x) * (y - mean_y) for x, y in zip(x_i, y_i))
    bot_corr = math.sqrt(sum((x - mean_x)**2 for x in x_i) * sum((y - mean_y)**2 for y in y_i))
    corr = top_corr / bot_corr
    results.append(f"Коэффициент корреляции Пирсона для линейной зависимости: {corr}")

    r2_top = np.sum((y_i - [eval(best_approximation.replace('x', str(x))) for x in x_i])**2)
    r2_bot = np.sum((y_i - np.mean(y_i))**2)
    r2 = 1 - r2_top / r2_bot
    results.append(f"Коэффициент детерминации: {r2}")
    if r2 >= 0.95:
        results.append("Высокая точность аппроксимации.")
    elif r2 >= 0.75:
        results.append("Удволетворительная аппроксимация.")
    elif r2 >= 0.5:
        results.append("Слабая аппроксимация.")
    else:
        results.append("Точность аппроксимации недостаточна.")

    output_mode = input("Выберите тип вывода (консоль/файл): ")
    output_filename = ""
    if output_mode == "файл":
        output_filename = input("Введите имя файла для вывода: ")

    if output_mode == "консоль":
        for line in results:
            print(line)
    else:
        write_results_to_file(output_filename, results)

    plt.scatter(x_i, y_i, color='red', label='Исходные данные')
    plt.legend()
    plt.xlabel('Ось X')  
    plt.ylabel('Ось Y')  
    plt.grid(True)  
    plt.show()

if __name__ == "__main__":
    ezp = exp
    main()
