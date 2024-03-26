import numpy as np
import matplotlib.pyplot as plt

# Определение систем уравнений и их Якобианов
def F1(x):
    return np.array([x[0]**2 + x[1]**2 - 4, x[0]**2 - x[1] - 1])

def J1(x):
    return np.array([[2*x[0], 2*x[1]], [2*x[0], -1]])

def F2(x):
    return np.array([np.sin(x[0]) + x[1]**2 - 1.5, x[0]**2 - np.cos(x[1]) - 0.5])

def J2(x):
    return np.array([[np.cos(x[0]), 2*x[1]], [2*x[0], np.sin(x[1])]])

def solve_system(A, B):
    n = len(A)
    
    for i in range(n):
        pivot = A[i][i]
        for j in range(i, n):
            A[i][j] /= pivot
        B[i] /= pivot

        for k in range(i+1, n):
            factor = A[k][i]
            for j in range(i, n):
                A[k][j] -= factor * A[i][j]
            B[k] -= factor * B[i]
    
    
    x = [0 for _ in range(n)]
    for i in range(n-1, -1, -1):
        x[i] = B[i] - sum(A[i][j] * x[j] for j in range(i+1, n))
    return x

def norm(x):
    return sum(xi**2 for xi in x)**0.5

def newton_method(F, J, x0, epsi=0.01, max_iter=100):
    x = np.array(x0, dtype=float)
    errors = []

    for i in range(max_iter):
        Fx = F(x)
        Jx = J(x)
        delta = solve_system(Jx, -Fx)
        x_new = x + delta
        error = norm(delta)
        errors.append(error)

        if error < epsi and delta[0] < epsi and delta[1] < epsi:
            print(f"Решение найдено: {x_new} после {i+1} итераций.")
            print(f"Вектор погрешностей: {errors}")

            if np.linalg.norm(F(x_new)) < epsi:
                print("Проверка решения: УСПЕШНО")
            else:
                print("Проверка решения: НЕ УСПЕШНО")
            return x_new

        x = x_new

    print("Решение не найдено.")
    exit(1)

def plot_system(F):
    title='График системы уравнений'
    x = np.linspace(-3, 3, 400)
    y = np.linspace(-3, 3, 400)
    X, Y = np.meshgrid(x, y)
    Z = np.array([F(np.array([X[i, j], Y[i, j]])) for i in range(X.shape[0]) for j in range(X.shape[1])])
    Z = Z.reshape(X.shape + (2,))
    
    plt.figure(figsize=(8, 6))
    plt.contour(X, Y, Z[:, :, 0], levels=[0], colors='r')
    plt.contour(X, Y, Z[:, :, 1], levels=[0], colors='b')
    plt.title(title)
    plt.xlabel('x')
    plt.ylabel('y')
    plt.grid(True)
    plt.axhline(0, color='black', lw=0.5)
    plt.axvline(0, color='black', lw=0.5)
    plt.show()

def main():
    print("Выберите систему уравнений для решения:")
    print("1: x^2 + y^2 - 4 = 0 и x^2 - y - 1 = 0")
    print("2: sin(x) + y^2 - 1.5 = 0 и x^2 - cos(y) - 0.5 = 0")
    choice = input("Введите номер системы: ")

    if choice == '1':
        F, J = F1, J1
    elif choice == '2':
        F, J = F2, J2
    else:
        print("Некорректный выбор.")
        return

    try:
        x0 = np.array(list(map(float, input("Введите начальные приближения x и y через пробел: ").split())))
    except:
        print("Некорректный ввод начальных приближений.")
        return


    solution, iterations = newton_method(F, J, x0)
    plot_system(F)

if __name__ == "__main__":
    main()
