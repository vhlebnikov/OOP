package ru.nsu.khlebnikov;

public class CalculatorTest {
    public static void main(String[] args) throws IllegalExpressionException {
        while (true) {
            System.out.println(Calculator.calculate(System.in));
        }
    }
}
