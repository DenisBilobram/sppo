package tpo.lab1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SecantCalculatorTest {

    @Test
    public void testSecAtZero() {
        double x = 0.0;
        double expected = 1.0;
        double actual = SecantCalculator.sec(x);
        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void testSecAtPiOverSix() {
        double x = Math.PI / 6;
        double expected = 1 / Math.cos(x);
        double actual = SecantCalculator.sec(x);
        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void testSecAtPiOverFour() {
        double x = Math.PI / 4;
        double expected = 1 / Math.cos(x);
        double actual = SecantCalculator.sec(x);
        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void testSecAtPiOverThree() {
        double x = Math.PI / 3;
        double expected = 1 / Math.cos(x);
        double actual = SecantCalculator.sec(x);
        assertEquals(expected, actual, 0.1);
    }

    @Test
    public void testSecAtNegativePiOverSix() {
        double x = -Math.PI / 6;
        double expected = 1 / Math.cos(x);
        double actual = SecantCalculator.sec(x);
        assertEquals(expected, actual, 0.01);
    }
}