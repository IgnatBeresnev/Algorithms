package me.beresnev.algorithms.sorting;

import me.beresnev.ArrayInitializer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 27.02.17.
 */
public class BucketSortTest {
    @Test
    public void sortMaxValue() throws Exception {
        int[] array = ArrayInitializer.getRandomizedArray(500, 0, 1500);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        BucketSort.sortMaxValue(copy, 1500);
        Assert.assertArrayEquals(array, copy);
    }

    @Test
    public void sortWithInsertion() {
        int[] array = ArrayInitializer.getRandomizedArray(500, 0, 1500);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        BucketSort.sortWithInsertion(copy);
        Assert.assertArrayEquals(array, copy);
    }
}