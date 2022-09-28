package ru.nsu.khlebnikov;

/**
 * heap-based sorting.
 */
public class HeapSort {

    /**
     * swaps array elements.
     *
     * @param arr input array
     * @param a first element
     * @param b second element
     */
    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * heapify makes that for each subtree the root is bigger than its sons.
     *
     * @param arr input array (binary tree)
     * @param i root of subtree
     * @param n conditional size of binary tree
     */
    private static void heapify(int[] arr, int i, int n) {
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int max = i;

        if (l < n && arr[l] > arr[max]) {
            max = l;
        }
        if (r < n && arr[r] > arr[max]) {
            max = r;
        }
        if (i != max) {
            swap(arr, i, max);
            heapify(arr, max, n);
        }
    }

    /**
     * main heapsort function.
     *
     * @param arr input array that will be sorted
     */
    public static void heapsort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, i, n);
        }
        for (int i = n - 1; i >= 0; i--) {
            swap(arr, 0, i);
            heapify(arr, 0, i);
        }
    }
}
