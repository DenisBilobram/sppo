package tpo.task1;

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
        double result = 0.0;
        for (int n = 0; n < 5; n++) {
            long euler = EULER_NUMBERS[n];              
            long fact = factorial(2 * n);               
            double term = (Math.pow(-1, n) * euler * Math.pow(x, 2 * n)) / fact; 
            result += term;                             
        }
        return result;
    }

}