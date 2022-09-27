package ru.nsu.khlebnikov;

import java.util.Arrays;

/**
 * A stack is linear data structure,
 * elements are stacked on top of each other.
 * It works according to the principle - last in, first out (LIFO).
 */
public class Stack {
    private int size;
    private int capacity;
    public Object[] arr;

    /**
     * Initial stack constructor.
     *
     * @param capacity capacity of stack
     */
    public Stack(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.arr = new Object[capacity];
    }

    /**
     * "push" method puts element to the end of the stack.
     *
     * @param elem new element, that will be inserted in the stack
     */
    public void push(Object elem) {
        if (size == capacity) {
            realloc();
        }
        arr[size++] = elem;
    }

    /**
     * "realloc" method increases capacity of the stack,
     * capacity is doubled.
     */
    private void realloc() {
        capacity *= 2;
        arr = Arrays.copyOf(arr, capacity);

    }

    /**
     * "pushStack" method puts another stack at the end of stack.
     *
     * @param stackToPush stack, that will be inserted in the stack
     */
    public void pushStack(Stack stackToPush) {
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
    public Object pop() {
        if (size != 0) {
            Object poppedElem = arr[--size];
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
    public Stack popStack(int count) {
        Stack resultStack;
        if (count <= size) {
            resultStack = new Stack(count);
            resultStack.size = count;
            for (int i = count - 1; i >= 0; i--) {
                resultStack.arr[i] = pop();
            }
        } else {
            resultStack = new Stack(size);
            resultStack.size = size;
            for (int i = size - 1; i >= 0; i--) {
                resultStack.arr[i] = pop();
            }
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
     * "stackAssertion" method is used for compare two stacks
     * for equality and returns boolean value,
     * whether the stacks are equal or not.
     *
     * @param expected expected answer (second stack)
     * @return boolean value, that means equality (or not) of two stacks
     */
    public boolean stackAssertion(Stack expected) {
        if (count() != expected.count()) {
            return false;
        } else {
            for (int i = 0; i < count(); i++) {
                if (arr[i] != expected.arr[i]) {
                    return false;
                }
            }
        }
        return true;
    }
}
