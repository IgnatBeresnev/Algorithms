package me.beresnev.algorithms.sorting;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 18.02.17.
 */
public class InsertionSort {

    /**
     * Insertion sort
     * <p>
     * Time complexity:
     * best O(n)
     * worst & average O(n^2)
     * Worst space complexity:
     * O(1)
     * <p>
     * + Requires little space
     * - Not effective with big arrays (>100 elements)
     */
    private InsertionSort() {
    }

    /**
     * Walks through the input array from left to right. Compares current element
     * with it's neighbour on the left. If neighbour is bigger, then order is
     * fucked up and we need to move our current element to the right place.
     *
     * @param arr input array
     * @see #findPlace(int[], int)
     * <p>
     * After i iterations, first i elements are sorted.
     * Elements are bubbled into the sorted left part.
     * Faster (~x5) than bubble sort (< operations), which does the opposite (bubble out).
     * @see me.beresnev.algorithms.sorting.BubbleSort
     * <p>
     * Notice that we start with index 1. It's because we'll be comparing with
     * the left neighbour (i - 1) straight away. Don't want to get NPE, do we?
     */
    public static void sort(int[] arr) {
        if (arr.length < 2) return;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                findPlace(arr, i);
            }
        }
    }

    /**
     * We move backwards in the same list (from the position of j) until left
     * neighbour is <= than our current element. On our way, if we see that
     * left neighbour is > than our current element, we swap those elements
     * to achieve the ascending (natural) order.
     *
     * @param arr initial input array
     * @param j   index of the element to start countdown from
     */
    private static void findPlace(int[] arr, int j) {
        for (int i = j; i > 0; i--) {
            if (arr[i] >= arr[i - 1]) return;
            swap(arr, i, i - 1);
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
