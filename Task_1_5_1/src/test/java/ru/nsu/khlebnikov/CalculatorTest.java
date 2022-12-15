package ru.nsu.khlebnikov;

import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.lang.ArithmeticException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Test for my calculator implementation.
 */
public class CalculatorTest {
    @Test
    public void testFromTask() throws IllegalExpressionException, IOException {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("input_1.txt")) {
            Assertions.assertEquals(0.0, Calculator.calculate(stream));
        }
    }

    @Test
    public void bigExpressionTest() throws IllegalExpressionException, IOException {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("big_expression.txt")) {
            Double number = Calculator.calculate(stream) * 1000000;
            number = (double) Math.round(number);
            number /= 1000000;
            Assertions.assertEquals(0.226806, number);

        }
    }

    @Test
    public void exceptionTest() throws IllegalExpressionException, IOException {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("division_by_zero.txt")) {
            Assertions.assertTrue(Double.isInfinite(Calculator.calculate(stream)));
        }
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("empty_input.txt")) {
            Throwable exception =
                    assertThrows(NoSuchElementException.class, () -> Calculator.calculate(stream));
            Assertions.assertEquals(exception.getMessage(), "Пустое выражение");
        }
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("illegal_expression.txt")) {
            Throwable exception =
                    assertThrows(IllegalExpressionException.class, () -> Calculator.calculate(stream));
            Assertions.assertEquals(exception.getMessage(), "Illegal expression");
        }
        try(InputStream stream = getClass().getClassLoader().getResourceAsStream("illegal_argument.txt")) {
            Throwable exception =
                    assertThrows(IllegalArgumentException.class, () -> Calculator.calculate(stream));
            Assertions.assertEquals(exception.getMessage(), "Illegal operation");
        }
    }
}
