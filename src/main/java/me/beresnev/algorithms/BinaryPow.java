package me.beresnev.algorithms;

/**
 * @author Ignat Beresnev
 * @version 1.2
 * @since 24.02.17.
 */
public class BinaryPow {

    /**
     * Allows you to power numbers with O(log(n)) multiplications,
     * instead of O(n).
     * <p>
     * If pow variable is even, then a^n = a^(n/2)^2 = a^(n/2) * a^(n/2)
     * If pow is odd, then a^n = a^(n-1) * a
     * <p>
     * In other words, we just count a^(n/2) once and use it twice.
     * So for 2^10 we'll count res = 2^5 and then return res * res;
     *
     * @see java.math.BigInteger#pow(int) it's also binary pow
     */
    private BinaryPow() {
    }

    /**
     * Example:
     * We want 2^4. Obvious: 2 * 2 * 2 * 2
     * Smart: n = 2 * 2; return n * n;
     * <p>
     * For odd numbers, we do n-1, making it even, then count
     * and multiply by number. Basically this: a^(n-1) * a
     *
     * @return 0 if pow is negative, 1 if pow == 0, result otherwise
     */
    public static long pow(int number, int pow) {
        if (pow < 0) return 0;
        if (pow == 0) {
            return 1;
        }
        if ((pow & 1) == 1) { // n % 2
            return pow(number, pow - 1) * number;
        } else {
            long b = pow(number, pow / 2);
            return b * b;
        }
    }

    /**
     * Non-recursive binary pow. Pretty much uses the same logic
     * and same formulas for counting. Not optimized
     *
     * @see #powWhileOptimized(int, int) for ompimized version
     */
    public static long powWhile(int number, int pow) {
        if (pow < 0) return 0;
        long res = 1;
        int a = number;
        int n = pow;
        while (n > 0)
            if ((pow & 1) == 1) { // n % 2
                res = res * a;
                n--;
            } else {
                a = a * a;
                n = n / 2;
            }
        return res;
    }

    /**
     * Optimized version, uses bit-shift operations instead of / 2.
     * Also, you might have noticed that a*a happens anyway during
     * the next cycle (since we do n--), so we just let it go and
     * multiply straight away, saving some cycles.
     */
    public static long powWhileOptimized(int number, int pow) {
        long res = 1;
        int a = number;
        int n = pow;
        while (n > 0) {
            if ((n & 1) == 1)
                res *= a;
            a *= a;
            n >>= 1;
        }
        return res;
    }

    /**
     * @param number number to get pow for
     * @param length = max power
     * @return array with values number^1, number^2 ... number^length
     */
    public static long[] getAllPowFor(int number, int length) {
        long[] arr = new long[length];

        arr[0] = 1;
        for (int i = 1; i < length; i++) {
            arr[i] = arr[i - 1] * number;
        }
        return arr;
    }
}
