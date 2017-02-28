package me.beresnev;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 25.02.17.
 */
public class ArrayInitializer {
    private static Random random = new Random();
    private static int DEFAULT_BOUND = 50;

    /**
     * Simple class that generates arrays for tests. This way
     * I can write minimum amount of code needed for testing
     * and maximize randomness of the tests.
     */
    private ArrayInitializer() {
    }

    /**
     * @param length   length of the array to return, [0... length-1]
     * @param maxValue maximum value that can be present in the array.
     * @return array filled with random integers 0 ... maxValue
     */
    public static int[] getRandomizedArray(int length, int minValue, int maxValue) {
        int[] array = new int[length];
        int bound = maxValue != 0 ? maxValue : DEFAULT_BOUND;
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt(bound) + minValue;
        }
        return array;
    }

    /**
     * @return copy of the given array. I found it to be easier than Arrays.copyOf
     */
    public static int[] getCopyOf(int[] inputArray) {
        int[] copy = new int[inputArray.length];
        System.arraycopy(inputArray, 0, copy, 0, inputArray.length);
        return copy;
    }

    /**
     * @return array filled with negative numbers in the range of -100; -20
     */
    public static int[] getNegativeArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = (-100 + random.nextInt(80));
        }
        System.out.println("Generated array: " + Arrays.toString(array));
        return array;
    }
}
