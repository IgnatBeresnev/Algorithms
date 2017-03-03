package me.beresnev.algorithms;


/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 02.03.17.
 */
public class RabinKarp {

    /**
     * Rabin Karp algorithm.
     * <p>
     * We use string hashing for comparison. If hashes match, then likely do
     * strings. Comparing hashes allows us to do faster and cheaper compares.
     * <p>
     * If we used a simple and naive algorithm, such an operation, as that
     * described below, would take at least O(n*m) time, since we'd be moving
     * left and comparing every substring along the way with the target.
     * <p>
     * That's practically what we're doing, only RabinKarp algorithm takes
     * O(n+m) time. We pre-calculate the full hashes of both target and source
     * strings, and then we compare hashes of every substring of source with
     * the hash of target, which takes O(1) time. If it matches, we do full
     * by-character comparison, and if it's successful, then we have a match.
     * The trick is that we're extracting only the required fraction of hash.
     * <p>
     * TODO: Implement rolling hash data structure
     */
    private RabinKarp() {
    }

    /**
     * @param source the full string we need to do search in
     * @param target string we need to find in source
     * @return number of occurrences
     * @see StringHashing.Hash#getHash(int, int)
     */
    public static int contains(String source, String target) {
        StringHashing.Hash sourceHash = new StringHashing.Hash(source);
        StringHashing.Hash targetHash = new StringHashing.Hash(target);

        int matches = 0;
        int tLength = target.length();
        int sLength = source.length();

        long tHash = targetHash.getStringHash();
        // last check is when there are tLength chars left in source string
        for (int i = 0; i + tLength <= sLength; i++) {
            // if hashes match, likely do strings, but we check nonetheless
            if (sourceHash.getHash(i, i + tLength - 1) == tHash) {
                if (equalsByChar(source, i, target))
                    matches++;
            }
        }
        return matches;
    }

    /**
     * @return true if the source substring starting at index
     * equals target, false otherwise
     */
    private static boolean equalsByChar(String source, int index, String target) {
        if (source.length() - index < target.length())
            return false;
        for (int i = 0; i < target.length(); i++)
            if (source.charAt(i + index) != target.charAt(i))
                return false;
        return true;
    }

    /**
     * Class below uses Alternative methods from StringHashing. If you don't
     * understand what I'm taking about, visit StringHashing.Alternative
     *
     * @see StringHashing.AlternativeHash
     */
    public static class Alternative extends StringHashing.AlternativeHash {

        /**
         * Same as contains method above, the only difference is the used method
         * for hashing. As you can see, to get the correct hashes and thus results,
         * we have to multiply whatever we get by the missing degree of P.
         * Everything else is pretty much the same
         */
        public static int contains(String source, String target) {
            int matches = 0;
            int tLength = target.length();
            int sLength = source.length();

            long tHash = getStringHash(target);
            long[] sPrefixHash = getPrefixHashes(source.toCharArray());
            for (int i = 0; i + tLength <= sLength; i++) {
                if (getAltHash(sPrefixHash, i, i + tLength - 1) == tHash * pow[i]) { // here
                    if (equalsByChar(source, i, target))
                        matches++;
                }
            }
            return matches;
        }
    }
}
