package lab2.log;

import lab2.common.UnaryFunction;

public class Log3Function implements UnaryFunction {
    private final UnaryFunction lnFunction;
    private final double ln3;
    public Log3Function(UnaryFunction lnFunction) {
        this.lnFunction = lnFunction;
        this.ln3 = lnFunction.apply(3.0);
    }
    @Override
    public double apply(double x) {
        return lnFunction.apply(x) / ln3;
    }
}
