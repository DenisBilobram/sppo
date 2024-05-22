import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from scipy.stats import norm, chi2, kstest

# Given data
data = np.array([
    8, 10, 11, 17, 15, 12, 15, 14, 16, 18,
    15, 13, 14, 16, 17, 8, 11, 16, 14, 10,
    16, 17, 18, 11, 15, 17, 13, 14, 12, 14,
    12, 14, 11, 17, 9, 12, 15, 12, 13, 11,
    13, 12, 14, 11, 13, 14, 10, 13, 12, 14,
    16, 10, 13, 12, 13, 12, 14, 11, 17, 15,
    12, 12, 14, 10, 12, 16, 13, 15, 16, 14,
    13, 12, 12, 12, 11, 15, 14, 13, 12, 11,
    16, 13, 14, 9, 15, 9, 15, 10, 13, 10,
    13, 16, 17, 14, 17, 12, 13, 10, 12, 13
])

# 1. Range of variation
R = np.ptp(data)
print(f"Range of variation (R): {R}")

# 2. Frequency distribution series
freq_series = pd.Series(data).value_counts().sort_index()
print("Frequency distribution series:\n", freq_series)

# 3. Interval frequency distribution series
interval_series = pd.cut(data, bins=10).value_counts().sort_index()
print("Interval frequency distribution series:\n", interval_series)

# 4. Polygon and histogram
plt.figure(figsize=(12, 6))
plt.subplot(1, 2, 1)
plt.plot(freq_series.index, freq_series.values, marker='o')
plt.title('Polygon of Relative Frequencies')
plt.subplot(1, 2, 2)
plt.hist(data, bins=10, edgecolor='k', alpha=0.7)
plt.title('Histogram of Relative Frequencies')
plt.show()

# 5. Empirical distribution function
sorted_data = np.sort(data)
cdf = np.arange(1, len(data)+1) / len(data)
plt.step(sorted_data, cdf, where='post')
plt.title('Empirical Distribution Function')
plt.xlabel('Value')
plt.ylabel('Empirical CDF')
plt.grid(True)
plt.show()

# 6. Sample characteristics
mean = np.mean(data)
variance = np.var(data, ddof=1)
std_dev = np.std(data, ddof=1)
print(f"Mean (M): {mean}")
print(f"Variance (D): {variance}")
print(f"Standard deviation (σ): {std_dev}")

# 7. Choosing the distribution law
# Given the histogram, the data appears to follow a normal distribution.

# 8. Estimating distribution parameters
estimated_mean, estimated_std = norm.fit(data)
print(f"Estimated mean: {estimated_mean}")
print(f"Estimated standard deviation: {estimated_std}")

# Plot the PDF
xmin, xmax = plt.xlim()
x = np.linspace(xmin, xmax, 100)
p = norm.pdf(x, estimated_mean, estimated_std)
plt.plot(x, p, 'k', linewidth=2)
plt.hist(data, bins=10, density=True, alpha=0.6, color='g')
plt.title('Histogram and Normal PDF')
plt.show()

# 9. Testing hypotheses
# Chi-square test for goodness of fit
observed_freq, bins = np.histogram(data, bins=10)
expected_freq = len(data) * np.diff(norm.cdf(bins, loc=estimated_mean, scale=estimated_std))
chi2_stat, p_val_chi2 = chi2_contingency(observed_freq, expected_freq)
print(f"Chi-square statistic: {chi2_stat}, p-value: {p_val_chi2}")

# Kolmogorov-Smirnov test
ks_stat, p_val_ks = kstest(data, 'norm', args=(estimated_mean, estimated_std))
print(f"KS statistic: {ks_stat}, p-value: {p_val_ks}")

# Confidence intervals for the mean and variance
alpha = 0.05
t_critical = norm.ppf(1 - alpha/2)
ci_mean = (mean - t_critical * std_dev / np.sqrt(len(data)), mean + t_critical * std_dev / np.sqrt(len(data)))
ci_variance = ((len(data) - 1) * variance / chi2.ppf(1 - alpha/2, len(data) - 1),
               (len(data) - 1) * variance / chi2.ppf(alpha/2, len(data) - 1))

print(f"95% confidence interval for the mean: {ci_mean}")
print(f"95% confidence interval for the variance: {ci_variance}")

# Hypothesis test for the mean
null_mean = 12.5
t_stat = (mean - null_mean) / (std_dev / np.sqrt(len(data)))
p_value = 2 * (1 - norm.cdf(np.abs(t_stat)))
print(f"T statistic: {t_stat}, p-value: {p_value}")

# Hypothesis test for the variance
null_variance = 3.0
chi2_stat_variance = (len(data) - 1) * variance / null_variance
p_value_variance = chi2.sf(chi2_stat_variance, len(data) - 1)
print(f"Chi-square statistic for variance: {chi2_stat_variance}, p-value: {p_value_variance}")
