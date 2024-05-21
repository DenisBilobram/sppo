import numpy as np
from scipy.stats import linregress
import matplotlib.pyplot as plt

# Данные
U = np.array([15, 14, 13])
B_kr_squared = np.array([6.32025e-5, 5.992676e-5, 4.83025e-5])

# Метод наименьших квадратов (МНК)
slope, intercept, r_value, p_value, std_err = linregress(U, B_kr_squared)

# Значения для линии регрессии
U_fit = np.linspace(min(U), max(U), 100)
B_kr_squared_fit = slope * U_fit + intercept


# Новые данные с учетом поправки
B_kr_squared_corrected = np.array([6.32025e-5, 5.992676e-5, 4.83025e-5])

# Метод наименьших квадратов (МНК)
slope_corrected, intercept_corrected, r_value_corrected, p_value_corrected, std_err_corrected = linregress(U, B_kr_squared_corrected)

# Значения для линии регрессии
B_kr_squared_fit_corrected = slope_corrected * U_fit + intercept_corrected

# Угловой коэффициент прямой (slope)
slope_corrected


# Построение графика
plt.figure(figsize=(10, 6))
plt.plot(U, B_kr_squared_corrected, 'bo', markersize=8, label='Данные $B^2_{кр}$')
plt.plot(U_fit, B_kr_squared_fit_corrected, 'r-', label='Линия МНК')
plt.xlabel('$U$ (В)', fontsize=14)
plt.ylabel('$B^2_{кр}$ ($Тл^2*10^-5$)', fontsize=14)
plt.title('Зависимость $B^2_{кр}$ от $U$', fontsize=16)
plt.grid(True)
plt.legend(fontsize=12)
plt.xticks(U)
plt.yticks(np.linspace(min(B_kr_squared_corrected), max(B_kr_squared_corrected), 6))

plt.show()

