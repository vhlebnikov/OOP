public class Main {
    public static void main(String[] args) {
        int[] arr = {5, 4, 3, 2, 1};
        for (int j : arr) {
            System.out.printf("%d ", j);
        }
        System.out.print("\n");
        heapsort(arr);
        for (int j : arr) {
            System.out.printf("%d ", j);
        }

    }

    private static void swap(int[] arr, int a, int b) {
        int c = arr[a];
        arr[a] = arr[b];
        arr[b] = c;
    }

    private static void heapsort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, i, n);
        }
        for (int i = n - 1; i >= 0; i--) {
            swap(arr, 0, i);
            heapify(arr, 0, i);
        }
    }

    private static void heapify(int[] arr, int i, int n) {
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int max = i;

        if (l < n && arr[l] > arr[max])
            max = l;
        if (r < n && arr[r] > arr[max])
            max = r;
        if (i != max) {
            swap(arr, i, max);
            heapify(arr, max, n);
        }
    }
}