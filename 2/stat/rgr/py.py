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

# 8. Verify the agreement using the Chi-square test
observed_freq, bins = np.histogram(data, bins='auto')
expected_freq = len(data) * np.diff(norm.cdf(bins, loc=mu, scale=std))
chi2_stat = np.sum((observed_freq - expected_freq)**2 / expected_freq)
p_val_chi2 = chi2.sf(chi2_stat, len(observed_freq) - 1)
print(f"Chi-square statistic: {chi2_stat}, p-value: {p_val_chi2}")

# 9. Interval estimates and hypothesis testing for normal distribution
confidence_level = 0.95
degrees_freedom = len(data) - 1
sample_mean = np.mean(data)
sample_std = np.std(data, ddof=1)
confidence_interval = norm.interval(confidence_level, loc=sample_mean, scale=sample_std / np.sqrt(len(data)))

print(f"{confidence_level*100}% confidence interval for the mean: {confidence_interval}")

# Hypothesis testing
alpha = 0.1  # Уровень значимости по варианту 11
hypothesized_mean = 13  # example value
t_stat = (sample_mean - hypothesized_mean) / (sample_std / np.sqrt(len(data)))
p_value = 2 * (1 - norm.cdf(abs(t_stat)))

print(f"t-statistic: {t_stat}, p-value: {p_value}")

# Выводы на русском языке
print("\nВыводы:")
if p_val_chi2 < alpha:
    print(f"Гипотеза о нормальном распределении отвергается на уровне значимости {alpha}.")
else:
    print(f"Нет оснований отвергать гипотезу о нормальном распределении на уровне значимости {alpha}.")

if p_value < alpha:
    print(f"Гипотеза о равенстве математического ожидания {hypothesized_mean} отвергается на уровне значимости {alpha}.")
else:
    print(f"Нет оснований отвергать гипотезу о равенстве математического ожидания {hypothesized_mean} на уровне значимости {alpha}.")

print(f"Доверительный интервал для математического ожидания: {confidence_interval}")