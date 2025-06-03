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

class SystemFunctionLayer2Test {

    @ParameterizedTest
    @MethodSource("lab2.common.stub.TestPoint#primaryStream")
    void layer2_cosSinCscMocked(TestPoint tp) {

        UnaryFunction cosMock = mock(UnaryFunction.class);
        UnaryFunction sinMock = mock(UnaryFunction.class);
        UnaryFunction secMock = mock(UnaryFunction.class);

        when(cosMock.apply(anyDouble())).thenAnswer(inv -> TestPoint.forX(inv.getArgument(0)).cos);
        when(sinMock.apply(anyDouble())).thenAnswer(inv -> TestPoint.forX(inv.getArgument(0)).sin);
        when(secMock.apply(anyDouble())).thenAnswer(inv -> TestPoint.forX(inv.getArgument(0)).sec);

        UnaryFunction tan = new TanFunction(sinMock, cosMock);
        UnaryFunction cot = new CotFunction(sinMock, cosMock);
        UnaryFunction csc = new CscFunction(sinMock);

        UnaryFunction ln = new LnFunction(1e-12);
        UnaryFunction log2 = new Log2Function(ln);
        UnaryFunction log3 = new Log3Function(ln);
        UnaryFunction log10 = new Log10Function(ln);

        SystemFunction system = new SystemFunction(
                sinMock, cosMock, tan, cot, secMock, csc,
                ln, log2, log3, log10);

        assertEquals(tp.systemValue, system.apply(tp.x), 1e-2);

        if (tp.x <= 0) {
            verify(cosMock, atLeastOnce()).apply(anyDouble());
            verify(sinMock, atLeastOnce()).apply(anyDouble());
            verify(secMock, atLeastOnce()).apply(anyDouble());
        } else {
            verify(cosMock, never()).apply(anyDouble());
            verify(sinMock, never()).apply(anyDouble());
            verify(secMock, never()).apply(anyDouble());
        }
    }

    @ParameterizedTest
    @MethodSource("lab2.common.stub.TestPoint#primaryStream")
    void layer2_cosLnMocked(TestPoint tp) {

        UnaryFunction cosMock = mock(UnaryFunction.class);
        UnaryFunction sin = new SinFunction(cosMock);
        UnaryFunction csc = new CscFunction(sin);

        when(cosMock.apply(anyDouble())).thenAnswer(inv -> TestPoint.forX(inv.getArgument(0)).cos);

        UnaryFunction tan = new TanFunction(sin, cosMock);
        UnaryFunction cot = new CotFunction(sin, cosMock);
        UnaryFunction sec = new SecFunction(cosMock);

        UnaryFunction lnMock = mock(UnaryFunction.class);
        when(lnMock.apply(anyDouble())).thenAnswer(inv -> TestPoint.forX(inv.getArgument(0)).ln);

        UnaryFunction log2 = new Log2Function(lnMock);
        UnaryFunction log3 = new Log3Function(lnMock);
        UnaryFunction log10 = new Log10Function(lnMock);

        SystemFunction system = new SystemFunction(
                sin, cosMock, tan, cot, sec, csc,
                lnMock, log2, log3, log10);

        assertEquals(tp.systemValue, system.apply(tp.x), 1e-3);

        if (tp.x <= 0) {
            verify(cosMock, atLeastOnce()).apply(anyDouble());
        } else {
            verify(lnMock, atLeastOnce()).apply(anyDouble());
        }
    }
}
