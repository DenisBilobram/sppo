import sys
from decimal import Decimal

def matrix_input():

    matrix = []
    while True:
        rows = int(input("Введите размерность матрицы n <= 20: "))
        if rows <= 20:
            break
    print("Введите коэффиценты расширенной матрицы (A|b).")
    for i in range(rows):
        while True:
            row = [Decimal(num) for num in input(f"Введите {rows+1} коэффициентов {i+1}-й строки через пробел: ").split() if num.isdigit]
            if (rows == len(row)-1):
                break
        matrix.append(row)


    return matrix

def matrix_parse(path: str):
    matrix = []
    with open(path, 'r') as file:
        file = file.readlines()
        for line in file:
            row = [Decimal(num) for num in line.split() if num.isdigit]
            if len(row)-1 != len(file):
                return []
            matrix.append(row)

    return matrix
    

def to_stepped(matrix):
    n = len(matrix)

    for i in range(n):

        if matrix[i][i] == 0:
            for k in range(i+1, n):
                if matrix[k][i] != 0:
                    matrix[i], matrix[k] = matrix[k], matrix[i]
                    break
            else:
                continue

        el = matrix[i][i]

        for j in range(i+1, n):
            if (matrix[i][i] == 0):
                continue
            ratio = matrix[j][i] / el
            transformed = [num*ratio for num in matrix[i]]
            nuled = [matrix[j][p] - transformed[p] for p in range(n+1)]
            matrix[j] = nuled

    return matrix

def check(matrix):
    rank_a = len([row for row in matrix[:-1] if any(elem != 0 for elem in row)])
    rank = len([row for row in matrix[:-1] if any(elem != 0 for elem in row)])

    if rank_a != rank:
        print("СЛАУ не совместна.")
        exit(1)

    determinant = Decimal(1)
    for i in range(len(matrix)):
        determinant *= matrix[i][i]

    if (determinant == 0):
        print("СЛАУ не определена.")
        exit(1)

    print(f"Определитель равен: {determinant}.")

def print_matrix(matrix):
    for row in matrix:
        for el in row:
            print(float(el), end="  ")
        print()

def back_step(matrix):
    n = len(matrix)
    x = [0] * n  
    
    for i in range(n-1, -1, -1): 
        sum_ax = sum(matrix[i][j] * x[j] for j in range(i+1, n))
        x[i] = (matrix[i][-1] - sum_ax) / matrix[i][i] 
        
    return x

def get_residuals(matrix, x):
    r = [0] * len(x)
    b = [el[-1] for el in matrix]
    a = [row[:-1] for row in matrix]

    for i in range(len(r)):
        r[i] = b[i] - sum(a[i][j] * x[j] for j in range(len(x)))

    return r



def main():

    matrix = []

    if len(sys.argv) == 1:
        matrix = matrix_input()
    elif len(sys.argv) == 2:
        matrix = matrix_parse(sys.argv[1])
    else:
        print("Неверный формат. Запустите программу без аргументов для ввода СЛАУ с клавиатуры или укажите путь к файлу с СЛАУ.")
        exit(1)

    if matrix == []:
        print("Неподходящая матрица.")
        exit(1)
    
    matrix = to_stepped(matrix)

    check(matrix)

    print_matrix(matrix)

    x = back_step(matrix)

    print(f"Вектор x: {[float(el) for el in x]}")

    r = get_residuals(matrix, x)

    print(f"Вектор r: {[float(el) for el in r]}")

    


    





if __name__ == '__main__':
    main()




    