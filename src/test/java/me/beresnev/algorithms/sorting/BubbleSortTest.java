package me.beresnev.algorithms.sorting;

import me.beresnev.ArrayInitializer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Ignat Beresnev
 * @version 1.2
 * @since 26.02.17.
 */
public class BubbleSortTest {
    @Test
    public void sort() throws Exception {
        int[] array = ArrayInitializer.getRandomizedArray(40, 0, 40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        BubbleSort.sort(copy);
        Assert.assertArrayEquals(array, copy);
    }

    @Test
    public void notOptimizedSort() throws Exception {
        int[] array = ArrayInitializer.getRandomizedArray(40, 0, 40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        BubbleSort.notOptimizedSort(copy);
        Assert.assertArrayEquals(array, copy);
    }

    @Test
    public void negativeNumbers() {
        int[] array = ArrayInitializer.getNegativeArray(20);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        BubbleSort.notOptimizedSort(copy);
        Assert.assertArrayEquals(array, copy);
    }

}