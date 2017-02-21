package me.beresnev.algorithms.searching;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 20.02.17.
 */
public class BinarySearch {

    /**
     * Binary Search
     * <p>
     * Time complexity:
     * best O(1)
     * worst & average O(logN)
     * Worst space complexity:
     * O(1)
     */
    private BinarySearch() {
    }

    /**
     * Returns the index of target in the arr array. If the target
     * is not in the array, returns -1. We also assume that the array
     * is sorted at this point in time.
     */
    public static int getIndex(int[] arr, int target) {
        if (arr.length == 1)
            return 0; // if there's 1 element, then it's the peak.
        if (target < arr[0] || target > arr[arr.length - 1])
            throw new IllegalArgumentException("Value is not in the array or array is not sorted");
        return binarySearch(arr, target, 0, arr.length - 1);
    }

    /**
     * If we want to find an item in a sorted array, there's no need to do
     * linear search and look at every element. We can narrow our search
     * and get O(logN) complexity. We start with array N = 10, and straight
     * away we look at N/2 element (middle) in the range of [0, n].
     * If elementValue == target, we found it. If not, compare target with
     * elementValue. If elementValue < target, then our target is in the
     * right side of the array. If it's >, then it's on the left side.
     * Now we do the same search, but we look at middle element of the
     * left/right side, where left side - [start, mid - 1], and right
     * side - [mid + 1, end]. We add and subtract 1 because we've seen this
     * element. Now do it until you've hit the target, changing search bounds.
     * <p>
     * Below is pretty much Arrays' class binarySearch method
     *
     * @param toIndex inclusive - watch out for array.length. Should be length - 1
     * @see java.util.Arrays#binarySearch0(int[], int, int, int)
     */
    private static int binarySearch(int[] arr, int target, int fromIndex, int toIndex) {
        int low = fromIndex;
        int high = toIndex;

        // for why (low <= high) look at recursiveBinarySearch javadoc
        while (low <= high) {
            // avoiding computation of average overflow
            int mid = (low + high) >>> 1;
            int midVal = arr[mid];

            if (midVal < target)
                low = mid + 1;
            else if (midVal > target)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -1;
    }

    /**
     * Returns the index of the target in the array.
     * fromIndex and toIndex are bounds to search within.
     * <p>
     * fromIndex can be higher than toIndex if our target is not in
     * the array. This happens because every time we don't find our
     * target, we do mid+1 as our new fromIndex if arr[mid] < target,
     * whilst toIndex stays the same. Hence the difference.
     *
     * @param toIndex inclusive - watch out for array.length. Should be length - 1
     */
    private static int recursiveBinarySearch(int[] arr, int target, int fromIndex, int toIndex) {
        if (fromIndex > toIndex) // can happen if there's no such element in the array
            return -1; // since, if we don't find target, we do mid -1 or mid + 1

        // avoiding computation of average overflow
        int mid = (fromIndex + toIndex) >>> 1;
        int midVal = arr[mid];

        if (midVal < target)
            // go right
            return recursiveBinarySearch(arr, target, mid + 1, toIndex);
        else if (midVal > target)
            return recursiveBinarySearch(arr, target, fromIndex, mid - 1);
        else
            return mid; // target found
    }
}
