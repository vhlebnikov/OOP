package ru.nsu.khlebnikov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for my stack realization.
 */
public class StackTest {

    @Test
    public void initTest() {
        Stack expected = new Stack(1);
        expected.push(2);

        Stack stack = new Stack(1);
        stack.push(2);
        stack.push(7);
        Stack s1 = new Stack(2);
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
        Stack expected = new Stack(10);
        Stack stack = new Stack(1);

        for (int i = 0; i < 10; i++) {
            expected.push(i);
            stack.push(i);
        }

        Assertions.assertTrue(stack.stackAssertion(expected));
    }

    @Test
    public void popStackTest() {
        Stack stack = new Stack(4);
        Stack stack1 = new Stack(4);
        Stack expected = new Stack(0);
        Stack expected1 = new Stack(4);

        for (int i = 0; i < 4; i++) {
            stack.push(i);
            stack1.push(i);
        }
        expected1 = stack.popStack(10);

        Assertions.assertTrue(stack1.stackAssertion(expected1));
        Assertions.assertTrue(stack.stackAssertion(expected));
    }

    @Test
    public void emptyStackTest() {
        Stack stack = new Stack(0);
        Stack expected = new Stack(0);
        Stack empty = new Stack(0);

        stack.pushStack(empty);
        empty.pushStack(empty);
        stack.pushStack(empty);
        stack.pushStack(stack);

        Assertions.assertTrue(stack.stackAssertion(expected));
    }
}
