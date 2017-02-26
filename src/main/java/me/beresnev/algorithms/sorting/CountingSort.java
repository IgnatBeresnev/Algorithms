package me.beresnev.algorithms.sorting;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 25.02.17.
 */
public class CountingSort {

    /**
     * Counting Sort
     * <p>
     * Time complexity:
     * - Overall: O(n+k), where N - size of the array and k - max element in it.
     * - Best: O(n) when k == O(n).
     * <p>
     * Space complexity:
     * - O(n+k), n - for output array, k - for keeping track of n elements.
     * <p>
     * Makes sense to use it when k = O(n). Then time complexity is O(n).
     * When k is large, it might be space
     */
    private CountingSort() {
    }

    /**
     * The simplest implementation for numbers. Not stable:
     * will return repeating elements in random order.
     * Don't worry about getting NPE in arr when trying to insert
     * an element, since frequency[i] > 0 will be true only for
     * arr.length elements. Even though frequency.length is >
     * than arr.length, the elements in freq that are > 0 == arr.length
     *
     * @param maxValue maximum value of an element we can encounter in arr
     */
    public static void simpleSort(int[] arr, int maxValue) {
        int[] frequency = new int[maxValue + 1];
        for (int anArr : arr) {
            frequency[anArr]++;
        }

        int b = 0; // keeping track of the position
        for (int i = 0; i < frequency.length; i++) {
            for (int j = 0; j < frequency[i]; j++) {
                arr[b] = i;
                b++;
            }
        }
    }

    /**
     * In simple implementation, the order in which repeating elements
     * are returned is not guaranteed. That's not very stable. Suppose
     * we have some object that we store in the array (not an int), and
     * when we get the sorted array back, we want repeating elements to
     * be in the order that they were in the initial input array.
     * We can solve it by: 1) in every [] store array/linkedlist.
     * 2) Make an array that would help us determine the place for an
     * element long before we actually put it there. Let's go through #2.
     * <p>
     * 1) First, we walk through the array and count how many times we've seen
     * this key (has to be int) by incrementing the counter in position array (pos).
     * At this point we'll have an array where pos[i] shows how many times arr[i] was
     * seen in the input array. That's useful, but what we now want is to make
     * pos[i] return how many elements there are that are < than i (index). We'll
     * use it to later on put elements in correct places in the output array.
     * 2) counter - shows how many elements we've seen so far (all pos[0-i]).
     * We walk through pos array, and counter = counter + pos[i]. I'll remind
     * you that pos[i] will show how many times arr[i] was seen in the array (>=0)
     * By doing this we "reserve" place for repeating elements. If pos[2] = 2,
     * then there are 2 elements whose keys are the same, they need to be together,
     * so our next element we'll put at index 4 (2 + 2), if it makes sense.
     * 3) Now we want to produce output array. When we do pos[value], it shows
     * us how many elements there will be BEFORE this value in the sorted array.
     * So we want to put this value on it's righteous place (can be 0-arr.length).
     * We do it by out[pos[value]] = value. And then, in case there is another key
     * that is the same (equals), we want to put it right after this element.
     * So we increment position for this int element, so that it'll be right after us
     * That is achieved by pos[value]++.
     *
     * @param arr      input array with object to be sorted by key
     * @param maxValue value of the biggest element in that array
     * @return new sorted array
     */
    public static int[] stableIntSort(int[] arr, int maxValue) {
        int[] pos = new int[maxValue + 1];
        int[] out = new int[arr.length];

        for (int value : arr) { // 1
            pos[value]++;
        }

        int counter = 0; // 2
        for (int i = 0; i < pos.length; i++) { // determining element index in new array
            int temp = pos[i];
            pos[i] = counter; // index for element arr[i], based on how many elements are before it
            counter += temp; // # of prev. elements + # of current elements from pos.
        }

        /* Reverse method of the logic above, works perfectly fine. Going from the back.
        int num = arr.length;
        for(int i = pos.length - 1; i >= 0; i--){
            pos[i] = num - pos[i];
            num = pos[i]; //
        }
        */

        for (int value : arr) { // 3
            out[pos[value]] = value;
            pos[value]++;
        }
        return out;
    }

    /**
     * Same as method above, only here the code is clean and it demonstrates
     * the object sorting by key. Now, key can be whatever. It can be a
     * numeric representation of the class, but it has to be int (i.e number)
     *
     * @param max value of the biggest element in that array
     * @return new sorted array
     */
    public static SomeObject[] stableObjectSort(SomeObject[] arr, int max) {
        SomeObject[] out = new SomeObject[arr.length];
        int[] pos = new int[max + 1];

        for (SomeObject anArr : arr) {
            pos[anArr.key]++;
        }

        int carry = 0;
        for (int i = 0; i < pos.length; i++) {
            int temp = pos[i];
            pos[i] = carry;
            carry += temp;
        }
        for (SomeObject obj : arr) {
            out[pos[obj.key]] = obj;
            pos[obj.key]++;
        }
        return out;
    }

    /**
     * Sample class with two fields. Have a look at the test for results.
     */
    public static class SomeObject implements Comparable<SomeObject> {
        public int key;
        public int value;

        public SomeObject(int key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof SomeObject && key == ((SomeObject) o).key;
        }

        @Override
        public int hashCode() {
            return key;
        }

        @Override
        public int compareTo(SomeObject o) {
            return key == o.key ? 0 : key > o.key ? 1 : -1;
        }
    }
}
