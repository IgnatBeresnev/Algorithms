package me.beresnev.algorithms;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 26.02.17.
 */
public class ArrayPeakTest {
    @Test
    public void middlePeak() throws Exception {
        int[] array = new int[]{1, 2, 3, 4, 5, 2, 1};
        int peak = ArrayPeak.find(array);
        Assert.assertEquals(peak, 5);
    }

    @Test
    public void boundPeakLeft() {
        int[] array = new int[]{9, 8, 7, 6, 5, 4};
        int peak = ArrayPeak.find(array);
        Assert.assertEquals(peak, 9);
    }

    @Test
    public void boundPeakRight() {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int peak = ArrayPeak.find(array);
        Assert.assertEquals(peak, 8);
    }

    @Test
    public void doublePeak() {
        int[] array = new int[]{1, 2, 3, 6, 3, 2, 3, 4, 9, 4, 3, 2};
        int peak = ArrayPeak.find(array);
        Assert.assertThat(peak, CoreMatchers.anyOf(CoreMatchers.is(6), CoreMatchers.is(9)));
    }

    @Test
    public void equalsPeak() {
        int[] array = new int[]{1, 2, 3, 3, 3, 2, 1};
        int peak = ArrayPeak.find(array);
        Assert.assertEquals(peak, 3);
    }

    @Test
    public void linearEquals() {
        int[] array = new int[]{3, 3, 3, 3, 3, 3, 3, 3};
        int peak = ArrayPeak.find(array);
        Assert.assertEquals(peak, 3);
    }

    @Test
    public void nullArray() {
        int[] array = new int[]{};
        int peak = ArrayPeak.find(array);
        Assert.assertEquals(peak, -1);
    }
}