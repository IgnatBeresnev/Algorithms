package me.beresnev.algorithms.sorting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 27.02.17.
 */
public class BucketSort {

    /**
     * Time complexity:
     * - Best & Average O(n+k), where N - size, k - # of buckets
     * - Worst = complexity of used sorting for buckets. Typically
     * insertion sort is used, so the worst case is O(n^2)
     * <p>
     * Space complexity:
     * - O(n+k)
     * <p>
     * Best O(n) when values of the array are uniformly distributed across
     * the buckets. So values within the array have to be VERY different.
     * - If values within the array are close together, we have worst-case,
     * since they will be put in 1 bucket, which we will sort later on.
     * - If array.size = range of values, use Pigeonhole sort for efficiency.
     * - If all buckets.size==1, bucketsort == counting sort, only with
     * less space consumption O(#buckets) instead of O(maxValue)
     * <p>
     * The idea is very simple: We have N elements, and we put those
     * elements in M buckets. We determine the element's bucket by
     * some formula, for more details look at implementations below.
     * These formulas find such a bucket that is appropriate for the
     * element's value. Smaller elements are closer to the beginning,
     * bigger - closer to the end. Therefore, smallest elem - 1st bucket,
     * biggest element - last bucket. Then we iterate through the buckets
     * and put all elements from it into the arraylist (one after another).
     * <p>
     * Implementations differ on the formula and on ways of
     * resolving collisions, since they have to be sorted as well.
     * Also, my implementation has bucket size of arr.length / 2
     */
    private BucketSort() {
    }


    /**
     * General logic is the same. There are 2 differences:
     * - To determine the bucket the "interval" variable is used, which takes
     * min and max values (so we have to find them beforehand in a loop)
     * - It adds values to the end of the bucket. Then, when it's time to
     * iterate through that bucket, it does linkedList->Array, and sorts
     * that array with insertion sort. In this case insertion sort is rather
     * fast, since values within the bucket are very close value-wise.
     * TODO: Try to improve interval and .toArray()
     */
    public static void sort(int[] arr) {
        int minValue = arr[0];
        int maxValue = arr[0];
        for (int value : arr) {
            if (value < minValue)
                minValue = value;
            else if (value > maxValue)
                maxValue = value;
        }

        int bucketsSize = arr.length / 2;
        List<LinkedList<Integer>> buckets = new ArrayList<>(bucketsSize);
        for (int i = 0; i < bucketsSize; i++) {
            buckets.add(new LinkedList<Integer>());
        }

        double interval = ((double) (maxValue - minValue + 1)) / buckets.size();
        for (int value : arr) {
            buckets.get((int) ((value - minValue - 1) / interval)).add(value);
        }

        int arrIndex = 0;
        for (LinkedList<Integer> currentBucket : buckets) {
            Integer[] bucketArray = new Integer[currentBucket.size()];
            bucketArray = currentBucket.toArray(bucketArray);
            InsertionSort.comparableSort(bucketArray);

            for (Integer value : bucketArray) {
                arr[arrIndex++] = value;
            }
        }
    }
}
