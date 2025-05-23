package lab2.trig;

import lab2.common.UnaryFunction;

public class SinFunction implements UnaryFunction {
    private final UnaryFunction cos;
    public SinFunction(UnaryFunction cos) {
        this.cos = cos;
    }

    @Override
    public double apply(double x) {
        return cos.apply(Math.PI / 2 - x);
    }
}