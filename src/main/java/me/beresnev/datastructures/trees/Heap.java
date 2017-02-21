package me.beresnev.datastructures.trees;

import java.util.Arrays;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 18.02.17.
 */
public abstract class Heap {
    private static final int DEFAULT_SIZE = 10;
    int[] array; // values

    // This basically represents the true size of the array
    // points at the element (last added + 1)
    int elementPointer;
    private int size; // overall size of the array

    /**
     * Heap
     * <p>
     * Time complexity:
     * Insert - O(logN)
     * Delete (w/ index) - O(logN)
     * Root (min/max) - O(1)
     * Delete/Change root - O(logN)
     * Search - O(n)
     * Space complexity:
     * O(n)
     */
    Heap() {
        array = new int[DEFAULT_SIZE];
    }

    /**
     * Constructs a heap from the given array.
     */
    Heap(int[] arr) {
        size = arr.length;
        array = new int[size];
        System.arraycopy(arr, 0, array, 0, arr.length);
        elementPointer = array.length;
        for (int i = (size - 1) / 2; i >= 0; i--) {
            heapify(i);
        }
    }

    /**
     * "Heapifies" the branch and all its children, making sure the
     * structure is as it should be (max/min heap). Corrects the
     * order where necessary. Method is overriden in MaxHeap and MinHeap.
     * <p>
     * Time complexity: O(logN) - height of the tree.
     *
     * @param branch index of the branch element to heapify
     * @see me.beresnev.datastructures.trees.MinHeap#heapify(int)
     * @see me.beresnev.datastructures.trees.MaxHeap#heapify(int)
     */
    abstract void heapify(int branch);

    /**
     * Changes the root value to given. Changes new root's place.
     * If there's ever a need in this method, it will run
     * one heapify instead of two: 1) in remove; 2) in add
     *
     * @param value new value of root
     */
    public void changeRootValue(int value) {
        array[0] = value;
        heapify(0);
    }

    /**
     * Adds an element to the heap. If the array is full,
     * check boolean grow. If true, expand. If false, exception.
     *
     * @param value int to add to the heap
     */
    public void add(int value) {
        if (elementPointer >= array.length)
            grow();

        array[elementPointer] = value;
        checkHeapStructureBottomUp(elementPointer);
        elementPointer++;
    }

    /**
     * @return and remove the root element (biggest/smallest).
     * @throws NullPointerException if root element is null/empty
     */
    public int remove() throws NullPointerException {
        if (elementPointer == 0)
            throw new NullPointerException("No root found");
        int rootValue = array[0];

        array[0] = array[elementPointer - 1]; // with -1 it returns the last element in the array
        array[elementPointer - 1] = 0;
        elementPointer--; // "decreasing" size of the array since we removed root element (changed to 0)
        // now pointer points at an empty element (0), ready for insertion.
        heapify(0);

        return rootValue;
    }

    /**
     * Returns without deleting. Equivalent of peek()
     *
     * @return root element (biggest/smallest)
     */
    public int examine() {
        return array[0];
    }

    /**
     * Moves up the branches and checks the ordering correctness,
     * depending on whether it's min or max heap. Swaps where necessary.
     * Should be overriden in Max and Min heap.
     *
     * @param branch index of the branch to check the structure.
     * @see me.beresnev.datastructures.trees.MinHeap#checkHeapStructureBottomUp(int)
     * @see me.beresnev.datastructures.trees.MaxHeap#checkHeapStructureBottomUp(int)
     */
    abstract void checkHeapStructureBottomUp(int branch);

    /**
     * ArrayList's grow method. Grows to 1/2 of the oldCapacity (size)
     * Limited by Integer.MAX_INT, but it's unchecked for.
     */
    private void grow() {
        int oldCapacity = array.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        array = Arrays.copyOf(array, newCapacity);
    }

    /**
     * Element pointer increases every time we add an element and
     * decreases every time we delete one. So it basically can tell you
     * if there are any elements in the array.
     */
    public boolean isEmpty() {
        return elementPointer == 0;
    }

    void swap(int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    int parent(int branch) {
        return branch / 2;
    }

    int leftChild(int branch) {
        return 2 * branch;
    }

    int rightChild(int branch) {
        return leftChild(branch) + 1;
    }


    /**
     * Instead of returning a toString of the array, majority of which
     * can be filled with 0's, we return only the values in the heap.
     */
    public String toString() {
        if (elementPointer == 0)
            return "[]";

        StringBuilder sb = new StringBuilder()
                .append('[');
        for (int i = 0; i < elementPointer; i++) {
            if (i == elementPointer - 1)
                return sb.append(array[i]).append(']').toString();
            sb.append(array[i]).append(',').append(' ');
        }
        return sb.toString(); // it actually should never get to this point
    }
}
