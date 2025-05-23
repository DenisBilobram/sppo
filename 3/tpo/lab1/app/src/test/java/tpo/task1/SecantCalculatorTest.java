package tpo.task1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SecantCalculatorTest {

    static Stream<Arguments> data() {
        return Stream.of(

                Arguments.of(0.0, 1.0),
                Arguments.of(Math.PI / 6, 1 / Math.cos(Math.PI / 6)),
                Arguments.of(-Math.PI / 6, 1 / Math.cos(Math.PI / 6)),
                // Точки в отрицательной ветви
                Arguments.of(Math.PI, 1 / Math.cos(Math.PI)),
                Arguments.of(Math.PI * 5 / 6, 1 / Math.cos(Math.PI * 5 / 6)),
                Arguments.of(Math.PI * 7 / 6, 1 / Math.cos(Math.PI * 7 / 6)),
                // Отрицательное значение >= -π, которое должно нормироваться
                Arguments.of(-Math.PI, -1)
        );
    }

    @ParameterizedTest(name = "Failed for x = {0}")
    @MethodSource("data")
    void testSec(double x, double expected) {
        double actual = SecantCalculator.sec(x);
        assertEquals(expected, actual, 0.01, "Failed for x = " + x);
    }
}
