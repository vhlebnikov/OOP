package ru.nsu.khlebnikov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for my stack realization.
 */
public class StackTest {

    @Test
    public void initTest() {
        Stack<Integer> expected = new Stack<Integer>(1);
        expected.push(2);

        Stack<Integer> stack = new Stack<Integer>(1);
        stack.push(2);
        stack.push(7);
        Stack<Integer> s1 = new Stack<Integer>(2);
        s1.push(4);
        s1.push(8);
        stack.pushStack(s1);
        stack.pop();
        stack.popStack(2);
        stack.count();

        Assertions.assertTrue(stack.stackAssertion(expected));
    }

    @Test
    public void popTest() { // testing work of realloc
        Stack<Integer> expected = new Stack<Integer>(10);
        Stack<Integer> stack = new Stack<Integer>(1);

        for (int i = 0; i < 10; i++) {
            expected.push(i);
            stack.push(i);
        }

        Assertions.assertTrue(stack.stackAssertion(expected));
    }

    @Test
    public void popStackTest() {
        Stack<Integer> stack = new Stack<Integer>(4);
        Stack<Integer> stack1 = new Stack<Integer>(4);

        for (int i = 0; i < 4; i++) {
            stack.push(i);
            stack1.push(i);
        }

        Stack<Integer> expected = new Stack<Integer>(0);
        Stack<Integer> expected1 = new Stack<Integer>(4);
        expected1 = stack.popStack(10);

        Assertions.assertTrue(stack1.stackAssertion(expected1));
        Assertions.assertTrue(stack.stackAssertion(expected));
    }

    @Test
    public void emptyStackTest() {
        Stack<Integer> stack = new Stack<Integer>(0);
        Stack<Integer> empty = new Stack<Integer>(0);

        stack.pushStack(empty);
        empty.pushStack(empty);
        stack.pushStack(empty);
        stack.pushStack(stack);

        Stack<Integer> expected = new Stack<Integer>(0);
        Assertions.assertTrue(stack.stackAssertion(expected));
    }

    @Test
    public void stringStackTest() {
        Stack<String> expected = new Stack<String>(1);
        expected.push("2");

        Stack<String> stack = new Stack<String>(4);
        stack.push("2");
        stack.push("7");
        Stack<String> s1 = new Stack<String>(2);
        s1.push("4");
        s1.push("8");
        stack.pushStack(s1);
        stack.pop();
        stack.popStack(2);
        stack.count();
        Assertions.assertTrue(stack.stackAssertion(expected));
    }
}
