import matplotlib.pyplot as plt

data = [
    20, 19, 22, 24, 21, 18, 23, 17, 20, 16, 15, 23, 21, 24, 18, 23, 21, 19, 20, 24, 21, 20, 18, 17, 22, 20, 16, 22, 18, 20, 17, 21, 17,
    19, 20, 20, 21, 18, 22, 23, 21, 25, 22, 20, 19, 21, 24, 23, 21, 19, 22, 21, 19, 20, 23, 22, 25, 21, 21
]

print(sorted(data))


frequency = {}
for value in data:
    if value in frequency:
        frequency[value] += 1
    else:
        frequency[value] = 1

sorted_values = sorted(frequency.keys())
sorted_frequency = [frequency[value] for value in sorted_values]

relative_frequency = [f / len(data) for f in sorted_frequency]
cumulative_frequency = []
cumulative = 0
for f in sorted_frequency:
    cumulative += f
    cumulative_frequency.append(cumulative)

cumulative_relative_frequency = [cf / len(data) for cf in cumulative_frequency]

plt.figure(figsize=(14, 4))

plt.subplot(1, 3, 2)
plt.plot(sorted_values, relative_frequency, marker='o', linestyle='-', color='purple')
plt.fill_between(sorted_values, relative_frequency, color='lavender', alpha=0.5)
plt.xlabel('Значение')
plt.ylabel('Относительные частоты')
plt.title('Полигон')

plt.subplot(1, 3, 3)
plt.plot(sorted_values, cumulative_relative_frequency, marker='o', linestyle='-', color='green')
plt.fill_between(sorted_values, cumulative_relative_frequency, color='honeydew', alpha=0.5)
plt.xlabel('Значение')
plt.ylabel('Накопительная относительная частота')
plt.title('Кумулята')

plt.tight_layout()
plt.show()
