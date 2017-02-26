package me.beresnev.algorithms.sorting;

import me.beresnev.ArrayInitializer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 26.02.17.
 */
public class MergeSortTest {
    @Test
    public void sort() throws Exception {
        int[] array = ArrayInitializer.getRandomizedArray(40, 0, 40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        MergeSort.sort(copy);
        Assert.assertArrayEquals(array, copy);
    }

    @Test
    public void sortReturn() throws Exception {
        int[] array = ArrayInitializer.getRandomizedArray(40, 0, 40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        copy = MergeSort.sortReturn(copy);
        Assert.assertArrayEquals(array, copy);
    }
}