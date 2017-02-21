package me.beresnev.datastructures.trees;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 21.02.17.
 */
public class BinarySearchTree<K extends Comparable<K>, V> {

    private Node<K, V> root;

    /**
     * Binary Search Tree
     * <p>
     * Time complexity:
     * O(h) for everything.
     * <p>
     * Since BST doesn't know how to balance itself, the worst
     * case scenario for its height is O(n). For example, if we
     * add elements in their ascending order (1, 2, 3, 4, 5 etc),
     * there will be 1 long node on the right. Then the cost of all
     * operations would be O(n). However, if our root is 25, and we
     * add elements from 0 to 50, height will most likely be log n,
     * which would make all operations' cost O(log n).
     */
    public BinarySearchTree() {
    }

    /**
     * Adds the key and value into the tree. If given key
     * is already in the tree, override its value to given.
     * Keeps going down the tree, comparing with current along
     * the way to find the correct position for new pair.
     * Stops when current = null, and creates a new node.
     */
    public void insert(K key, V value) {
        if (root == null) {
            root = new Node<>(key, value);
            return;
        }

        Node<K, V> current = root;
        while (true) {
            int compare = key.compareTo(current.key);
            if (compare < 0 && current.left == null) {
                current.left = new Node<>(current, key, value);
                return;
            } else if (compare > 0 && current.right == null) {
                current.right = new Node<>(current, key, value);
                return;
            } else if (compare == 0) {
                current.value = value;
                return;
            }
            current = compare < 0 ? current.left : current.right;
        }
    }

    /**
     * Puts the key in the tree if given key is absent.
     * To override the value, use insert method.
     */
    public void insertIfAbsent(K key, V value) {
        root = recursiveInsert(root, null, key, value);
    }

