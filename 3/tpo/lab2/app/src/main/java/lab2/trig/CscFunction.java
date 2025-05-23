package lab2.trig;

import lab2.common.UnaryFunction;

public class CscFunction implements UnaryFunction {
    private final UnaryFunction sinFunction;
    public CscFunction(UnaryFunction sinFunction) {
        this.sinFunction = sinFunction;
    }
    @Override
    public double apply(double x) {
        return 1.0 / sinFunction.apply(x);
    }
}