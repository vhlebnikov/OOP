package ru.nsu.khlebnikov;

/**
 * Exception for errors are occurred with a calculation expression.
 */
public class IllegalExpressionException extends Exception {
    
    public IllegalExpressionException(String message) {
        super(message);
    }
}
