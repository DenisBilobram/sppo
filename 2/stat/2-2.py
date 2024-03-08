import math
import matplotlib.pyplot as plt

nums = [12, 6, 8, 6, 10, 11, 7, 10, 12, 8, 7, 7, 6, 7, 8, 6, 11, 9, 11, 9, 10, 11, 9, 10, 7, 8, 8, 8, 11, 9, 8, 7, 5, 9, 7, 7, 14, 11, 9, 8, 7, 11, 15, 6, 4 ,7, 5, 5, 10, 7, 7, 5, 8, 10, 10, 15, 10, 10, 13, 12]
print(len(nums))

n = math.ceil(1+3.322*math.log(len(nums), 10))
n = 6
l = (max(nums)-min(nums))/n

intervals = [(min(nums)+l*i, min(nums)+l*(i+1)) for i in range(n)]
print(intervals)

chastots = [0] * n

for el in nums:
    for i in range(n):
        if el >= intervals[i][0] and el < intervals[i][1] + 0.1:
            chastots[i] += 1
nakop_chastots = [sum(chastots[:i]) for i in range(n+1)]

x_values = [0] + [interval[1] for interval in intervals]

plt.plot(x_values, nakop_chastots)
plt.xlabel('Значение')
plt.ylabel('ЭФР')
plt.title('Эмпирическая функция распределения интервального ряда')
plt.grid(True)
plt.show()
