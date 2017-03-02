package me.beresnev.algorithms;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 02.03.17.
 */
public class StringHashing {

    private StringHashing() {
    }

    /**
     * Standard JDK implementation as of Java 8. Uses prime 31
     * and each character in the string, hashcode == sum
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

    public static class Hash {
        private static final int PRIME = 239017;

        private Long[] h;
        private Long[] deg;
        private char[] values;

        /**
         * http://codeforces.com/blog/entry/17507
         */
        Hash(String input) {
            values = input.toCharArray();
            fillHashes();
        }

        private void fillHashes() {
            int n = values.length;
            h = new Long[n];
            deg = new Long[n];

            h[0] = 0L;
            deg[0] = 1L;

            for (int i = 0; i < n - 1; i++) {
                h[i + 1] = h[i] * PRIME + values[i];
                deg[i + 1] = deg[i] * PRIME;
            }
        }

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

        /**
         * Compare hashes of to substrings
         */
        public static boolean compareSubstringsHashes(String first, String second, int from, int to) {
            long[] firstHash = getPrefixHashes(first.toCharArray());
            long[] secondHash = getPrefixHashes(second.toCharArray());
            return getAltHash(firstHash, from, to) == getAltHash(secondHash, from, to);
        }
    }
}
