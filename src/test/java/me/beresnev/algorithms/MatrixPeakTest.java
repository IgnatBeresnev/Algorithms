package me.beresnev.algorithms;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 26.02.17.
 */
public class MatrixPeakTest {

    @Test
    public void emptyMatrix() {
        int[][] matrix = new int[][]{};
        int peak = MatrixPeak.find(matrix);
        Assert.assertEquals(0, peak);
    }

    @Test
    public void simplePeak() throws Exception {
        int[][] matrix = new int[][]{
                {0, 2, 0},
                {3, 5, 1},
                {0, 4, 0}};
        int peak = MatrixPeak.find(matrix);
        Assert.assertEquals(5, peak);
    }

    @Test
    public void cornerPeakUpperRight() {
        int[][] matrix = new int[][]{
                {2, 2, 5},
                {3, 1, 1},
                {1, 2, 3}};
        int peak = MatrixPeak.find(matrix);
        Assert.assertEquals(5, peak);
    }

    @Test
    public void cornerPeakUpperLeft() {
        int[][] matrix = new int[][]{
                {5, 3, 3},
                {3, 2, 3},
                {1, 0, 1}};
        int peak = MatrixPeak.find(matrix);
        Assert.assertEquals(5, peak);
    }

    @Test
    public void cornerPeakLowerRight() {
        int[][] matrix = new int[][]{
                {2, 2, 1},
                {1, 2, 4},
                {1, 4, 5}};
        int peak = MatrixPeak.find(matrix);
        Assert.assertEquals(5, peak);
    }

    @Test
    public void cornerPeakLowerLeft() {
        int[][] matrix = new int[][]{
                {1, 1, 1},
                {3, 2, 1},
                {5, 4, 1}};
        int peak = MatrixPeak.find(matrix);
        Assert.assertEquals(5, peak);
    }

    @Test
    public void doublePeak() {
        int[][] matrix = new int[][]{
                {1, 1, 9},
                {3, 2, 1},
                {5, 4, 3}};
        int peak = MatrixPeak.find(matrix);
        Assert.assertThat(peak, CoreMatchers.anyOf(CoreMatchers.is(5), CoreMatchers.is(9)));
    }

    @Test
    public void allEquals() {
        int[][] matrix = new int[][]{
                {5, 5, 5},
                {5, 5, 5},
                {5, 5, 5}};
        int peak = MatrixPeak.find(matrix);
        Assert.assertEquals(5, peak);
    }
}