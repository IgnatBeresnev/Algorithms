package me.beresnev.datastructures;

import java.util.Objects;

/**
 * @author Ignat Beresnev
 * @version 1.0
 * @since 03.03.17.
 */
public class OpenAddressing<K, V> {

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_CAPACITY = 16;
    private static final int DEFAULT_THRESHOLD = 12;
    private int capacity;
    private int threshold;
    private int size;
    private Node[] table;

    /**
     * This is an open-addressing implementation of a hash table, where there's
     * only one element in each bucket.
     * <p>
     * ----------------------------- DISCLAIMER! ---------------------------
     * This class is written strictly to demonstrate what open addressing is!
     * By no means this it the finished code in it's best state. I've purposely
     * designed it so that the used probing method can be switched at any time (hardcoded).
     * If you want to use an Open Addressing hash table, choose only 1 probing method
     * and build your data structure around that.
     * ---------------------------------------------------------------------
     * <p>
     * ALSO! Probing methods can be more or less efficient at particular load factors.
     * The default here is 0.75, which is somewhat in the middle, but I suggest you
     * do research and find appropriate load factor for chosen probing method.
     *
     * @see HashMap for hashing with chaining
     */
    public OpenAddressing() {
        capacity = DEFAULT_CAPACITY;
        threshold = DEFAULT_THRESHOLD;
        table = new Node[capacity];
    }

    /**
     * I'm actually not entirely sure if it's a good way to
     * calculate the second hash. I've written this based on examples and
     * theory I've seen online. 13 is a prime that is < than the table size.
     */
    private static int doubleHash(int h) {
        return 13 - h % 13;
    }

    /**
     * Very simplistic and NOT badhash-proof index calculation.
     *
     * @see HashMap#indexFor(int, int) for something more advanced
     */
    private static int indexFor(int hash, int length) {
        return Math.abs(hash % length);
    }

    /**
     * We help GC by removing references to obejects, and also change
     * the "deleted" node's state to available. The bucket from the node
     * is actually NOT null, but if someone wants to insert something it
     * (it sees the AVAILABLE state), then let it be. We won't lose anything.
     */
    private static void cleanNode(Node node) {
        node.key = null;
        node.value = null;
        node.hash = 0;
        node.state = State.AVAILABLE;
    }

    /**
     * Using the default probing method, finds and puts a node in the table.
     */
    public void put(K key, V value) {
        int hash = key.hashCode();
        int trueIndex = defaultProbing(table, false, key);
        if (trueIndex != -1) {
            table[trueIndex] = new Node<>(hash, key, value);
            if (size++ >= threshold)
                resize(2 * capacity);
        }
    }

    /**
     * @return value for given key if the key is present, null otherwise
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int index = defaultProbing(table, true, key);
        return (index != -1) ? (V) table[index].value : null;
    }

    /**
     * removes the node with the given key.
     */
    public void remove(K key) {
        int index = defaultProbing(table, true, key);
        if (index != -1) {
            cleanNode(table[index]);
            size--;
        }
    }

    /**
     * Method that allows you to quickly switch between different probing methods.
     *
     * @param table  table within which to probe
     * @param lookup if false, then we need an index with null / available
     *               state. If true, then we only need the index of the key
     * @param key    key either to insert / override, or to just find (if lookup true)
     * @return index depending on the lookup. If true, then of the key
     * if false, then the index of the available/empty bucket
     */
    private int defaultProbing(Node[] table, boolean lookup, K key) {
        //return linearProbing(table, lookup, key);
        //return quadraticProbing(table, lookup, key);
        return doubleProbing(table, lookup, key);
    }

    /**
     * Difference: indexFor(hash + trial, capacity), where trial - 0...n
     * If a collision occurs, linear probing basically goes directly to the
     * next bucket (current+1) and looks there. If it's occupied, go to the
     * next one. Repeat until an empty/required bucket is found.
     * <p>
     * Problem: clusters. If there's 1 collision, then there will most likely be
     * a cluster of objects near it. Quite inefficient, especially in high-load
     * tables. Estimated amount of clusters is somewhere around O(logN)
     */
    private int linearProbing(Node[] table, boolean lookup, K key) {
        int capacity = table.length;
        int hash = key.hashCode();
        for (int trial = 0; trial < capacity; trial++) {
            int index = indexFor(hash + trial, capacity);

            Node current = table[index];
            if (!lookup && (current == null || current.state == State.AVAILABLE)) {
                return index;
            } else {
                if (current == null)
                    return -1;
                else if (hash == current.hash && (key == current.key || key.equals(current.key)))
                    return index;
            }
        }
        return -1;
    }

    /**
     * Difference: indexFor(hash + (trial * trial), where trial - 0...n
     * Jumps in indexes increase linearly, so it makes a bigger leap, allowing
     * you to avoid clusters. However, depending on your needs linear might end up
     * being the same or an even better choice.
     */
    private int quadraticProbing(Node[] table, boolean lookup, K key) {
        int capacity = table.length;
        int hash = key.hashCode();
        for (int trial = 0; trial < capacity; trial++) {
            int index = indexFor(hash + (trial * trial), capacity);

            Node current = table[index];
            if (!lookup && (current == null || current.state == State.AVAILABLE)) {
                return index;
            } else {
                if (current == null)
                    return -1;
                else if (hash == current.hash && (key == current.key || key.equals(current.key)))
                    return index;
            }
        }
        return -1;
    }

    /**
     * Difference: indexFor(hash + trial * doubleHash(hash)
     * Is thought to be better than other probing methods.
     *
     * @see #doubleHash(int) for second hash function
     */
    private int doubleProbing(Node[] table, boolean lookup, K key) {
        int capacity = table.length;
        int hash = key.hashCode();
        for (int trial = 0; trial < capacity; trial++) {
            int index = indexFor(hash + trial * doubleHash(hash), capacity);

            Node current = table[index];
            if (!lookup && (current == null || current.state == State.AVAILABLE)) {
                return index;
            } else {
                if (current == null)
                    return -1;
                else if (hash == current.hash && (key == current.key || key.equals(current.key)))
                    return index;
            }
        }
        return -1;
    }

    /**
     * @see HashMap#resize(int) for more details. This method is
     * actually the same.
     */
    private void resize(int newCapacity) {
        Node[] newTable = new Node[newCapacity];
        transferTo(newTable);
        table = newTable;
        capacity = newCapacity;
        threshold = (int) (newCapacity * DEFAULT_LOAD_FACTOR);
    }

    /**
     * Same as transferTo in HashMap class, the only difference is that
     * since we don't have chaining, we don't need to walk buckets. So
     * we just do a linear walk through the old table and re-index
     * elements and put them in the new table.
     *
     * @see HashMap#transferTo(HashMap.Node[]) for more details
     */
    @SuppressWarnings("unchecked")
    private void transferTo(Node[] newTable) {
        Node[] oldTable = table;
        for (int i = 0; i < oldTable.length; i++) {
            Node<K, V> current = oldTable[i];
            if (current != null) {
                oldTable[i] = null; // letting GC do its work
                int trueIndex = defaultProbing(newTable, false, current.key);
                if (trueIndex != -1) {
                    newTable[trueIndex] = current;
                }
            }
        }
    }

    /**
     * @return number of elements in the table
     */
    public int size() {
        return size;
    }

    private enum State {
        AVAILABLE, OCCUPIED
    }

    private static class Node<K, V> {
        private int hash;
        private K key;
        private V value;
        private State state;

        Node(int hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            state = State.OCCUPIED;
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
    }
}
