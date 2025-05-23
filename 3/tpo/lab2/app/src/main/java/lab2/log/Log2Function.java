package lab2.log;

import lab2.common.UnaryFunction;

public class Log2Function implements UnaryFunction {
    private final UnaryFunction lnFunction;

    private final double ln2;
    public Log2Function(UnaryFunction lnFunction) {
        this.lnFunction = lnFunction;
        this.ln2 = lnFunction.apply(2.0);
    }
    @Override
    public double apply(double x) {
        return lnFunction.apply(x) / ln2;
    }
}
