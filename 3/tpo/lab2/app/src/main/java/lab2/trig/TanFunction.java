package lab2.trig;

import lab2.common.UnaryFunction;

public class TanFunction implements UnaryFunction {
    private final UnaryFunction sinFunction;
    private final UnaryFunction cosFunction;
    public TanFunction(UnaryFunction sinFunction, UnaryFunction cosFunction) {
        this.sinFunction = sinFunction;
        this.cosFunction = cosFunction;
    }
    @Override
    public double apply(double x) {
        double cosVal = cosFunction.apply(x);
        double sinVal = sinFunction.apply(x);
        return sinVal / cosVal;
    }
}
