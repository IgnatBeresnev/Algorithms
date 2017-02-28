package me.beresnev.datastructures;

import java.util.Objects;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 28.02.17.
 */
public class HashMap<K, V> {

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_CAPACITY = 16;
    private static final int DEFAULT_THRESHOLD = 12;
    private int capacity; // how many items map can hold (table.length)
    private int threshold; // default - 75% of capacity, if size=threshold, expand
    private int size; // how many elements THERE ARE in the map
    private Node[] table;

    public HashMap() {
        capacity = DEFAULT_CAPACITY;
        threshold = DEFAULT_THRESHOLD;
        table = new Node[capacity];
    }

    /**
     * Seen in JDK6. Quoting: Applies a supplemental hash function
     * to a given hashCode, which defends against poor quality
     * hash functions. This is critical because HashMap uses
     * power-of-two length hash tables, that otherwise encounter
     * collisions for hashCodes that do not differ in lower bits.
     * <p>
     * Might be slower than current implementation in Java 8
     * TODO: Read about this difference and about high/low bits
     *
     * @param h = object.hashCode()
     * @return corrected hash
     */
    private static int hash(int h) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * @param hash   = hash(object.hashCode())
     * @param length capacity of the current table
     * @return bucket for certain hash depending on given capacity
     */
    private static int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    /**
     * Pretty straight-forward. Get index based on the hash, look at a certain
     * bucket, look for key inside the chain. If given key is found, override
     * value, if not - add a new node (method addNode)
     * TODO: Add null support
     *
     * @return old value if key was already present, null otherwise
     * @see #addNode(int, Object, Object, int) for when we actually insert a new pair
     */
    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        if (key == null || value == null)
            throw new IllegalArgumentException("No null support as for now");
        int hash = hash(key.hashCode());
        int index = indexFor(hash, capacity);

        for (Node<K, V> current = table[index]; current != null; current = current.next) {
            if (hash == current.hash && (key == current.key || key.equals(current.key))) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
        }
        addNode(hash, key, value, index);
        return null;
    }

    /**
     * Adds this pair key-value to the beginning of the bucket,
     * places oldNode from that bucket right after (next).
     * If size >= threshold, we grow.
     * TODO: Probably can do something better than oldNode != null
     *
     * @see #resize(int) for details on how hashMap expands
     */
    @SuppressWarnings("unchecked")
    private void addNode(int hash, K key, V value, int bucket) {
        Node<K, V> oldNode = table[bucket];
        Node<K, V> newNode = new Node<>(hash, key, value, oldNode);
        table[bucket] = newNode;
        if (oldNode != null)
            oldNode.previous = newNode;
        if (size++ >= threshold)
            resize(2 * capacity);
    }

    /**
     * TODO: Do we throw something instead of null?
     *
     * @return value of the removed node, null otherwise
     */
    public V remove(K key) {
        if (key == null)
            throw new IllegalArgumentException("Null key removal is not permitted");
        return removeNode(key);
    }

    /**
     * Based on hash, gets this key's bucket, looks at where the required node is:
     * 1) If it's the head (1st element in the bucket), just change the head to next
     * 2) If it's the last element, just delete pointer to that element
     * 3) Must have both prev and next. Just unlink this element, exchanging pointers.
     *
     * @return value of the removed node or null if node with key not found
     */
    @SuppressWarnings("unchecked")
    private V removeNode(K key) {
        int hash = hash(key.hashCode());
        int index = indexFor(hash, capacity);
        Node<K, V> firstTableNode = table[index];

        for (Node<K, V> current = firstTableNode; current != null; current = current.next) {
            if (hash == current.hash && (key == current.key || key.equals(current.key))) {
                if (firstTableNode == current)
                    table[index] = current.next;
                else if (current.next == null)
                    current.previous.next = null;
                else {
                    current.previous.next = current.next;
                    current.next.previous = current.previous;
                }
                size--;
                return current.value;
            }
        }
        return null;
    }

    /**
     * @return value of the given key, if present. null otherwise
     */
    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("No null support as for now");
        Node<K, V> node = getNode(key);
        return node == null ? null : node.value;
    }

    /**
     * @return true if node with given key exists, false otherwise
     */
    public boolean contains(K key) {
        Node<K, V> node = getNode(key);
        return node != null;
    }

    /**
     * @return node with the given key, null otherwise
     */
    @SuppressWarnings("unchecked")
    private Node<K, V> getNode(K key) {
        int hash = hash(key.hashCode());
        Node<K, V> node = table[indexFor(hash, capacity)];
        for (Node<K, V> current = node; current != null; current = current.next) {
            if (hash == current.hash && (key == current.key || key.equals(current.key)))
                return current;
        }
        return null;
    }

    /**
     * @return number of elements in the map
     */
    public int size() {
        return size;
    }

    /**
     * @return true if map size == 0 (that is empty), false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * When size >= threshold, we need to expand. Start with this method
     * Make a new table, transfer the elements, update counters
     *
     * @param newCapacity x2 of old capacity (table.length)
     * @see #transferTo(Node[]) for how we transfer elements to the new table
     */
    private void resize(int newCapacity) {
        Node[] newTable = new Node[newCapacity];
        transferTo(newTable);
        table = newTable;
        capacity = newCapacity;
        threshold = (int) (newCapacity * DEFAULT_LOAD_FACTOR);
    }

    /**
     * Walk through every chain within every bucket in the OLD table,
     * starting from the beginning. Unlink elements in the old table to let
     * GC do its work. Then get new index for the Node based on new capacity,
     * place that node into the new table at that index. BUT! Just in case
     * there's already something in that bucket in the new table, we take
     * whatever is in that bucket (even null) and link it AFTER current
     * element. Then we place that element in the beginning of the new bucket.
     *
     * @param newTable table to transfer elements into
     */
    @SuppressWarnings("unchecked")
    private void transferTo(Node[] newTable) {
        Node[] oldTable = table;
        int newCapacity = newTable.length;
        for (int i = 0; i < oldTable.length; i++) { // bucket walking
            Node<K, V> current = oldTable[i];
            if (current != null) {
                oldTable[i] = null; // letting GC do its work
                while (current != null) { // chain walking
                    int index = indexFor(current.hash, newCapacity);
                    Node<K, V> oldNext = current.next;
                    // link whatever is in that bucket after this element
                    // place the element in the beginning of the new bucket
                    current.next = newTable[index];
                    newTable[index] = current;
                    current = oldNext;
                }
            }
        }
    }

    private static class Node<K, V> {
        private final int hash;
        private final K key;
        private V value;

        private Node next;
        private Node previous;

        /**
         * @param hash hashCode of the key
         * @param next node that will be linked after this node
         */
        private Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Node) {
                Node e = ((Node) o);
                if (Objects.equals(key, e.key) && Objects.equals(value, e.value))
                    return true;
            }
            return false;
        }

        // overridden just because I've implemented equals
        // Standard Java SE HashMap.Entry hashCode() implementation
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public String toString() {
            return key + "=" + value;
        }
    }
}
