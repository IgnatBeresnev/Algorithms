package me.beresnev.algorithms.sorting;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 18.02.17.
 */
public class HeapSort {

    /**
     * Heap sort
     * <p>
     * Time complexity:
     * O(n log n),
     * buildMaxHeap - O(n)
     * heapify O(log n)
     * Space complexity:
     * O(1)
     */
    private HeapSort() {
    }

    /**
     * In a max heap parent must be always > than children. So every
     * time we get the root [0], we know it'll be the biggest number
     * in the heap. So we take the biggest number and put it in the
     * end of the array, decrease the total number, so that we ignore
     * that last N elements (they're sorted), and run heapify to find
     * the new root (biggest number) and place it at [0]. Repeat.
     */
    public static void sort(int[] arr) {
        int totalLeft = arr.length - 1;
        buildMaxHeap(arr, totalLeft);

        for (int i = totalLeft; i > 0; i--) {
            swap(arr, 0, i);
            totalLeft--;
            heapify(arr, 0, totalLeft);
        }
    }

    /**
     * Builds a max heap from the array. Parents must be > than children.
     * Why do we start from total / 2? Because that is the last level
     * of nodes. Below are leaves only. So we start swapping from there.
     * <p>
     * Time complexity of this - O(n) through careful analysis. We start
     * with the last level of nodes, swap everything (constant), and then
     * as we get higher, the number of leaves to be swapped increases x2,
     * but there are < 2 nodes every level. So it's
     *
     * @param arr unsorted input array
     * @see me.beresnev.datastructures.trees.MaxHeap
     */
    private static void buildMaxHeap(int[] arr, int totalLeft) {
        for (int i = totalLeft / 2; i >= 0; i--) {
            heapify(arr, i, totalLeft);
        }
    }

    /**
     * A typical max-heap heapify from the Heap data structure.
     * Time complexity: O(logN) - height of the tree.
     *
     * @param branch index of the selected branch
     * @see me.beresnev.datastructures.trees.Heap#heapify(int)
     */
    private static void heapify(int[] arr, int branch, int totalLeft) {
        int leftChild = 2 * branch;
        int rightChild = leftChild + 1;

        int largestValueIndex = branch;
        if (leftChild <= totalLeft && arr[leftChild] > arr[branch]) {
            largestValueIndex = leftChild;
        }

        if (rightChild <= totalLeft && arr[rightChild] > arr[largestValueIndex]) {
            largestValueIndex = rightChild;
        }

        if (largestValueIndex != branch) {
            swap(arr, branch, largestValueIndex);
            heapify(arr, largestValueIndex, totalLeft);
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
