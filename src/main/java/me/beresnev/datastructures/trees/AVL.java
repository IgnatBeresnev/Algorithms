package me.beresnev.datastructures.trees;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 23.02.17.
 */
public class AVL<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    /**
     * AVL balanced tree
     * <p>
     * Time complexity:
     * O(log n) for everything. = height
     * Space complexity:
     * O(n)
     */
    public AVL() {
        super();
    }

    /**
     * Pretty staright-forward method. Uses BST's insert,
     * and then checks the tree balance. If its wrong, corrects it.
     * Also, after insertion, rebalance method corrects heights.
     *
     * @see #insertAndReturn(Comparable, Object) for implementation
     */
    public void insert(K key, V value) {
        Node<K, V> newNode = insertAndReturn(key, value);
        rebalanceTree(newNode);
    }

    /**
     * Another straight-forward method. Uses BST's remove,
     * and then checks balance, starting from the removed node's parent.
     * Also, after removal, rebalance method corrects heights.
     *
     * @see #removeAndReturnParent(Comparable) for implementation
     */
    public void remove(K key) {
        Node<K, V> parentOfRemoved = removeAndReturnParent(key);
        rebalanceTree(parentOfRemoved);
    }

    /**
     * In balanced AVL tree we'd like to keep the difference between the
     * height of left and right children <= 1. If it's >= 2, we need
     * to rebalance to guarantee O(logN) height. If height of the whole
     * tree is N, then height of one child is N=n-1, and of second N=n-2
     * Reason for that is that in a balanced tree the difference between
     * children nodes <= 1. Thus -1, -2. It must be maintained at all times.
     * <p>
     * Worst balance is when every node differs by 1. In this case,
     * take my word that h < 2lg(N), where N - min number of nodes (!=leaves)
     * More exactly, h ~ 1440lg(n) - through Fibonacci number
     *
     * @see #getNodeHeight(Node) for height and depth definition
     * @see #rightRotate(Node) for rotation logic
     */
    private void rebalanceTree(Node<K, V> node) {
        Node<K, V> current = node;
        while (current != null) {
            updateHeight(current);
            if (getNodeHeight(current.left) >= 2 + getNodeHeight(current.right)) {
                if (getNodeHeight(current.left.left) >= getNodeHeight(current.left.right)) {
                    rightRotate(current);
                } else {
                    leftRotate(current.left);
                    rightRotate(current);
                }
            } else if (getNodeHeight(current.right) >= 2 + getNodeHeight(current.left)) {
                if (getNodeHeight(current.right.right) >= getNodeHeight(current.right.left)) {
                    leftRotate(current);
                } else {
                    rightRotate(current.right);
                    leftRotate(current);
                }
            }
            current = current.parent;
        }
    }

    /**
     * @see #rightRotate(Node) to understand rotation logic
     */
    private void leftRotate(Node<K, V> node) {
        Node<K, V> rightChild = node.right;
        rightChild.parent = node.parent;

        if (rightChild.parent == null) {
            root = rightChild;
        } else {
            if (rightChild.parent.left == node) {
                rightChild.parent.left = rightChild;
            } else if (rightChild.parent.right == node) {
                rightChild.parent.right = rightChild;
            }
        }

        node.right = rightChild.left;
        if (node.right != null) {
            node.right.parent = node;
        }
        rightChild.left = node;
        node.parent = rightChild;
        updateHeight(node);
        updateHeight(rightChild);
    }

    /**
     * To visually understand the change, draw tree from these numbers:
     * 41 20 65 11 29 50 26 23. We'll be rotating 29->26->23 to get
     * 23<-26->29, which is balanced. Steps when rightRotate(29):
     * <p>
     * 1) Change the parent (26.parent=29.parent). 1.1) If null = root
     * 1.2) Change parent's child pointer to new child (to 26)
     * 2) Change 26.right to 29.left (makes sense, it's <29, thus left)
     * 2.1) Update NEW 29.left.parent pointer to 29 (if exists)
     * 3) 26.right = 29; 29.parent = 26. Finish rotation
     */
    private void rightRotate(Node<K, V> node) {
        Node<K, V> leftChild = node.left;
        leftChild.parent = node.parent; // 1

        if (leftChild.parent == null) { // 1.1
            root = leftChild;
        } else { // 1.2
            if (leftChild.parent.left == node) {
                leftChild.parent.left = leftChild;
            } else if (leftChild.parent.right == node) {
                leftChild.parent.right = leftChild;
            }
        }

        node.left = leftChild.right; // 2
        if (node.left != null) {
            node.left.parent = node; // 2.1
        }
        leftChild.right = node; // 3
        node.parent = leftChild;
        updateHeight(node);
        updateHeight(leftChild);
    }

    /**
     * Height of a node - longest path from this node down to a leaf.
     * Don't confuse with depth (distance from root to node).
     * <p>
     * Use this method to avoid calling height on null nodes.
     * Moreover, we actually need it to sometimes return -1,
     * which would indicate that right/left child is empty.
     * (it balances the +1 in the updateHeight formula).
     *
     * @return node height or -1 if node == null
     * @see #updateHeight(Node) for why we return -1
     */
    private int getNodeHeight(Node<K, V> node) {
        return node == null ? -1 : node.height;
    }

    /**
     * This is simply the formula for counting node's height.
     * We need +1 for root height (it's max(left, right) + 1 (root itself))
     * and  in case right/left child is null (getHeight(null) returns -1).
     * Then node's height will be 0, which is logical.
     *
     * @param node node whose height to update
     * @see #getNodeHeight(Node) for height definition and more info
     */
    private void updateHeight(Node<K, V> node) {
        node.height = Math.max(getNodeHeight(node.left), getNodeHeight(node.right)) + 1;
    }
}
