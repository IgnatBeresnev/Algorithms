package me.beresnev.algorithms.math;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 24.02.17.
 */
public class EratosthenesSieve {

    /**
     * Average implementation of Eratosthenes sieve. We basically go from 1 to N,
     * and cross out (=false) all numbers that can be divided by i. Start with 2,
     * then loop until the end of array (every cycle j = i + i) and for every num
     * that we see along the way, make array[j] = false, since if i is not prime,
     * than i + i is not prime either. We also start from i^2, because all of the
     * previous numbers that can be divided by i, must have a divider already. So they
     * were crossed out already. By the end we will have crossed indexes != prime.
     * <p>
     * Time complexity - O(n log log n)
     * Space complexity - O(n)
     * <p>
     * There are faster algorithms that take linear time, but most likely
     * they'll require more space and with large arrays it's not that great.
     */
    private EratosthenesSieve() {
    }

    /**
     * It's probably not the best way to get an array with primes
     * (an int array, not boolean). However, I've used this class to test
     * the correctness of initPrimesArray method.
     */
    public static Integer[] getPrimes(int limitNumber) {
        if (limitNumber <= 0)
            throw new IllegalArgumentException("Can't count negative numbers");
        boolean[] primes = initPrimesArray(limitNumber);
        List<Integer> primesList = new LinkedList<>();
        for (int i = 0; i < limitNumber; i++) {
            if (primes[i]) {
                primesList.add(i);
            }
        }
        return primesList.toArray(new Integer[primesList.size()]);
    }

    /**
     * For the description of the method, look at the constructors javadoc.
     *
     * @param limitNumber initializes array from 0 to limitNumber inclusive.
     * @return boolean array, where if array[i] == true, i == prime
     */
    public static boolean[] initPrimesArray(int limitNumber) {
        if (limitNumber <= 0) return null;
        boolean[] primes = new boolean[limitNumber + 1];
        Arrays.fill(primes, Boolean.TRUE);
        primes[0] = primes[1] = false; // we know that 0 & 1 != prime

        for (int i = 2; i < primes.length; i++) {
            if (primes[i]) {
                // i*i can easily go over Integer.MAX_VALUE. If it does, then
                // the number will be negative (signed), we'll get ~infinite loop
                if ((long) i * (long) i < primes.length) {
                    for (int j = i * i; j < primes.length; j += i) {
                        primes[j] = false;
                    }
                    /* Another way of writing that cycle
                    for (int j = 2; i * j < primes.length; j++)
                        primes[i*j] = false;
                     */
                }
            }
        }

        return primes;
    }
}
