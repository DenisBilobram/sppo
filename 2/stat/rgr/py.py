import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from scipy.stats import norm, kstest, chi2

# Данные о расходе воды (100 значений)
data = [8, 10, 11, 17, 15, 12, 15, 14, 16, 18,
        15, 18, 13, 14, 16, 17, 8, 11, 14, 10,
        16, 14, 18, 11, 11, 15, 15, 10, 12, 14,
        17, 12, 14, 14, 9, 12, 9, 13, 14, 10,
        12, 10, 11, 13, 11, 13, 14, 14, 12, 13,
        12, 14, 14, 12, 16, 15, 13, 13, 13, 15,
        13, 10, 12, 11, 11, 14, 15, 15, 19, 11,
        11, 13, 11, 12, 14, 15, 13, 11, 17, 18,
        12, 12, 12, 9, 10, 13, 9, 16, 14, 13,
        16, 13, 15, 12, 12, 17, 13, 15, 16, 14]

R = np.ptp(data)
print("Range of variation (R):", R)

# 2. Create a statistical series of frequencies
frequencies = pd.Series(data).value_counts().sort_index()
print("Statistical series of frequencies:\n", frequencies)

# 3. Create an interval statistical series of frequencies and relative frequencies
bins = np.histogram_bin_edges(data, bins='auto')
hist, bin_edges = np.histogram(data, bins=bins)
relative_frequencies = hist / len(data)

interval_series = pd.DataFrame({
    'Interval': pd.IntervalIndex.from_arrays(bin_edges[:-1], bin_edges[1:]),
    'Frequency': hist,
    'Relative Frequency': relative_frequencies
})
print("Interval statistical series:\n", interval_series)

# Display the interval series
print(interval_series)

# 4. Plot polygon and histogram of relative frequencies
plt.figure(figsize=(12, 6))
plt.subplot(1, 2, 1)
plt.plot(bin_edges[:-1], relative_frequencies, marker='o')
plt.title('Polygon of Relative Frequencies')
plt.xlabel('Value')
plt.ylabel('Relative Frequency')

plt.subplot(1, 2, 2)
plt.hist(data, bins=bins, density=True, alpha=0.6, color='g')
plt.title('Histogram of Relative Frequencies')
plt.xlabel('Value')
plt.ylabel('Relative Frequency')

plt.tight_layout()
plt.show()

# 5. Find empirical distribution function and plot its graph
ecdf = np.arange(1, len(data)+1) / len(data)
sorted_data = np.sort(data)
plt.step(sorted_data, ecdf, where="post")
plt.title('Empirical Distribution Function')
plt.xlabel('Value')
plt.ylabel('F(x)')
plt.grid(True)
plt.show()

# 6. Calculate sample values of numerical characteristics
mean = np.mean(data)
variance = np.var(data, ddof=1)
std_dev = np.std(data, ddof=1)

print(f"Mathematical expectation (M): {mean}")
print(f"Variance (D): {variance}")
print(f"Standard deviation (σ): {std_dev}")

# 7. Determine the distribution law (assuming normal distribution here)
mu, std = norm.fit(data)
xmin, xmax = min(data), max(data)
x = np.linspace(xmin, xmax, 100)
p = norm.pdf(x, mu, std)

plt.hist(data, bins=bins, density=True, alpha=0.6, color='g')
plt.plot(x, p, 'k', linewidth=2)
title = "Fit results: mu = %.2f,  std = %.2f" % (mu, std)
plt.title(title)
plt.show()
