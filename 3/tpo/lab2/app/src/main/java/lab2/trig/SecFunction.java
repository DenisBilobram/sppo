package lab2.trig;

import lab2.common.UnaryFunction;

public class SecFunction implements UnaryFunction {
    private final UnaryFunction cosFunction;
    public SecFunction(UnaryFunction cosFunction) {
        this.cosFunction = cosFunction;
    }
    @Override
    public double apply(double x) {
        return 1.0 / cosFunction.apply(x);
    }
}
