package ru.nsu.khlebnikov;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * tests for heapsort.
 */
public class HeapSortTest {

    @Test
    public void initTest() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] ans = {1, 2, 3, 4, 5};
        HeapSort.heapsort(arr);
        assertArrayEquals(ans, arr);
    }

    @Test
    public void regularArrayTest() {
        int[] arr = {1, 2, 2, 5, 7, 10, 10};
        int[] ans = {1, 2, 2, 5, 7, 10, 10};
        HeapSort.heapsort(arr);
        assertArrayEquals(ans, arr);
    }

    @Test
    public void reversedArrayTest() {
        int[] arr = {10, 10, 7, 5, 2, 2, 1};
        int[] ans = {1, 2, 2, 5, 7, 10, 10};
        HeapSort.heapsort(arr);
        assertArrayEquals(ans, arr);
    }

    @Test
    public void emptyArrayTest() {
        int[] arr = {};
        int[] ans = {};
        HeapSort.heapsort(arr);
        assertArrayEquals(ans, arr);
    }

    @Test
    public void oneElementArrayTest() {
        int[] arr = {5};
        int[] ans = {5};
        HeapSort.heapsort(arr);
        assertArrayEquals(ans, arr);
    }
}
