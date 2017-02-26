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
public class HeapSortTest {
    @Test
    public void simpleHeap() throws Exception {
        int[] array = ArrayInitializer.getRandomizedArray(40, 0, 40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        HeapSort.sort(copy);
        Assert.assertArrayEquals(array, copy);
    }

    @Test
    public void negativeNumbers() {
        int[] array = ArrayInitializer.getNegativeArray(20);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        HeapSort.sort(copy);
        Assert.assertArrayEquals(array, copy);
    }

    @Test
    public void badArray() {
        int[] array = new int[]{};
        HeapSort.sort(array);
    }

    @Test
    public void straightArray() {
        int[] array = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        HeapSort.sort(copy);

        Assert.assertArrayEquals(array, copy);
    }
}