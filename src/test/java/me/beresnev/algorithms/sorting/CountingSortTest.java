package me.beresnev.algorithms.sorting;

import me.beresnev.ArrayInitializer;
import me.beresnev.datastructures.trees.AVL;
import me.beresnev.datastructures.trees.BinarySearchTree;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ignat Beresnev
 * @version 1.1
 * @since 25.02.17.
 */
public class CountingSortTest {

    @Test
    public void stableIntSort() throws Exception {
        int[] array = ArrayInitializer.getRandomizedArray(40, 1, 40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        copy = CountingSort.stableIntSort(copy, 40);
        Assert.assertArrayEquals(array, copy);
    }

    @Test
    public void stableIntSortRepeating() throws Exception {
        int[] repeatingArray = new int[5];
        int[] repeating = new int[]{3, 3, 3, 3, 3};
        for (int i = 0; i < 5; i++) {
            repeatingArray[i] = 3;
        }
        repeatingArray = CountingSort.stableIntSort(repeatingArray, 3);
        Assert.assertArrayEquals(repeatingArray, repeating);
    }

    @org.junit.Test
    public void simpleSort() throws Exception {
        int[] array = ArrayInitializer.getRandomizedArray(40, 1, 40);
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        CountingSort.simpleSort(copy, 40);

        Assert.assertArrayEquals(array, copy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeSort() {
        int[] array = ArrayInitializer.getNegativeArray(20);
        int[] copy = ArrayInitializer.getCopyOf(array);
        int[] copy1 = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        CountingSort.simpleSort(copy, 99);
        CountingSort.stableIntSort(copy1, 99);
    }

    @Test
    public void badArgumentArray() {
        int[] array = new int[]{}; // nothing should happen, nothing to sort
        CountingSort.simpleSort(array, 40);
        CountingSort.stableIntSort(array, 40);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badArgumentMaxValue() {
        int[] array = ArrayInitializer.getNegativeArray(20);
        CountingSort.simpleSort(array, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badArgumentStableMaxValue() {
        int[] array = ArrayInitializer.getNegativeArray(20);
        CountingSort.stableIntSort(array, -1);
    }

    @Test
    public void straightArray() {
        int[] array = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        CountingSort.simpleSort(copy, 40);

        Assert.assertArrayEquals(array, copy);
    }

    @Test
    public void straightStableArray() {
        int[] array = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] copy = ArrayInitializer.getCopyOf(array);

        Arrays.sort(array);
        CountingSort.stableIntSort(copy, 40);

        Assert.assertArrayEquals(array, copy);
    }

    /**
     * Please don't even look at it... I had no other sorting
     * algorithm that could compare objects, so I used AVL
     * <p>
     * This just checks that the structure is the same, including stability.
     * Key is the same, values are not.
     */
    @Test
    public void stableObjectSort() throws Exception {
        List<CountingSort.SomeObject> someObjList = new ArrayList<>();
        AVL<CountingSort.SomeObject, Integer> avl = new AVL<>();

        for (int i = 0; i < 30; i++) {
            if (i % 5 == 0) {
                CountingSort.SomeObject object2 = new CountingSort.SomeObject(i, -100);
                someObjList.add(object2);
                avl.insert(object2, -100);
            }
            CountingSort.SomeObject object1 = new CountingSort.SomeObject(i, 1);
            someObjList.add(object1);
            avl.insert(object1, 1);
            if (i % 2 == 0) {
                CountingSort.SomeObject object2 = new CountingSort.SomeObject(i, 99);
                someObjList.add(object2);
                avl.insert(object2, 99);
            }
        }
        CountingSort.SomeObject[] array = someObjList.toArray(new CountingSort.SomeObject[someObjList.size()]);

        BinarySearchTree.Node[] nodes = avl.getArray();
        CountingSort.SomeObject[] array1 = new CountingSort.SomeObject[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            array1[i] = (CountingSort.SomeObject) nodes[i].getKey();
        }
        Assert.assertArrayEquals(array, array1);

        for (int i = 0; i < array.length; i++) {
            System.out.printf("Counting. key %d, value %d%n", array[i].key, array[i].value);
            System.out.printf("AVL. key %d, value %d%n", array1[i].key, array1[i].value);
            System.out.println("-------------------------------------------------------------");
        }
    }
}