package me.beresnev.algorithms.text;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 15.03.17.
 */
public class TextJustification {

    private static final int PAGE_WIDTH = 70;
    private static boolean isLast = false;

    /**
     * Work in progress
     */
    private TextJustification() {
    }

    public static void printLines(String line) {
        isLast = false;
        String[] words = line.split("\\s+");
        int current = 0;
        while (current < words.length) {
            int finish = DP(words, current);
            if (finish > words.length - 1) {
                finish = words.length;
            }
            System.out.println(toPrint(words, current, finish));
            current = finish;
        }
    }

    // i - starting point, doesn't change
    // k - floating, going from i++ to find the end of current line
    private static int DP(String[] arr, int i) {
        int min = Integer.MAX_VALUE;
        int minPointer = Integer.MAX_VALUE;
        for (int k = i + 1; k < arr.length; k++) {
            int badness = badness(arr, i, k, PAGE_WIDTH);
            if (badness == Integer.MAX_VALUE) {
                break;
            } else if (badness < min) {
                min = badness;
                minPointer = k;
            }
        }

        if (minPointer == arr.length - 1) {
            minPointer = arr.length;
            isLast = true;
        }
        return minPointer;
    }

    private static int badness(String[] arr, int i, int j, int pageWidth) {
        int total = countChars(arr, i, j);
        if (total > pageWidth) {
            return Integer.MAX_VALUE;
        } else {
            return (int) Math.pow(pageWidth - total, 3);
        }
    }

    // TODO: Pre-count the lengths of all string so that later length(j, i) - O(1)
    private static int countChars(String[] arr, int start, int finish) {
        int characters = 0;
        int needMinSpaces = 0;
        for (int i = start; i < finish; i++) {
            characters += arr[i].length();
            needMinSpaces++;
        }
        return characters + (needMinSpaces - 1); // OR needMinSpaces - 1
    }

    private static String toPrint(String[] arr, int from, int to) {
        StringBuilder builder = new StringBuilder();
        int charsTotal = countChars(arr, from, to);
        int spaces = PAGE_WIDTH - charsTotal;
        for (int i = from; i < to; i++) {
            builder.append(arr[i]).append(' '); // TODO: Remove flag isLast
            if (((to - from) <= spaces) && !isLast) { // TODO: Optimize additional spaces based on words/length
                builder.append("  ");
            } else if (spaces > 0 && !isLast) {
                builder.append(' ');
            }
            spaces--;
        }
        return builder.toString().trim();
    }
}
