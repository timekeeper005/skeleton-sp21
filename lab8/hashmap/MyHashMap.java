package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Tamaki Tiana
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int arraySize;
    private final double maxLoad;
    private int size;

    /** Constructors */
    public MyHashMap() {
        this.arraySize = 16;
        this.maxLoad = 0.75;
        this.size = 0;
        this.buckets = createTable(arraySize);
    }

    public MyHashMap(int arraySize) {
        this.arraySize = arraySize;
        this.maxLoad = 0.75;
        this.size = 0;
        this.buckets = createTable(arraySize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= maxLoad
     *
     * @param arraySize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int arraySize, double maxLoad) {
        this.arraySize = arraySize;
        this.maxLoad = maxLoad;
        this.size = 0;
        this.buckets = createTable(arraySize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key,value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        @SuppressWarnings("unchecked") // suppress the unchecked cast warning
        Collection<Node>[] buckets = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            buckets[i] = createBucket(); // correct way to assign to the array
        }
        return buckets;
    }

    /** Drops out all items with the help of garbage collector. */
    @Override
    public void clear() {
        this.buckets = createTable(arraySize);
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        // Calculate the index of the bucket in the hash table
        int index = reducedHashFunction(key, arraySize - 1);

        // Iterate through all nodes in the bucket to find the matching key
        for (Node node : buckets[index]) {
            // Compare the current node's key with the input key
            if (node.key.equals(key)) {
                // If the matching key is found, return its value
                return node.value;
            }
        }

        // If the corresponding key is not found, return null
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    /** Puts a new item into hashtable.If there's already one,update it.
     *  If load factor is greater than maxLoad,resize the array and redistribute all items.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            return;
        }
       // Resizes array if necessary
       resizeArray(2);
       // Adds the new entry to the hash map
       putHelper(key,value);
    }

    /** Resizes array and redistribute all items if necessary. */
    private void resizeArray(double scalingRate) {
        double loadFactor = (double) (size) / arraySize;
        if (loadFactor > maxLoad) {
            reconstruct((int) (arraySize * scalingRate));
            arraySize *= (int) scalingRate;
        }
    }

    private void putHelper(K key, V value) {
        int index = reducedHashFunction(key, arraySize - 1);
        if (buckets[index] != null) {
            for (Node node : buckets[index]) {
                if (node.key.equals(key)) {
                    node.value = value; // updates this value
                    return;
                }
            }
        }

        // Not found,adds new node
        Node node = createNode(key, value);
        buckets[index].add(node);
        size++; // Updates size
    }

    private void reconstruct(int capacity) {
        MyHashMap<K,V> newTable = new MyHashMap<>(capacity);
        newTable.putAll(this);
        this.buckets = newTable.buckets;
    }

    /** Puts all items in the given hashMap into this hashMap. */
    public void putAll(MyHashMap<K,V> otherHashMap){
        for (K key : otherHashMap) {
            V value = otherHashMap.get(key);
            put(key, value);
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Collection<Node> bucket : this.buckets) {
           for (Node node : bucket) {
               set.add(node.key);
           }
        }
        return set;
    }

    @Override
    public V remove(K key) {
        int index = reducedHashFunction(key, arraySize - 1);
        // Attempts to get the node in the corresponding bucket
        Node nodeToRemove = null;
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                nodeToRemove = node;
                break;
            }
        }

        if (nodeToRemove != null) { // If we get the node to remove
            V value = nodeToRemove.value;
            buckets[index].remove(nodeToRemove); // Removes it from the bucket
            size--; // Updates size
            resizeArray(0.5); // Resizes array if necessary

            return value;
        }

        return null;
    }

    @Override
    public V remove(K key, V value) {
        int index = reducedHashFunction(key, arraySize - 1);
        // Attempts to get the node in the corresponding bucket
        Node nodeToRemove = null;
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                nodeToRemove = node;
                break;
            }
        }

        if (nodeToRemove != null) { // If we get the node to remove
            V realValue = nodeToRemove.value;
            if (realValue.equals(value)) {
                buckets[index].remove(nodeToRemove); // Removes it from the bucket
                size--; // Updates size
                resizeArray(0.5); // Resizes array if necessary
            }
            return value;
        }

        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            int currentIndex = 0;
            Iterator<Node> currentIterator = (buckets.length > 0) ? buckets[currentIndex].iterator() : Collections.emptyIterator();

            @Override
            public boolean hasNext() {
                // Checks if there's next item in the bucket
                while (!currentIterator.hasNext()) {
                    // Moves to the next bucket
                    if (currentIndex < buckets.length - 1) {
                        currentIndex++;
                        currentIterator = buckets[currentIndex].iterator();
                    } else {
                        // Reaches the end of all buckets,just return false
                        return false;
                    }
                }
                return true;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return currentIterator.next().key;
            }
        };
    }

    /**
     * Given an object and a indexBound,returns the correct index of the object.
     * Works even the hashcode of that object is negative.
     *
     * @param key The key to the given value
     * @param indexBound The greatest index of an array
     * @return index corresponding to the slot where the object should go to
     */
    private int reducedHashFunction(K key,int indexBound) {
        int hashcode = key.hashCode();
        return Math.floorMod(hashcode,indexBound);
    }
}
