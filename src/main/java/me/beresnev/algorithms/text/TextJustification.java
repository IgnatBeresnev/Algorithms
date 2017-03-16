package me.beresnev.algorithms.text;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 15.03.17.
 */
public class TextJustification {

    private static final int PAGE_WIDTH = 40;

    /**
     * Work in progress
     */
    private TextJustification() {
    }

    public static List<String> getLines(String line) {
        List<String> lines = new ArrayList<>();
        String[] words = line.split("\\s+");
        int[] wordsLength = preCountPrefixLength(words);
        int current = 0;
        while (current < words.length) {
            int finish = DP(words, wordsLength, current);
            if (finish > words.length - 1) {
                finish = words.length;
            }
            lines.add(getLineFromWords(words, wordsLength, current, finish));
            current = finish;
        }
        return lines;
    }

    // i - starting point, doesn't change
    // k - floating, going from i++ to find the end of current line
    private static int DP(String[] arr, int[] len, int i) {
        int min = Integer.MAX_VALUE;
        int minPointer = Integer.MAX_VALUE;
        for (int k = i + 1; k < arr.length; k++) {
            int badness = badness(len, i, k, PAGE_WIDTH);
            if (badness == Integer.MAX_VALUE) {
                break;
            } else if (badness < min) {
                min = badness;
                minPointer = k;
            } // TODO: if badness max value then k is already +1
        }

        if (minPointer == arr.length - 1) {
            minPointer = arr.length;
        }
        return minPointer;
    }

    private static int badness(int[] len, int i, int j, int pageWidth) {
        int total = getLengthSum(len, i, j);
        if (total > pageWidth) {
            return Integer.MAX_VALUE;
        } else {
            return (int) Math.pow(pageWidth - total, 3);
        }
    }

    private static String getLineFromWords(String[] arr, int[] len, int from, int to) {
        StringBuilder builder = new StringBuilder();
        int charsTotal = getLengthSum(len, from, to);
        int needSpaces = PAGE_WIDTH - charsTotal;

        for (int i = from; i < to; i++) {
            builder.append(arr[i]).append(' ');

            if (to != arr.length) {
                if (((to - from) <= needSpaces)) {
                    // TODO: Calculate required #spaces per space based on neededSpaces and #words.
                    builder.append("  ");
                } else if (needSpaces > 0) {
                    builder.append(' ');
                }
                needSpaces--;
            }
        }
        return builder.toString().trim();
    }

    private static int getLengthSum(int[] arr, int from, int to) {
        to -= 1;
        return from != 0 ? arr[to] - arr[from - 1] : arr[to];
    }

    private static int[] preCountPrefixLength(String[] arr) {
        int[] sum = new int[arr.length];
        int sumCount = 0;
        for (int i = 0; i < arr.length; i++) {
            String word = arr[i];
            sumCount += word.length() + 1;
            sum[i] = sumCount;
        }
        sum[arr.length - 1] -= 1;
        return sum;
    }
}
