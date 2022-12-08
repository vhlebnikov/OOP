package ru.nsu.khlebnikov;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * My calculator implementation.
 * Have a single private static method, that can calculate prefix expressions.
 */
public class Calculator {

    private static final Deque<String> stack = new ArrayDeque<>();

    /**
     * Method that calculates the value of prefix expression.
     *
     * @param stream the stream with an input data
     * @return the answer being a double number
     * @throws IllegalExpressionException the exception thrown in case of an error
     */
    public static Double calculate(InputStream stream) throws IllegalExpressionException {
        Scanner sc = new Scanner(stream);
        String input = sc.nextLine();
        if (input.isEmpty()) {
            throw new NoSuchElementException("Пустое выражение");
        }
        List<String> elements = new ArrayList<>(Arrays.asList(input.split(" ")));
        Collections.reverse(elements);

        for (String elem : elements) {
            if (isNumber(elem)) {
                stack.push(elem);
                continue;
            }
            calculateOperation(elem);
        }
        if (stack.size() != 1) {
            throw new IllegalExpressionException("Illegal expression");
        }
        return Double.parseDouble(stack.pop());
    }

    /**
     * Method that checks if string can be parsed into a double number.
     *
     * @param str the input string
     * @return true if can and otherwise false
     */
    private static boolean isNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Method that checks operation arity.
     *
     * @param op the name of operation
     * @return the operation arity
     */
    private static int operationArity(String op) {
        switch (op) {
            case "+", "-", "*", "/", "pow" -> {
                return 2;
            }
            case "log", "sqrt", "sin", "cos" -> {
                return 1;
            }
            default -> throw new IllegalArgumentException("Illegal operation");
        }
    }

    /**
     * Method that applies operation to its arguments.
     *
     * @param op the operation
     * @throws IllegalExpressionException the exception thrown in case of an error
     */
    private static void calculateOperation(String op) throws IllegalExpressionException {
        Deque<Double> arguments = new ArrayDeque<>();
        int opArity = operationArity(op);
        if (stack.size() < opArity) {
            throw new IllegalExpressionException("Insufficiently of arguments");
        }
        for (int i = 0; i < opArity; i++) {
            arguments.addLast(Double.parseDouble(stack.pop()));
        }
        switch (op) {
            case "+" -> stack.push(String.valueOf(arguments.pop() + arguments.pop()));
            case "-" -> stack.push(String.valueOf(arguments.pop() - arguments.pop()));
            case "*" -> stack.push(String.valueOf(arguments.pop() * arguments.pop()));
            case "/" -> stack.push(String.valueOf(arguments.pop() / arguments.pop()));
            case "pow" -> stack.push(String.valueOf(Math.pow(arguments.pop(), arguments.pop())));
            case "log" -> stack.push(String.valueOf(Math.log(arguments.pop())));
            case "sqrt" -> stack.push(String.valueOf(Math.sqrt(arguments.pop())));
            case "sin" -> stack.push(String.valueOf(Math.sin(arguments.pop())));
            case "cos" -> stack.push(String.valueOf(Math.cos(arguments.pop())));
            default -> throw new IllegalArgumentException("Illegal operation");
        }
    }
}
