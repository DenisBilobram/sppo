import matplotlib.pyplot as plt

x = [0, 1, 2, 3, 4]
n = [6, 7, 3, 6, 3]

y_vals = [sum(n[:i+1]) for i in range(len(n))]

plt.step(x, y_vals, where='post')
plt.xlabel('x')
plt.ylabel('F(x)')
plt.title('Эмпирическая функция распределения')
plt.grid(True)
plt.show()

avg = sum([x[i]*n[i] for i in range(len(x))])/sum(n)