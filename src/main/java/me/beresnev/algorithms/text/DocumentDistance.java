package me.beresnev.algorithms.text;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ignat Beresnev
 * @version 1.2
 * @since 14.02.17.
 */

public class DocumentDistance {

    /**
     * Basically shows you how different two documents are.
     * Identical documents produce 0. Completely different ~1.5
     * <p>
     * Usage:
     * - You're a web search engine. You want to give the user
     * the best result for his query. This can be done by comparing
     * document distance between his query and the web page. Whichever
     * distance is the smallest, is most likely the best result.
     * <p>
     * - There's no point in allocating space for 2 identical documents.
     * By calculating document distance, you can make sure they're identical.
     * <p>
     * - Teachers want to find out if students are cheating. We take
     * their essays and compare them with one another. If document distance
     * between two essays is suspiciously low, it's a reason to have
     * a look at those essays and compare them ourselves.
     */
    private DocumentDistance() {
    }

    /**
     * The "distance" between two vectors is the angle between them.
     * If x = (x1, x2, ..., xn) is the first vector (xi = freq of word i)
     * and y = (y1, y2, ..., yn) is the second vector,
     * then the angle between them is defined as:
     * d(x,y) = arccos(inner_product(x,y) / (norm(x) * norm(y)))
     * where:
     * inner_product(x,y) = x1*y1 + x2*y2 + ... xn*yn
     * norm(x) = sqrt(inner_product(x,x))
     * where x1 and y1 - the amount of times word 1 was used in X and Y documents.
     *
     * @return float difference. If both documents are small, double produces
     * very wrong results. Moreover, we don't need that precision.
     */
    public static float getAngle(File d1, File d2) {
        if (!d1.exists() || !d2.exists())
            throw new IllegalArgumentException("File doesn't exist");
        if (d1 == d2)
            throw new IllegalArgumentException("Both params are the same one file");

        Map<String, Integer> d1Words = countWords(d1);
        Map<String, Integer> d2Words = countWords(d2);
        return (float) Math.acos(getInnerProduct(d1Words, d2Words) / (getNorm(d1Words) * getNorm(d2Words)));
    }

    /**
     * @return Inner product between two vectors, where vectors
     * are represented as dictionaries of (word, freq) pairs.
     */
    private static float getInnerProduct(Map<String, Integer> map1, Map<String, Integer> map2) {
        int sum = 0;
        for (Map.Entry<String, Integer> entry : map1.entrySet()) {
            if (map2.containsKey(entry.getKey())) {
                sum += entry.getValue() * map2.get(entry.getKey());
            }
        }

        return (float) sum;
    }

    /**
     * getNorm is used as denominator in Math.acos
     * <p>
     * It should be counted as getInnerProduct(d1, d1), but since it uses the same dictionary,
     * I've optimized it a little bit. If we're checking the same map, then surely
     * the words are the same in it. No point in wasting time and resources on .containsKey(),
     * it'll always be true. That is the only difference from getInnerProduct
     * <p>
     * It should be counted as
     *
     * @return inner product for that particular map.
     */
    private static float getNorm(Map<String, Integer> map) {
        int sum = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            sum += entry.getValue() * entry.getValue();
        }
        return (float) Math.sqrt(sum);
    }

    /**
     * Reads file line-by-line. Deletes punctuation, splits the line into words,
     * where a word is a string surrounded by spaces. Counts every word's usage.
     *
     * @param file to count words in
     * @return map filled with words as keys and number of occurrences as values.
     */
    @SuppressFBWarnings("DM_DEFAULT_ENCODING")
    private static Map<String, Integer> countWords(File file) {
        Map<String, Integer> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String[] words;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) continue;
                line = line.replaceAll("[\\p{Punct}&&[^@',&]]", "");
                words = line.split("\\s+");
                for (String word : words) {
                    word = word.toLowerCase();
                    if (map.containsKey(word)) {
                        map.put(word, map.get(word) + 1);
                    } else {
                        map.put(word, 1);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
