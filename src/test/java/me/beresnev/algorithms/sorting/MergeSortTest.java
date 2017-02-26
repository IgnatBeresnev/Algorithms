package me.beresnev.algorithms.sorting;

import me.beresnev.ArrayInitializer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 26.02.17.
 */
public class MergeSortTest {
    @Test
    public void simpleSort() {
        int[] array = ArrayInitializer.getRandomizedArray(40, 0, 40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        MergeSort.sort(copy);
        Assert.assertArrayEquals(array, copy);
    }

    @Test
    public void simpleSortReturn() {
        int[] array = ArrayInitializer.getRandomizedArray(40, 0, 40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        copy = MergeSort.sortReturn(copy);
        Assert.assertArrayEquals(array, copy);
    }

    @Test
    public void emptyArray() {
        int[] copy = new int[]{};
        MergeSort.sort(copy);
    }

    @Test
    public void emptyReturnArray() {
        int[] copy = new int[]{};
        int[] copy1 = MergeSort.sortReturn(copy);
        Assert.assertArrayEquals(copy, copy1);
    }

    @Test
    public void negativeArray() {
        int[] array = ArrayInitializer.getNegativeArray(40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        MergeSort.sort(copy);
        Assert.assertArrayEquals(array, copy);
    }

    @Test
    public void negativeReturnArray() {
        int[] array = ArrayInitializer.getNegativeArray(40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        copy = MergeSort.sortReturn(copy);
        Assert.assertArrayEquals(array, copy);
    }
}