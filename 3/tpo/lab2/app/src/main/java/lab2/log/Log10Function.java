package lab2.log;

import lab2.common.UnaryFunction;

public class Log10Function implements UnaryFunction {
    private final UnaryFunction lnFunction;
    private final double ln10;
    public Log10Function(UnaryFunction lnFunction) {
        this.lnFunction = lnFunction;
        this.ln10 = lnFunction.apply(10.0);
    }
    @Override
    public double apply(double x) {
        return lnFunction.apply(x) / ln10;
    }
}
