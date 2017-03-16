package me.beresnev.algorithms.text;

import me.beresnev.algorithms.math.BinaryPow;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 02.03.17.
 */
public class StringHashing {

    /**
     * One of the best ways to hash is to use this formula:
     * h(S) = S[0] + S[1] * P + S[2] * P^2 + S[3] * P^3 + ... + S[N] * P^N
     */
    private StringHashing() {
    }

    /**
     * Standard JDK implementation as of Java 8. Basically the same,
     * but it's the reverted formula above.
     *
     * @param s input string
     * @return hashCode for a string based on value and prime 31
     */
    private static int JDKStringHashing(String s) {
        char[] value = s.toCharArray();
        int h = 0;

        if (value.length > 0) {
            for (int i = 0; i < value.length; i++)
                h = 31 * h + value[i];
        }
        return h;
    }

    /**
     * One way to calculate the hash of a string. Beware, these hashes
     * are not Thue–Morse sequence proof, which means these hashes can be
     * broken by the sequence. However, it is an optimal and small implementation.
     */
    public static class Hash {
        private static final int PRIME = 239017;

        private Long[] h;
        private Long[] deg;
        private char[] values;

        Hash(String input) {
            values = input.toCharArray();
            fillHashes();
        }

        private void fillHashes() {
            int n = values.length;
            h = new Long[n]; // hashes of prefixes
            deg = new Long[n]; // array with calculated pow degrees

            h[0] = 0L;
            deg[0] = 1L;

            for (int i = 0; i < n - 1; i++) {
                h[i + 1] = h[i] * PRIME + values[i];
                deg[i + 1] = deg[i] * PRIME;
            }
        }

        /**
         * Unlike getHash in AlternativeHash, this method returns the HASH
         * of the L...R substring. In alternative, this would return the hash
         * that is also multiplied by some degree of P, so we'd have to correct it
         * ourselves.
         * <p>
         * To get h[L..R], we do h[0..R] - h[0..L]
         */
        long getHash(int L, int R) {
            return h[R] - h[L] * deg[R - L];
        }

        long getStringHash() {
            return h[h.length - 1];
        }
    }

    /**
     * Alternative to Hash way of doing string hashing. It is believed to be worse
     * than the methods described above, but it works non-the-less.
     */
    public static class AlternativeHash {
        final static long[] pow;
        private static final int DEFAULT_SIZE = 10000;
        private static final int DEFAULT_PRIME = Character.MAX_VALUE;

        static {
            pow = BinaryPow.getAllPowFor(DEFAULT_PRIME, DEFAULT_SIZE);
        }

        /**
         * An implementation which uses pre-computed pow (def. from 1 to 10000)
         * and a slightly different logic in terms of operations.
         */
        public static long getStringHash(String input) {
            char[] ch = input.toCharArray();

            long h = ch[0];
            for (int i = 1; i < ch.length; i++) {
                h += pow[i] * ch[i];
            }
            return h;
        }

        /**
         * @return array where arr[i] - hash for [0...i]
         */
        public static long[] getPrefixHashes(char[] input) {
            long[] h = new long[input.length];

            h[0] = input[0];
            for (int i = 1; i < h.length; i++) {
                h[i] = h[i - 1] + pow[i] * input[i];
            }
            return h;
        }

        /**
         * hash(S[L..R]) = hash(S[0..R]) - hash(S[0..L])
         * This would return the hash that is multiplied by some
         * degree of P. So to get the TRUE hash, we'd have to multiply this
         * to the missing degree of P. Look at the usage of this method
         * in RabinKarp class to understand it.
         *
         * @param h hash for every prefix
         * @param L left index
         * @param R right index
         * @return get for substring(l, r)
         */
        public static long getAltHash(long[] h, int L, int R) {
            long result = h[R];
            if (L > 0)
                result -= h[L - 1];
            return result;
        }
    }
}
