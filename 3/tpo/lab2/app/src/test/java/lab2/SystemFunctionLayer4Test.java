package lab2;

import lab2.common.UnaryFunction;
import lab2.common.stub.TestPoint;
import lab2.trig.*;
import lab2.log.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SystemFunctionLayer4Test {

    @ParameterizedTest
    @MethodSource("lab2.common.stub.TestPoint#primaryStream")
    void layer4_allReal(TestPoint tp) {

        UnaryFunction cos = new CosFunction(1e-12);
        UnaryFunction sin = new SinFunction(cos);
        UnaryFunction tan = new TanFunction(sin, cos);
        UnaryFunction cot = new CotFunction(sin, cos);
        UnaryFunction sec = new SecFunction(cos);
        UnaryFunction csc = new CscFunction(sin);

        UnaryFunction ln = new LnFunction(1e-12);
        UnaryFunction log2 = new Log2Function(ln);
        UnaryFunction log3 = new Log3Function(ln);
        UnaryFunction log10 = new Log10Function(ln);

        SystemFunction system = new SystemFunction(
                sin, cos, tan, cot, sec, csc,
                ln, log2, log3, log10);

        assertEquals(tp.systemValue, system.apply(tp.x), 1e-3);
    }
}
