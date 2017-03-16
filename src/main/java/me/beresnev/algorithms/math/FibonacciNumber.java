package me.beresnev.algorithms.math;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 11.03.17.
 */
public class FibonacciNumber {

    private static int[] memo;

    /**
     * F1 = F2 = 1
     * F(n) = F(n-1) + F(n-2)
     */
    private FibonacciNumber() {
    }

    /**
     * If you draw the fibonacci number F(n) as a tree,
     * then you'll see that we use reuse F(k) multiple
     * times. In naive, we compute it again. In optimized,
     * we just get the result from the prev. computation.
     * <p>
     * Running time ~ O(n)
     * There's a better way of doing it.
     */
    public static int computeOmptimized(int n) {
        memo = new int[n + 1];
        return memorizedDP(n);
    }

    private static int memorizedDP(int n) {
        if (memo[n] != 0) return memo[n];
        if (n <= 2) return 1;
        int f = memorizedDP(n - 1) + memorizedDP(n - 2);
        memo[n] = f;
        return f;
    }

    /**
     * Non-recursive, yet fast. Pretty much a topological sort of a DAG
     * You can save space by storing only 2 last results.
     * Moreover, time complexity is quite obvious (O(n))
     */
    public static int bottomUp(int n) {
        int[] memo = new int[n + 1];

        int f;
        for (int i = 0; i < memo.length; i++) {
            if (i <= 2) {
                f = 1;
            } else {
                f = memo[i - 1] + memo[i - 2];
            }
            memo[i] = f;
        }
        return memo[n];
    }

    /**
     * Exponential time 2^n
     * T(n) = T(n-1) + T(n-2) + O(1) = 2T(n-2) = O(2^n/2)
     */
    public static int naiveRecursive(int n) {
        if (n <= 2) return 1;
        return naiveRecursive(n - 1) + naiveRecursive(n - 2);
    }
}
