package me.beresnev.algorithms.searching;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 18.02.17.
 */
public class ArrayPeak {

    /**
     * Finds a peak in a sorted array.
     * Time complexity:
     * O(logN) - binary Search
     *
     * @see MatrixPeak to find 2D peak
     */
    private ArrayPeak() {
    }

    /**
     * We assume that the array is sorted at this point.
     *
     * @return peak, whose value is >= of both neighbours.
     */
    public static int find(int[] arr) {
        if (arr.length == 0) return -1;
        return binaryPeakSearch(arr, 0, arr.length - 1);
    }

    /**
     * Basically a binary search without a target. We just compare
     * mid element to it's neighbours, and return it if satisfies.
     *
     * @see me.beresnev.algorithms.searching.BinarySearch#binarySearch(int[], int, int, int)
     */
    private static int binaryPeakSearch(int[] arr, int fromIndex, int toIndex) {
        int low = fromIndex;
        int high = toIndex;

        while (low <= high) {
            // avoiding computation of average overflow
            int mid = (low + high) >>> 1;
            int midVal = arr[mid];

            if (mid == fromIndex || mid == toIndex) {
                // if we hit either side in sorted array, it's a peak
                return midVal;
            }


            if (midVal >= arr[mid - 1] && midVal >= arr[mid + 1])
                return midVal; // satisfies peak definition

            if (midVal <= arr[mid + 1])
                low = mid + 1;
            else if (midVal <= arr[mid - 1])
                high = mid - 1;
        }
        return -1;
    }
}
