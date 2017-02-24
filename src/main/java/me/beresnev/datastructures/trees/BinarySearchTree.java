package me.beresnev.datastructures.trees;

/**
 * @author Ignat Beresnev
 * @version 1.2
 * @since 21.02.17.
 */
public class BinarySearchTree<K extends Comparable<K>, V> {

    Node<K, V> root;
    private int treeSize = 0;
    private int arrayIndex = 0; // for the getArray method

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
     * Public method for insertion. Returns nothing.
     */
    public void insert(K key, V value) {
        insertAndReturn(key, value);
    }

    /**
     * Adds the key and value into the tree. Keeps going down
     * the tree, comparing with current along the way to find
     * the correct position for new pair. Stops when current = null,
     * and creates a new node.
     *
     * @return the node it has just added, needed for AVL rebalancing.
     * @see AVL#insert(Comparable, Object)
     */
    Node<K, V> insertAndReturn(K key, V value) {
        treeSize++;
        if (root == null) {
            root = new Node<>(key, value);
            return root;
        }

        Node<K, V> current = root;
        while (true) {
            current.height++;
            int compare = key.compareTo(current.key);
            if (compare < 0 && current.left == null) {
                current.left = new Node<>(current, key, value);
                return current.left;
            } else if (compare >= 0 && current.right == null) {
                current.right = new Node<>(current, key, value);
                return current.right;
            }
            current = compare < 0 ? current.left : current.right;
        }
    }

    /**
     * Same as insert method, only recursive. !!! Does NOT support height.
     *
     * @param node   node whose children to compare (recursive)
     * @param parent node's parent. By default null, then == prev. node
     * @return recur until node == null, make new node then unfold and return root.
     */
    private Node<K, V> recursiveInsert(Node<K, V> node, Node<K, V> parent, K key, V value) {
        if (node == null) return new Node<>(parent, key, value);

        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.left = recursiveInsert(node.left, node, key, value);
        } else if (compare >= 0) {
            node.right = recursiveInsert(node.right, node, key, value);
        }
        return node; // always returns root, also as compare == 0
    }

    /**
     * Public method for removing an element. Returns nothing
     */
    public void remove(K key) {
        removeAndReturnParent(key);
    }

    /**
     * When deleting an element, one of three situations will occurr:
     * Node to be deleted 1) has no children; 2) has 1 child; 3) has both
     * We need to check for children and act based on that. Deletion logic:
     * 1) Check if node is childless. If so, just delete parent's pointer
     * to the child. In every case, node to delete will have old parent pointer.
     * 2) else check if it has only 1 child. If so, just move the child up
     * a node. node.parent.right/left = node.right/left + update child.parent
     * 3) else it has both children. We need to find a replacement. It has to be
     * => than node to delete, but <= than our right child to preserve BST structure.
     * Use getMinNode on right child. Change value of node to be deleted (so that we
     * don't have to update parent and right child pointers). Then update
     * replace.parent.left/right to replace.right/left and update
     * right/left parent pointers (if exist).
     *
     * @return parent of the removed node for further AVL tree balancing
     * @see AVL#remove(Comparable)
     */
    Node<K, V> removeAndReturnParent(K key) {
        Node<K, V> remove = getNode(key);
        if (remove == null) return null;

        Node<K, V> parent = remove.parent;
        if (remove.left == null && remove.right == null) { // 1
            if (parent.left == remove) {
                parent.left = null;
            } else if (parent.right == remove) {
                parent.right = null;
            }
        } else if (remove.left == null || remove.right == null) { // 2
            if (remove.left == null) {
                if (parent.left == remove) {
                    parent.left = remove.right;
                } else {
                    parent.right = remove.right;
                }
                remove.right.parent = parent;
            } else {
                if (parent.left == remove) {
                    parent.left = remove.left;
                } else {
                    parent.right = remove.left;
                }
                remove.left.parent = parent;
            }
        } else { // 3
            Node<K, V> replace = getMinNode(remove.right);
            remove.key = replace.key;
            remove.value = replace.value;
            if (replace.parent.left == replace) {
                replace.parent.left = replace.right;
                if (replace.right != null) {
                    replace.right.parent = replace.parent;
                }
            } else {
                replace.parent.right = replace.right;
                if (replace.right != null) {
                    replace.right.parent = replace.parent;
                }
            }
        }
        treeSize--;
        return parent;
    }

    /**
     * @return value of the node with given key
     */
    public V get(K key) {
        Node<K, V> get = getNode(key);
        return get == null ? null : get.value;
    }

    /**
     * @return node with given key, null if not found
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
            } else if (compareResult >= 0) {
                if (currentNode.right == null) return null;
                currentNode = currentNode.right;
            }
        }
    }

    // used for printing the tree as a graph.
    // TODO: Remove after all of the tree tests are done
    public Node<K, V> getRoot() {
        return root;
    }

    /**
     * @return true if node with key exists, false otherwise
     */
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    /**
     * Keep going to the right until the end.
     * Furthest right element - the biggest
     *
     * @return node where key > all other keys
     */
    public Node<K, V> getMaxNode(Node<K, V> root) {
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
     * Furthest left element - the smallest.
     * Method is useful to find replacement for removed element
     *
     * @return node where key < all other keys
     * @see #removeAndReturnParent(Comparable)
     */
    public Node<K, V> getMinNode(Node<K, V> root) {
        if (root == null) return null;
        Node<K, V> current = root;
        while (true) {
            if (current.left == null) {
                return current;
            }
            current = current.left;
        }
    }

    /**
     * @return node that follows after the node with given key
     * returns null if node.right == null or if node(key) == null
     */
    public Node<K, V> getSuccessor(K key) {
        Node<K, V> node = getNode(key);
        return node == null ? null : node.right;
    }

    /**
     * @return node's predecessor (parent). null if
     * node.parent == null or if node(key) == null
     */
    public Node<K, V> getPredecessor(K key) {
        Node<K, V> node = getNode(key);
        return node == null ? null : node.parent;
    }

    /**
     * @return tree size (number of elements)
     */
    public int size() {
        return treeSize;
    }

    /**
     * Prints the whole ordered by key tree
     */
    public void printKeys() {
        printKeys(root);
        System.out.println(); // to make next print on new line
    }

    /**
     * Recurs deep until the furthest left element, which is min,
     * and then unfolds, printing everything along the way.
     */
    private void printKeys(Node<K, V> node) {
        // stops at node.left || node.right, they're null
        if (node != null) {
            printKeys(node.left);
            if (node == root)
                System.out.printf("[%s] ", node.key);
            else System.out.print(node.key + " ");
            printKeys(node.right);
        }
    }

    /**
     * @return sorted by key array, somewhat an entrySet
     */
    public Node[] getArray() {
        Node[] array = new Node[treeSize];
        fillArray(array, root);
        arrayIndex = 0; // putting it back to 1
        return array;
    }

    /**
     * @see #printKeys(Node) for more details
     */
    private void fillArray(Node[] array, Node<K, V> node) {
        if (node != null) {
            fillArray(array, node.left);
            array[arrayIndex++] = node;
            fillArray(array, node.right);
        }
    }

    public static class Node<K, V> {
        Node<K, V> parent;
        Node<K, V> left;
        Node<K, V> right; //
        int height = 0;
        private K key;
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

        /**
         * Used by user when we return a node/nodes
         *
         * @see #getArray()
         * @see #getMaxNode(Node)
         * @see #getMinNode(Node)
         */
        public K getKey() {
            return key;
        }

        /**
         * @see #getKey()
         */
        public V getValue() {
            return value;
        }
    }
}