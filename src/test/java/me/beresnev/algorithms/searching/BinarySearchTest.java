package me.beresnev.algorithms.searching;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 26.02.17.
 */
public class BinarySearchTest {
    @Test
    public void simpleSearch() {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int result = BinarySearch.getIndex(array, 8);
        Assert.assertEquals(8, array[result]);
    }

    @Test
    public void leftBound() {
        int[] array = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int result = BinarySearch.getIndex(array, 10);
        Assert.assertEquals(10, array[result]);
    }

    @Test
    public void rightBound() {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int result = BinarySearch.getIndex(array, 10);
        Assert.assertEquals(10, array[result]);
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("DLS_DEAD_LOCAL_STORE")
    @Test(expected = IllegalArgumentException.class)
    public void doesntExist() {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        int result = BinarySearch.getIndex(array, 44);
    }

    @Test
    public void emptyArray() {
        int[] array = new int[]{};
        int result = BinarySearch.getIndex(array, 44);
        Assert.assertEquals(result, -1);
    }

    @Test
    public void oneElementArray() {
        int[] array = new int[]{5};
        int result = BinarySearch.getIndex(array, 5);
        Assert.assertEquals(result, 0);
    }
}