    /**
     * Doesn't deal with duplicates. If it finds the same key as given, just returns.
     *
     * @param node   starting node, whose children to compare (recurrence)
     * @param parent node's parent. By default null, then == prev. node
     * @return recur until node == null, make new node.
     * then unfold and return root.
     */
    private Node<K, V> recursiveInsert(Node<K, V> node, Node<K, V> parent, K key, V value) {
        if (node == null) return new Node<>(parent, key, value);

        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.left = recursiveInsert(node.left, node, key, value);
        } else if (compare > 0) {
            node.right = recursiveInsert(node.right, node, key, value);
        }
        return node; // always returns root, also as compare == 0
    }

    /**
     * First looks for the node, if it exists - cool, get it. If not, return.
     * Then we find biggest key among node.left (typically - it's the furthest
     * right element in there. But if our left child has no right children of
     * its own, then our left child == the biggest. In this case it's simple,
     * we just change its parent to old.parent and then do new.right = old.right.
     * If, however, we get some distant node from the depth of the tree, we untie
     * it from the parent (and it has no children by definition), then put it in
     * place of the old (change parent, left and right). Job done. If node to
     * remove is root, almost the same, but without changing parents, root has none.
     *
     * @param key node to remove by key
     * @see #removeRoot()
     */
    public void remove(K key) {
        Node<K, V> nodeToRemove = getNode(key);
        if (nodeToRemove == null) return;
        if (nodeToRemove == root) {
            removeRoot();
            return;
        }

        if (nodeToRemove.hasBothChildren()) {
            Node<K, V> replacementNode = getMaxNode(nodeToRemove.left);
            if (replacementNode == nodeToRemove.left) {
                // look at javadoc as to why it's different in this case
                replaceNodesParent(nodeToRemove, replacementNode);
                replacementNode.right = nodeToRemove.right;
            } else {
                untieNodeFromParent(replacementNode);
                replaceNodesParent(nodeToRemove, replacementNode);
                replacementNode.right = nodeToRemove.right;
                replacementNode.left = nodeToRemove.left;
            }
        } else {
            if (nodeToRemove.hasNoChildren())
                removeNodeNoChildren(nodeToRemove);
            else // at this point must have at least 1 child
                moveNodeUpOneChild(nodeToRemove);
        }
    }

    /**
     * Removes the root, for the most part
     * using the same logic as the remove method.
     *
     * @see #remove(Comparable)
     */
    private void removeRoot() {
        if (root.hasBothChildren()) {
            Node<K, V> replacementNode = getMaxNode(root.left);
            if (replacementNode == root.left) {
                // if biggest on left is left child, then
                // it has no right children. Just change root
                // to left child and give root's right children.
                root.left.parent = null;
                root.left.right = root.right;
                root = root.left;
            } else {
                untieNodeFromParent(replacementNode);
                replacementNode.left = root.left;
                replacementNode.right = root.right;
                root = replacementNode;
            }
        } else if (root.hasNoChildren()) {
            root = null;
        } else { // at least 1 child, just make it root
            if (root.right != null) {
                root.right.parent = null;
                root = root.right;
            } else {
                root.left.parent = null;
                root = root.left;
            }
        }
    }

    /**
     * We untie the node from its parent by removing the pointer
     */
    private void untieNodeFromParent(Node<K, V> node) {
        if (node.isLeftChild()) {
            node.parent.left = null;
        } else {
            node.parent.right = null;
        }
    }

    /**
     * newChild takes place of oldChild, will have same position (left/right)
     *
     * @param oldChild node whose parents should have new child
     */
    private void replaceNodesParent(Node<K, V> oldChild, Node<K, V> newChild) {
        if (oldChild.isLeftChild()) {
            oldChild.parent.left = newChild;
        } else {
            oldChild.parent.right = newChild;
        }
    }

    /**
     * Removes the node with no children. Just removes parent's pointer to node
     */
    private void removeNodeNoChildren(Node<K, V> node) {
        if (node.isLeftChild()) {
            node.parent.left = null;
        } else {
            node.parent.right = null;
        }
    }

    /**
     * Checks which child is not null (only 1 of em), and then moves it up
     */
    private void moveNodeUpOneChild(Node<K, V> node) {
        if (node.right != null) {
            node.parent.right = node.right;
        } else if (node.left != null) {
            node.parent.left = node.left;
        }
    }

    /**
     * Returns the value of the node, whose key == given
     *
     * @return value or null if key not found
     */
    public V get(K key) {
        Node<K, V> requested = getNode(key);
        return requested != null ? requested.value : null;
    }

    /**
     * Goes through the whole tree, looking for given key.
     * If it finds it, returns the node, if not - null.
     */
    private Node<K, V> getNode(K key) {
        Node<K, V> currentNode = root;
        while (true) {
            int compareResult = key.compareTo(currentNode.key);
            if (compareResult == 0) {
                return currentNode;
            }

            if (compareResult < 0) {
                if (currentNode.left == null) return null;
                currentNode = currentNode.left;
            } else if (compareResult > 0) {
                if (currentNode.right == null) return null;
                currentNode = currentNode.right;
            }
        }
    }

    /**
     * Taking advantage of get returning null if it doesn't
     * find the key. We get null - return false.
     */
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    /**
     * Keep going to the right until the end.
     * Last element on the right - the biggest.
     */
    public K getMaxKey() {
        if (root == null) return null;
        return getMaxNode(root).key;
    }

    private Node<K, V> getMaxNode(Node<K, V> root) {
        Node<K, V> current = root;
        while (true) {
            if (current.right == null) {
                return current;
            }
            current = current.right;
        }
    }

    /**
     * Keep going to the left until the end.
     * Left element on the left - the smallest.
     */
    public K getMinKey() {
        if (root == null) return null;
        return getMinNode(root).key;
    }

    private Node<K, V> getMinNode(Node<K, V> root) {
        Node<K, V> current = root;
        while (true) {
            if (current.left == null) {
                return current;
            }
            current = current.left;
        }
    }

    /**
     * Prints the whole ordered tree in the console
     */
    public void print() {
        print(root);
    }

    /**
     * Private method for display above. Recurs deep
     * until the furthest left element, which is min,
     * and then unfolds, printing everything along
     * the way. Goes til the end of right side (max).
     * TODO: Could make another method that returns "sorted" array/list
     *
     * @see #print
     */
    private void print(Node<K, V> node) {
        // stops at node.left || node.right, they're null
        if (node != null) {
            print(node.left);
            System.out.print(" " + node.key);
            print(node.right);
        }
    }

    private static class Node<K, V> {
        private final K key;
        private Node<K, V> parent;
        private Node<K, V> left;
        private Node<K, V> right;
        private V value;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        private Node(Node<K, V> parent, K key, V value) {
            this.parent = parent;
            this.key = key;
            this.value = value;
        }

        private boolean isLeftChild() {
            return parent.left == this;
        }

        private boolean hasBothChildren() {
            return left != null && right != null;
        }

        private boolean hasNoChildren() {
            return left == null && right == null;
        }
    }
}
