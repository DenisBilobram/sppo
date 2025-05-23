package lab2;

import lab2.common.UnaryFunction;
import lab2.common.stub.TestPoint;
import lab2.trig.*;
import lab2.log.*;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SystemFunctionLayer1Test {

    @ParameterizedTest
    @MethodSource("lab2.common.stub.TestPoint#primaryStream")
    void layer1_onlyCosMocked(TestPoint tp) {

        UnaryFunction cosMock = mock(UnaryFunction.class);

        when(cosMock.apply(anyDouble()))
        .thenAnswer(inv -> TestPoint.forX(inv.getArgument(0)).cos);

        UnaryFunction sin = new SinFunction(cosMock);
        UnaryFunction tan = new TanFunction(sin, cosMock);
        UnaryFunction cot = new CotFunction(sin, cosMock);
        UnaryFunction sec = new SecFunction(cosMock);
        UnaryFunction csc = new CscFunction(sin);

        UnaryFunction ln    = new LnFunction(1e-12);
        UnaryFunction log2  = new Log2Function(ln);
        UnaryFunction log3  = new Log3Function(ln);
        UnaryFunction log10 = new Log10Function(ln);

        SystemFunction system = new SystemFunction(
                sin, cosMock, tan, cot, sec, csc,
                ln, log2, log3, log10);

        assertEquals(tp.systemValue, system.apply(tp.x), 1e-3,
                    () -> "Layer1 fail on x=" + tp.x);

        if (tp.x <= 0) {
            verify(cosMock, atLeastOnce()).apply(tp.x);
        } else {
            verify(cosMock, never()).apply(tp.x);
        }
    }
    @ParameterizedTest
    @MethodSource("lab2.common.stub.TestPoint#primaryStream")
    void layer1_onlyLnMocked(TestPoint tp) {

        UnaryFunction cos = new CosFunction(1e-12);

        UnaryFunction sin = new SinFunction(cos);
        UnaryFunction tan = new TanFunction(sin, cos);
        UnaryFunction cot = new CotFunction(sin, cos);
        UnaryFunction sec = new SecFunction(cos);
        UnaryFunction csc = new CscFunction(sin);

        UnaryFunction lnMock = mock(UnaryFunction.class);

        when(lnMock.apply(anyDouble()))
        .thenAnswer(inv -> TestPoint.forX(inv.getArgument(0)).ln);

        UnaryFunction log2  = new Log2Function(lnMock);
        UnaryFunction log3  = new Log3Function(lnMock);
        UnaryFunction log10 = new Log10Function(lnMock);

        SystemFunction system = new SystemFunction(
                sin, cos, tan, cot, sec, csc,
                lnMock, log2, log3, log10);

        assertEquals(tp.systemValue, system.apply(tp.x), 1e-3,
                    () -> "Layer1 fail on x=" + tp.x);

        if (tp.x > 0) {
            verify(lnMock, atLeastOnce()).apply(tp.x);
        } else {
            verify(lnMock, never()).apply(tp.x);
        }
    }

}

