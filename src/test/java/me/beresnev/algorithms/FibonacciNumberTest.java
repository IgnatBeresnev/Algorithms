package me.beresnev.algorithms;

import me.beresnev.algorithms.math.FibonacciNumber;
import org.junit.Test;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 11.03.17.
 */
public class FibonacciNumberTest {
    private int k = 45;


    /**
     * For k = 45, RAW results are:
     * - naive: 3386069073
     * - memoized: 13913
     * <p>
     * These are not proper benchmarks, I understand it.
     * But it clearly demonstrates the difference.
     */
    @Test
    public void basicTestNaive() {
        long start = System.nanoTime();
        int n = FibonacciNumber.naiveRecursive(k);
        long finish = System.nanoTime();
        System.out.println("Result: " + n);
        System.out.println("Time taken (naive): " + (finish - start));
        System.out.println("-------------------------------");
    }

    @Test
    public void memoized() {
        long start = System.nanoTime();
        int n = FibonacciNumber.computeOmptimized(k);
        long finish = System.nanoTime();
        System.out.println("Result: " + n);
        System.out.println("Time taken (memoized): " + (finish - start));
        System.out.println("-------------------------------");

    }

    @Test
    public void bottomUp() {
        long start = System.nanoTime();
        int n = FibonacciNumber.bottomUp(k);
        long finish = System.nanoTime();
        System.out.println("Result: " + n);
        System.out.println("Time taken (Bottomup): " + (finish - start));
        System.out.println("-------------------------------");

    }
}