package me.beresnev.algorithms.sorting;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 18.02.17.
 */
public class MergeSort {


    /**
     * Merge sort
     * <p>
     * Time complexity:
     * O(n log n). height + #leaves?
     * Worst space complexity:
     * O(n)
     * <p>
     * + Effective with big arrays (>1000 elements)
     * - Requires a lot of space
     */
    private MergeSort() {
    }

    /**
     * Sorts the input array as opposed to returning it.
     * If you want to return a sorted array,
     *
     * @param arr input array
     * @see #sortReturn(int[])
     */
    public static void sort(int[] arr) {
        if (arr.length < 2) return;

        int[] temp = new int[arr.length];
        mergesort(arr, temp, 0, arr.length - 1);
    }

    /**
     * "Divides" the initial array into smaller part to then combine them. Only
     * instead of the actual dividing, it just divides the bounds (indexes) that
     * represent a virtual array.
     *
     * @see #mergeReturn(int[], int[]) for better understanding.
     */
    private static void mergesort(int[] arr, int[] temp, int low, int high) {
        if (low < high) {
            // arr.length and temp.length never change, causing infinite recursive loop and stackOverFlow
            int mid = (low + high) / 2;
            mergesort(arr, temp, low, mid);
            mergesort(arr, temp, mid + 1, high);
            merge(arr, temp, low, mid, high);
        }
    }

    /**
     * Same as mergeReturn, the only difference is that instead of passing arrays,
     * we pass indexes (that is more confusing, tbh). We have pointers that "divide"
     * our initial array into virtual "left" and "right", where left arrays's
     * bounds are [lower, middle] and right array's bounds are [middle + 1, high]
     * <p>
     * How merging magic happens: imagine we have a right array and a left array.
     * We need "sort" them and put the result into the merged array (in our case, temp)
     * For instance, our left array is [1, 6, 10] and our right array is [3, 4, 5]
     * Put two fingers (pointers) on the first elements of arrays and compare them.
     * Put the smallest elem in the merged array. If this elements was from the left array,
     * move left finger (pointer) to the right. If it was the right one, move the other.
     * Do this until one of your pointers > size of that pointer's array. Then copy
     * the remaining element of the other array into the merged array. It'll be bigger
     * than the rest, since it's the only one left after all comparisons.
     * <p>
     * For example, [(1), 6, 10] and [(3), 4, 5]. Compare 1 and 3. 1 is less, put 1
     * into merged array. Move left (pointer) to the right once. Merged (temp): [1]
     * Continuing: [1, (6), 10] and [(3), 4, 5]. Compare 6 and 3. 3 is less, put 3
     * into merged array. Move right (pointer) to the right once. Merged: [1, 3]
     * Continuing: [1, (6), 10] and [3, (4), 5]. Compare 6 and 4. 4 is less...
     * ... by the end: [1, 6, (10)] and [3, 4, 5] (high+1). Put 10 to the end of merged.
     *
     * @param arr    initial input array
     * @param temp   temp array to store merged results
     * @param low    lower index of "left" array
     * @param middle high index of "left array. Middle + 1 = lower index of "right" array
     * @param high   high index of "right" array
     */
    private static void merge(int[] arr, int[] temp, int low, int middle, int high) {
        int leftPointer = low, rightPointer = middle + 1;

        for (int i = low; i <= high; i++) {
            if (leftPointer <= middle && rightPointer <= high) {
                if (arr[leftPointer] < arr[rightPointer]) { // TODO: Check stability if change < to =<
                    temp[i] = arr[leftPointer++];
                } else {
                    temp[i] = arr[rightPointer++];
                }
            } else if (leftPointer <= middle) { // докладываем последний элемент
                temp[i] = arr[leftPointer++];
            } else {
                temp[i] = arr[rightPointer++];
            }
        }
        System.arraycopy(temp, 0, arr, 0, high + 1);
    }

    /**
     * Returns the sorted array, as opposed to sorting the input one.
     * Either way, it needs O(n) space. The only difference is the return type.
     *
     * @param arr input array
     * @return sorted arr
     */
    public static int[] sortReturn(int[] arr) {
        if (arr.length < 2) return arr;

        int mid = arr.length / 2;
        int[] left = new int[mid];
        int[] right = new int[mid + arr.length % 2];

        int j = 0; // для добавления b
        for (int i = 0; i < arr.length; i++) {
            if (i < mid) {
                left[i] = arr[i];
            } else {
                right[j++] = arr[i];
            }
        }
        // В первый раз считывает, пока размер левого и правого не будет по одному.
        // Потом склеивает левый и правый.
        return mergeReturn(sortReturn(left), sortReturn(right));
    }

    /**
     * @param leftArr  left part of the initial array
     * @param rightArr right part of the initial array
     * @return merged sorted array from leftArr and rightArr
     * @see #mergesort(int[], int[], int, int)  for detailed explanation of how it works
     */
    private static int[] mergeReturn(int[] leftArr, int[] rightArr) {
        int leftPointer = 0, rightPointer = 0, combinedSize = leftArr.length + rightArr.length;
        int[] merged = new int[combinedSize];

        for (int i = 0; i < combinedSize; i++) {
            if (leftPointer < leftArr.length && rightPointer < rightArr.length) {
                if (leftArr[leftPointer] < rightArr[rightPointer]) {
                    merged[i] = leftArr[leftPointer++];
                } else {
                    merged[i] = rightArr[rightPointer++];
                }
            } else if (leftPointer < leftArr.length) { // докладываем последний элемент
                merged[i] = leftArr[leftPointer++];
            } else {
                merged[i] = rightArr[rightPointer++];
            }
        }
        return merged;
    }
}
