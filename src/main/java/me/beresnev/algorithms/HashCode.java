package me.beresnev.algorithms;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 27.02.17.
 */
public class HashCode {

    /**
     * Hash - French roots, hache -> hacher -> hatcher -> hash,
     * late 16th century. So hashing is cutting into small
     * pieces, mixing around, etc.
     */
    private HashCode() {
    }

    /**
     * Seen in JDK6. Quoting: Applies a supplemental hash function
     * to a given hashCode, which defends against poor quality
     * hash functions. This is critical because HashMap uses
     * power-of-two length hash tables, that otherwise encounter
     * collisions for hashCodes that do not differ in lower bits.
     *
     * @param hashCode = object.hashCode()
     * @return corrected hash
     */
    private static int hash(int hashCode) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
        return hashCode ^ (hashCode >>> 7) ^ (hashCode >>> 4);
    }

    public static class SomeObject {
        private int x;
        private int y;
        private int hash;

        public SomeObject(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * A very classical approach to generating hashcode.
         */
        public int hashCode() {
            if (hash != 0) return hash;
            final int prime = 31;
            int result = prime;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }
    }
}
