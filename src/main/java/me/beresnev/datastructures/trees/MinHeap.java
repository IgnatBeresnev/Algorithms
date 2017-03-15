package me.beresnev.datastructures.trees;

/**
 * @author Ignat Beresnev
 * @version 2.0
 * @since 18.02.17.
 */
public class MinHeap<T extends Comparable<T>> extends Heap<T> {

    public MinHeap() {
        super();
    }

    /**
     * @param branch index of the branch element to heapify
     * @see me.beresnev.datastructures.trees.Heap#heapify(int)
     */
    @SuppressWarnings("unchecked")
    void heapify(int branch) {
        int leftChild = leftChild(branch);
        int rightChild = rightChild(branch);

        if (!areNodesAvailable(leftChild, rightChild)) {
            return;
        }

        int smallestValueIndex = branch;
        int comparison = array[leftChild].compareTo(array[branch]);
        if (leftChild <= elementPointer - 1 && comparison < 0) {
            smallestValueIndex = leftChild;
        }

        comparison = array[rightChild].compareTo(array[smallestValueIndex]);
        if (rightChild <= elementPointer - 1 && comparison < 0) {
            smallestValueIndex = rightChild;
        }

        if (smallestValueIndex != branch) {
            swap(branch, smallestValueIndex);
            heapify(smallestValueIndex);
        }
    }

    /**
     * @param branch index of the branch to check the structure.
     * @see me.beresnev.datastructures.trees.Heap#checkHeapStructureBottomUp(int)
     */
    @SuppressWarnings("unchecked")
    @Override
    void checkHeapStructureBottomUp(int branch) {
        T first = array[parent(branch)];
        T second = array[branch];
        if (!areNodesAvailable(parent(branch), branch)) {
            return;
        }
        int comparison = first.compareTo(second);
        //int comparison = array[parent(branch)].compareTo(array[branch]);
        if (branch != 0 && comparison >= 0) {
            swap(parent(branch), branch);
            checkHeapStructureBottomUp(parent(branch));
        }
    }
}
