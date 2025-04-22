package tpo.task1;

import java.sql.SQLOutput;

public class SecantCalculator {

    private static final long[] EULER_NUMBERS = {1, -1, 5, -61, 1385, -50521};

    private static long factorial(int n) {
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static double sec(double x) {
        // Приведение x к интервалу (-π, π]
        double twoPi = 2 * Math.PI;
        double xNorm = x % twoPi;
        if (xNorm <= -Math.PI) {
            xNorm += twoPi;
        } else if (xNorm > Math.PI) {
            xNorm -= twoPi;
        }

        if (xNorm > Math.PI/2) {
            double y = Math.PI - xNorm;
            return -secSeries(y);
        } else if (xNorm < -Math.PI/2) {
            double y = -Math.PI - xNorm;
            return -secSeries(y);
        } else {
            return secSeries(xNorm);
        }
    }

    private static double secSeries(double x) {
        double result = 0.0;
        for (int n = 0; n < 5; n++) {
            double euler = EULER_NUMBERS[n];
            double fact = factorial(2 * n);
            double term = Math.pow(-1, n) * euler * Math.pow(x, 2 * n) / fact;
            result += term;
        }
        return result;
    }

}
