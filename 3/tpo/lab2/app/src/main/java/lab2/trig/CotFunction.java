package lab2.trig;

import lab2.common.UnaryFunction;

public class CotFunction implements UnaryFunction {
    private final UnaryFunction sinFunction;
    private final UnaryFunction cosFunction;
    public CotFunction(UnaryFunction sinFunction, UnaryFunction cosFunction) {
        this.sinFunction = sinFunction;
        this.cosFunction = cosFunction;
    }
    @Override
    public double apply(double x) {
        double sinVal = sinFunction.apply(x);
        double cosVal = cosFunction.apply(x);
        return cosVal / sinVal;
    }
}