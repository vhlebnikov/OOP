package ru.nsu.khlebnikov;

import java.util.Arrays;
import java.util.Objects;

/**
 * A stack is linear data structure,
 * elements are stacked on top of each other.
 * It works according to the principle - last in, first out (LIFO).
 */
public class Stack<T> {
    private int size;
    private int capacity;
    public T[] arr;

    /**
     * Initial stack constructor.
     *
     * @param capacity capacity of stack
     */
    public Stack(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.arr = (T[]) new Object[capacity];
    }

    /**
     * "push" method puts element to the end of the stack.
     *
     * @param elem new element, that will be inserted in the stack
     */
    public void push(T elem) {
        if (size == capacity) {
            realloc();
        }
        arr[size++] = elem;
    }

    /**
     * "pushStack" method puts another stack at the end of stack.
     *
     * @param stackToPush stack, that will be inserted in the stack
     */
    public void pushStack(Stack<T> stackToPush) {
        for (int i = 0; i < stackToPush.size; i++) {
            push(stackToPush.arr[i]);
        }
    }

    /**
     * "pop" method deletes the last element of the stack and returns it.
     * If stack is empty, method returns "null".
     *
     * @return popped element, if it exists (in this case it will return "null")
     */
    public T pop() {
        if (size != 0) {
            T poppedElem = arr[--size];
            arr[size] = null;
            return poppedElem;
        } else {
            return null;
        }
    }

    /**
     * "popStack" method deletes the last "count" elements ant puts them into new stack,
     * then returns it.
     *
     * @param count count of elements, that will be deleted from the end of initial stack
     *              and will be putted in the returned stack
     * @return stack of deleted elements
     */
    public Stack<T> popStack(int count) {
        Stack<T> resultStack;
        int minSize = Math.min(count, size);
        resultStack = new Stack<T>(minSize);
        resultStack.size = minSize;
        for (int i = minSize - 1; i >= 0; i--) {
            resultStack.arr[i] = pop();
        }
        return resultStack;
    }

    /**
     * "count" method returns size of stack.
     *
     * @return size of stack
     */
    public int count() {
        return size;
    }

    /**
     * Overridden "equals" method.
     *
     * @param elem the element being compared
     * @return returns true if two stacks are equal, else returns false
     */
    public boolean equals(Object elem) {
        if (this == elem) {
            return true;
        }
        if (elem == null || getClass() != elem.getClass()) {
            return false;
        }
        Stack<?> expected = (Stack<?>) elem;
        if (size != expected.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (arr[i] != expected.arr[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Overridden "hashCode" method.
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(size, capacity);
        result = 31 * result + Arrays.hashCode(arr);
        return result;
    }

    /**
     * "realloc" method increases capacity of the stack,
     * capacity multiplies by 1.5.
     */
    private void realloc() {
        capacity = 3 * capacity / 2 + 1; // +1 - to make a stack with a capacity of 1 element work
        arr = Arrays.copyOf(arr, capacity);

    }
}
