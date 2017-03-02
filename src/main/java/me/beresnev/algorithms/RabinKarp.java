package me.beresnev.algorithms;


/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 02.03.17.
 */
public class RabinKarp {

    private RabinKarp() {
    }

    public static int contains(String source, String target) {
        StringHashing.Hash sourceHash = new StringHashing.Hash(source);
        StringHashing.Hash targetHash = new StringHashing.Hash(target);

        int matches = 0;
        int tLength = target.length();
        int sLength = source.length();

        long tHash = targetHash.getStringHash();
        for (int i = 0; i + tLength <= sLength; i++) {
            if (sourceHash.getHash(i, i + tLength - 1) == tHash) {
                if (equalsByChar(source, i, target))
                    matches++;
            }
        }
        return matches;
    }

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
        // s - n, t - m
        public static int contains(String source, String target) {
            int matches = 0;
            int tLength = target.length();
            int sLength = source.length();

            long tHash = getStringHash(target);
            long[] sPrefixHash = getPrefixHashes(source.toCharArray());
            for (int i = 0; i + tLength <= sLength; i++) {
                if (getAltHash(sPrefixHash, i, i + tLength - 1) == tHash * pow[i]) {
                    if (equalsByChar(source, i, target))
                        matches++;
                }
            }
            return matches;
        }
    }
}
