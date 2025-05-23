package lab2.trig;

import lab2.common.UnaryFunction;

public class CosFunction implements UnaryFunction {
    private final double EPS;

    public CosFunction(double eps) {
        this.EPS = eps;
    }

    @Override
    public double apply(double x) {

        x = x % (2 * Math.PI);

        double term = 1.0;
        double sum  = 1.0;
        int n = 1;
        while (Math.abs(term) > EPS) {
            term *= -x * x / ((2 * n - 1) * (2 * n));
            sum  += term;
            n++;
        }
        return sum;
    }
}
