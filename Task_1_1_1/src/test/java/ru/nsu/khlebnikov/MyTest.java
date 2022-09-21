package ru.nsu.khlebnikov;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * tests for heapsort.
 */
public class MyTest {
    int[] arr;
    int[] ans;

    // test from task
    @Test
    public void firstTest() {
        arr = new int[]{5, 4, 3, 2, 1};
        ans = new int[]{1, 2, 3, 4, 5};
        HeapSort.heapsort(arr);
        assertArrayEquals(ans, arr);
    }

    // test with already sorted array
    @Test
    public void secondTest() {
        arr = new int[]{1, 2, 2, 5, 7, 10, 10};
        ans = new int[]{1, 2, 2, 5, 7, 10, 10};
        HeapSort.heapsort(arr);
        assertArrayEquals(ans, arr);
    }

    // test with reversed array
    @Test
    public void thirdTest() {
        arr = new int[]{10, 10, 7, 5, 2, 2, 1};
        ans = new int[]{1, 2, 2, 5, 7, 10, 10};
        HeapSort.heapsort(arr);
        assertArrayEquals(ans, arr);
    }

    // test with empty list
    @Test
    public void fourthTest() {
        arr = new int[]{};
        ans = new int[]{};
        HeapSort.heapsort(arr);
        assertArrayEquals(ans, arr);
    }

    // one element array
    @Test
    public void fifthTest() {
        arr = new int[]{5};
        ans = new int[]{5};
        HeapSort.heapsort(arr);
        assertArrayEquals(ans, arr);
    }
}
