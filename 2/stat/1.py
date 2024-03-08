x = [1, 3, 6, 16]
n = [4, 10, 5, 1]
avg = sum([x[i] * n[i] for i in range(len(x))])/sum(n)
disp = sum([(x[i]-avg)**2 * n[i] for i in range(len(x))])/sum(n)
otkl = disp**0.5
cv = otkl/avg * 100


x1 = [(1,5), (5, 9), (9, 13), (13, 17), (17, 21)]
n1 = [10, 20, 50, 12, 8]
centers1 = [(el[1] + el[0]) / 2 for el in x1]
avg1 = sum([centers1[i] * n1[i] for i in range(len(n1))])/sum(n1)
disp1 = (sum([centers1[i]**2 * n1[i] for i in range(len(n1))])/sum(n1) - avg1**2)
otkl1 = disp1**0.5
abs_otkl1 = sum([abs(centers1[i] - avg1)*n1[i] for i in range(len(n1))])/sum(n1)
cv1 = otkl1/avg1*100

