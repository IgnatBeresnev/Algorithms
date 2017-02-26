package me.beresnev.algorithms.sorting;

import me.beresnev.ArrayInitializer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 25.02.17.
 */
public class RadixSortTest {
    @Test
    public void lsdNoCopyingSort() throws Exception {
        int[] array = ArrayInitializer.getRandomizedArray(40, 0, 40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        copy = RadixSort.lsdNoCopyingSort(copy, 40);
        Assert.assertArrayEquals(array, copy);
    }

    @Test
    public void lsdSort() throws Exception {
        int[] array = ArrayInitializer.getRandomizedArray(40, 0, 40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        copy = RadixSort.lsdSort(copy, 40);
        Assert.assertArrayEquals(array, copy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeNumbers() {
        int[] array = ArrayInitializer.getNegativeArray(40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        copy = RadixSort.lsdSort(copy, 40);
        Assert.assertArrayEquals(array, copy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeNumbersNoCopying() {
        int[] array = ArrayInitializer.getNegativeArray(40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        copy = RadixSort.lsdNoCopyingSort(copy, 40);
        Assert.assertArrayEquals(array, copy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badMaxValue() {
        int[] array = ArrayInitializer.getNegativeArray(40);
        RadixSort.lsdSort(array, -10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badMaxValueNoCopying() {
        int[] array = ArrayInitializer.getNegativeArray(40);
        RadixSort.lsdNoCopyingSort(array, -10);
    }

    @Test
    public void emptyArray() {
        int[] array = new int[]{};
        int[] copy = RadixSort.lsdSort(array, 40);
        Assert.assertArrayEquals(array, copy);
    }

    @Test
    public void emptyArrayNoCopying() {
        int[] array = new int[]{};
        int[] copy = RadixSort.lsdNoCopyingSort(array, 40);
        Assert.assertArrayEquals(array, copy);
    }
}