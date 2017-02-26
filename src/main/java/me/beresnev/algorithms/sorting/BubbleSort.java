package me.beresnev.algorithms.sorting;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 18.02.17
 */
public class BubbleSort {

    /**
     * Bubble sort
     * <p>
     * Time complexity:
     * best O(n)
     * worst & average O(n^2)
     * Worst space complexity:
     * O(1)
     * <p>
     * Basically loops through the entire array, using 2 for loops. In the inner,
     * if left element is bigger than the right element, it swaps them, and then
     * continues dragging that element to the right until right neighbour is >.
     * Then, if right neighbour is bigger, we start dragging that element to the end.
     * We repeat that N times, where N - size of the array, to make sure it's sorted.
     * <p>
     * After i iterations, the last i elements are the biggest, and ordered.
     * Bigger elements are bubbled out to the right part of the array (the end).
     * Slower than insertion sort (~5 times), which does the opposite (bubble in to left).
     *
     * @see me.beresnev.algorithms.sorting.InsertionSort
     */
    private BubbleSort() {
    }

    /**
     * Slightly optimized version of bubble sort:
     * + If no swaps occurred (flag), no point in going through the array again,
     * since the list is already sorted at this point.
     * + Also, because it bubbles out biggest elements to the end, with each iteration
     * we'll have to check less elements, since the last ones are already sorted.
     * This way with n = 11, we're checking 66 elements instead of 132 (if we check everything)
     */
    public static void sort(int[] arr) {
        if (arr.length < 2) return; // 1 element is already sorted

        for (int i = 0; i < arr.length; i++) {
            boolean swapFlag = false;

            // optimized checking range arr.length - i here
            // no need to check the end of the array, it's already sorted.
            for (int j = 1; j < arr.length - i; j++) {
                if (arr[j - 1] > arr[j]) {
                    swap(arr, j - 1, j);
                    swapFlag = true;
                }
            }

            if (!swapFlag) return;
        }
    }

    /**
     * Not optimized bubble sort. You can compare
     * the number of operations and time taken.
     * The increase is not that great, but better than nothing.
     */
    public static void notOptimizedSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j < arr.length; j++) {
                if (arr[j - 1] > arr[j]) {
                    swap(arr, j - 1, j);
                }
            }
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
