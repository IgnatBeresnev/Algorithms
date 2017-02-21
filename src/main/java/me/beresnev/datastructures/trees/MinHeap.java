package me.beresnev.datastructures.trees;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 18.02.17.
 */
public class MinHeap extends Heap {

    public MinHeap() {
        super();
    }

    public MinHeap(int[] arr) {
        super(arr);
    }

    /**
     * @param branch index of the branch element to heapify
     * @see me.beresnev.datastructures.trees.Heap#heapify(int)
     */
    protected void heapify(int branch) {
        int leftChild = leftChild(branch);
        int rightChild = rightChild(branch);

        int smallestValueIndex = branch;
        if (leftChild <= elementPointer - 1 && array[leftChild] < array[branch]) {
            smallestValueIndex = leftChild;
        }

        if (rightChild <= elementPointer - 1 && array[rightChild] < array[smallestValueIndex]) {
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
    @Override
    void checkHeapStructureBottomUp(int branch) {
        if (array[parent(branch)] >= array[branch] && branch != 0) {
            swap(parent(branch), branch);
            checkHeapStructureBottomUp(parent(branch));
        }
    }
}
