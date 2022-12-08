package ru.nsu.khlebnikov;

import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Test for my calculator implementation.
 */
public class CalculatorTest {
    @Test
    public void testFromTask() throws IllegalExpressionException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("input_1.txt");
        Assertions.assertEquals(0.0, Calculator.calculate(stream));
    }
}
