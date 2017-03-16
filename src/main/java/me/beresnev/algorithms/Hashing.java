package me.beresnev.algorithms;

import me.beresnev.algorithms.text.StringHashing;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 02.03.17.
 */
public class Hashing {

    /**
     * Hash - French roots, hache -> hacher -> hatcher -> hash,
     * late 16th century. So hashing is cutting into small
     * pieces, mixing around, etc.
     *
     * @see StringHashing for how to hash strings
     */
    private Hashing() {
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
    public static int hash(int hashCode) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
        return hashCode ^ (hashCode >>> 7) ^ (hashCode >>> 4);
    }

    /**
     * A very classical approach to generating hashcode.
     */
    private int classicalHashCode() {
        // variables from the class
        int var1 = 0;
        int var2 = 0;

        final int prime = 31;
        int result = prime;
        result = prime * result + var1;
        result = prime * result + var2;
        return result;
    }
}
