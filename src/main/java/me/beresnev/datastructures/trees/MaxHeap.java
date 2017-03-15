package me.beresnev.datastructures.trees;

/**
 * @author Ignat Beresnev
 * @version 2.0
 * @since 18.02.17.
 */
public class MaxHeap<T extends Comparable<T>> extends Heap<T> {

    public MaxHeap() {
        super();
    }

    /**
     * @param branch index of the branch element to heapify
     * @see me.beresnev.datastructures.trees.Heap#heapify(int)
     */
    @SuppressWarnings("unchecked")
    protected void heapify(int branch) {
        int leftChild = leftChild(branch);
        int rightChild = rightChild(branch);

        if (!areNodesAvailable(leftChild, rightChild)) {
            return;
        }

        int largestValueIndex = branch;
        int comparison = array[leftChild].compareTo(array[branch]);
        if (leftChild <= elementPointer - 1 && comparison > 0) {
            largestValueIndex = leftChild;
        }

        comparison = array[rightChild].compareTo(array[largestValueIndex]);
        if (rightChild <= elementPointer - 1 && comparison > 0) {
            largestValueIndex = rightChild;
        }

        if (largestValueIndex != branch) {
            swap(branch, largestValueIndex);
            heapify(largestValueIndex);
        }
    }

    /**
     * @param branch index of the branch to check the structure.
     * @see me.beresnev.datastructures.trees.Heap#checkHeapStructureBottomUp(int)
     */
    @SuppressWarnings("unchecked")
    @Override
    void checkHeapStructureBottomUp(int branch) {
        int comparison = array[parent(branch)].compareTo(array[branch]);
        if (branch != 0 && comparison <= 0) {
            swap(parent(branch), branch);
            checkHeapStructureBottomUp(parent(branch));
        }
    }


}
