package me.beresnev.algorithms.sorting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
     * Also, my implementations have bucket size of arr.length / 2
     *
     * @see #sortMaxValue(int[], int) for implementation with 1 param
     * maxValue and built-in sorting within buckets (collisions)
     * @see #sortWithInsertion(int[]) for implementation that takes no
     * parameters, uses insertion sort to sort buckets before putting
     * values from it into the original array.
     */
    private BucketSort() {
    }

    /**
     * This implementation takes 1 param maxValue and uses the formula that
     * determines the bucket of the element using that 1 param maxValue.
     * Also, when adding an element to the bucket, it puts that element in the
     * correct position within the bucket straight away, so we don't need to run
     * any sorting algorithms later on. See addValueToBucket method for details.
     *
     * @param maxValue max value that can be encountered in the array
     *                 you can remove it and run a findMax inside for the array.
     *                 But in this case I suggest you use another implementation.
     * @see #addValueToBucket(int, LinkedList) for details on how to add to buckets
     */
    public static void sortMaxValue(int[] arr, int maxValue) {
        if (arr.length < 2)
            return;
        else if (maxValue < 2) // TODO: Leave or not? Math.abs when buckets
            throw new IllegalArgumentException("maxValue is too small");

        List<LinkedList<Integer>> buckets = new ArrayList<>(arr.length / 2);
        for (int i = 0; i < arr.length / 2; i++) {
            buckets.add(new LinkedList<Integer>());
        }

        for (int value : arr) {
            int bucketIndex = (value * buckets.size()) / (maxValue + 1); // formula
            LinkedList<Integer> bucket = buckets.get(bucketIndex);
            addValueToBucket(value, bucket);
        }

        int arrIndex = 0;
        for (LinkedList<Integer> bucket : buckets) {
            for (Integer value : bucket) {
                arr[arrIndex++] = value;
            }
        }
    }

    /**
     * What this method does is it puts the value in its correct position within
     * the bucket straight away, without delaying the sorting. If it comes across
     * a duplicate (a twin, if you will), it will put current value right AFTER
     * the duplicate, hence guarantying stability, since during the iteration
     * they will be extracted in the same order (last in, last out).
     *
     * @param value  value to insert
     * @param bucket bucket in which to put the value
     * @see #sortMaxValue(int[], int) for when it's used
     */
    private static void addValueToBucket(int value, LinkedList<Integer> bucket) {
        ListIterator<Integer> iterator = bucket.listIterator();
        while (true) {
            if (iterator.hasNext()) {
                int next = iterator.next();
                if (value == next) {
                    iterator.add(value);
                    break;
                } else if (value < next) {
                    iterator.set(value);
                    iterator.add(next);
                    break;
                }
            } else {
                // it's either first or last (biggest) element then
                iterator.add(value);
                break;
            }
        }
    }

    /**
     * General logic is the same. There are 2 differences:
     * - To determine the bucket the "interval" variable is used, which takes
     * min and max values (so we have to find them beforehand in a loop)
     * - It adds values to the end of the bucket. Then, when it's time to
     * iterate through that bucket, it does linkedList->Array, and sorts
     * that array with insertion sort. In this case insertion sort is rather
     * fast, since values within the bucket are very close value-wise.
     */
    public static void sortWithInsertion(int[] arr) {
        int minValue = arr[0];
        int maxValue = arr[0];
        for (int value : arr) {
            if (value < minValue)
                minValue = value;
            else if (value > maxValue)
                maxValue = value;
        }

        List<LinkedList<Integer>> buckets = new ArrayList<>(arr.length / 2);
        for (int i = 0; i < arr.length / 2; i++) {
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
