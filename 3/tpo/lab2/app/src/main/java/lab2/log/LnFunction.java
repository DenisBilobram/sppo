package lab2.log;

import lab2.common.UnaryFunction;

public class LnFunction implements UnaryFunction {
    private final double EPS;

    public LnFunction(double eps) {
        this.EPS = eps;
    }

    @Override
    public double apply(double x) {
        if (x <= 0.0) {
            throw new IllegalArgumentException("ln(x) определён только при x > 0");
        }

        double y = (x - 1) / (x + 1);
        double term = y; 
        double sum = 0.0;
        int n = 0;
        while (Math.abs(term) > EPS) {
            sum += term / (2 * n + 1);

            term *= y * y;
            n++;
        }
        return 2 * sum;
    }
}
