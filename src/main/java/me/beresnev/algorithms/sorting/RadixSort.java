package me.beresnev.algorithms.sorting;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 25.02.17.
 */
public class RadixSort {

    /**
     * Radix LSD sort
     * <p>
     * Time complexity:
     * - O(d(n+k)), but if D is constant and K = O(n),
     * then it's linear O(n)
     * <p>
     * More in-depth:
     * - O(n+b) per digit (CS)
     * - O((n+b)*d) - total, d - max base (length of max)
     * = O((n+b)*log b(k)), minimized when b = n
     * = O(n * log n(k)), hence if k <= n^c, total = O(nc)
     * <p>
     * Space complexity:
     * - ???
     * <p>
     * Useful for when max value is much bigger than size.
     * Say, size is 100, but max value is 9999999
     */
    private RadixSort() {
    }

    /**
     * See Counting sort first. Radix sort pretty much uses it as a routine.
     * Instead of doing counting sort on the whole numbers, we'll looking
     * at each digit individually. We'll start with Least Significant Digit,
     * hence the name LSD, which means with the right-most digit. In number
     * 345, we'll start with 5, then go onto 4, then 3. We'll do K iterations,
     * where K - length of the maxValue (since we're looking at each digit).
     * <p>
     * First iteration, we sort ALL numbers by their LAST digits. Second -
     * we sort ALL numbers AGAIN by their LAST-1 digit. And so on. By the
     * end we'll have a perfectly sorted array. Unfortunately, in this method
     * to save the iteration sorting result, so that later we can sort it
     * again but by another digit, we have to use system.arraycopy. I don't
     * think it's possible to do without it in 1 method. However, if you don't
     * want to copy arrays back and forth, there's another method down below.
     *
     * @param maxValue value of the biggest element in that array
     * @return sorted array
     * @see #getDigit(int, int) for how to extract 1 digit from the whole
     * @see #lsdNoCopyingSort(int[], int) for lsd sort without arraycopy
     */
    public static int[] lsdSort(int[] arr, int maxValue) {
        if (arr.length < 2)
            return arr;
        if (maxValue < 1) // if it's 0 or negative, we can't sort.
            throw new IllegalArgumentException("Max number is too low");

        int[] out = new int[arr.length];
        int[] pos;

        for (int i = 1; maxValue / i > 0; i *= 10) { // see getDigit method
            pos = new int[10];
            for (int value : arr) {
                if (value < 0)
                    throw new IllegalArgumentException("Cannot sort array with negative numbers");
                pos[getDigit(value, i)]++;
            }

            int carry = 0;
            for (int j = 0; j < pos.length; j++) {
                int temp = pos[j];
                pos[j] = carry;
                carry += temp;
            }

            for (int value : arr) {
                int digit = getDigit(value, i);
                out[pos[digit]] = value;
                pos[digit]++;
            }
            System.arraycopy(out, 0, arr, 0, out.length);
        }
        return out;
    }

    /**
     * Pretty much the same logic as above, but we control iterations
     * in one method, and do sorting in another one. It allows us to
     * avoid System.arraycopy.
     *
     * @see #radixCountingSort(int[], int) for sorting
     */
    public static int[] lsdNoCopyingSort(int[] arr, int maxValue) {
        if (arr.length < 2)
            return arr;
        if (maxValue < 1) // if it's 0 or negative, we can't sort.
            throw new IllegalArgumentException("Max number is too low");

        for (int i = 1; maxValue / i > 0; i *= 10) {
            arr = radixCountingSort(arr, i);
        }
        return arr;
    }

    /**
     * Modified version of the counting sort (added getDigit method),
     * that sorts the array by digit of the given base and returns the result.
     *
     * @param base current base (1, 10, 100, 1000, etc.)
     * @see CountingSort for original method
     */
    private static int[] radixCountingSort(int[] arr, int base) {
        int[] out = new int[arr.length];
        int[] pos = new int[10];

        for (int value : arr) {
            if (value < 0)
                throw new IllegalArgumentException("Cannot sort array with negative numbers");
            pos[getDigit(value, base)]++;
        }

        int carry = 0;
        for (int j = 0; j < pos.length; j++) {
            int temp = pos[j];
            pos[j] = carry;
            carry += temp;
        }

        for (int value : arr) {
            int digit = getDigit(value, base);
            out[pos[digit]] = value;
            pos[digit]++;
        }
        return out;
    }

    /**
     * @return one digit for given base. For example, in number 345
     * the base 1 digit - 5, base 10 - 4 (cos 40), base 100 - 3 (cos 300)
     */
    private static int getDigit(int value, int base) {
        return (value / base) % 10;
    }
}
