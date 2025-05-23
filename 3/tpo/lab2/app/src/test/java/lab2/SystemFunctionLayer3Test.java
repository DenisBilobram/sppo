package lab2;

import lab2.common.UnaryFunction;
import lab2.common.stub.TestPoint;
import lab2.log.*;
import lab2.trig.CosFunction;
import lab2.trig.CotFunction;
import lab2.trig.CscFunction;
import lab2.trig.SecFunction;
import lab2.trig.SinFunction;
import lab2.trig.TanFunction;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SystemFunctionLayer3Test {

    private static final UnaryFunction COS  = x -> TestPoint.forX(x).cos;
    private static final UnaryFunction SIN  = x -> TestPoint.forX(x).sin;
    private static final UnaryFunction TAN  = x -> TestPoint.forX(x).tan;
    private static final UnaryFunction COT  = x -> TestPoint.forX(x).cot;
    private static final UnaryFunction SEC  = x -> TestPoint.forX(x).sec;
    private static final UnaryFunction CSC  = x -> TestPoint.forX(x).csc;
    private static final UnaryFunction LN  = x -> TestPoint.forX(x).ln;
    private static final UnaryFunction LOG2  = x -> TestPoint.forX(x).log2;
    private static final UnaryFunction LOG3  = x -> TestPoint.forX(x).log3;
    private static final UnaryFunction LOG10  = x -> TestPoint.forX(x).log10;

    @ParameterizedTest
    @MethodSource("lab2.common.stub.TestPoint#primaryStream")
    void layer3_allTrigStubs(TestPoint tp) {

        UnaryFunction ln = new LnFunction(1e-12);
        UnaryFunction log2 = new Log2Function(ln);
        UnaryFunction log3 = new Log3Function(ln);
        UnaryFunction log10 = new Log10Function(ln);

        SystemFunction system = new SystemFunction(
                SIN, COS, TAN, COT, SEC, CSC,
                ln, log2, log3, log10);

        assertEquals(tp.systemValue, system.apply(tp.x), 1e-3);
    }

    @ParameterizedTest
    @MethodSource("lab2.common.stub.TestPoint#primaryStream")
    void layer3_allLogStubs(TestPoint tp) {

        UnaryFunction cos = new CosFunction(1e-12);
        UnaryFunction sin = new SinFunction(cos);
        UnaryFunction tan = new TanFunction(sin, cos);
        UnaryFunction cot = new CotFunction(sin, cos);
        UnaryFunction sec = new SecFunction(cos);
        UnaryFunction csc = new CscFunction(sin); 

        SystemFunction system = new SystemFunction(
                sin, cos, tan, cot, sec, csc,
                LN, LOG2, LOG3, LOG10);

        assertEquals(tp.systemValue, system.apply(tp.x), 1e-3);
    }
}

