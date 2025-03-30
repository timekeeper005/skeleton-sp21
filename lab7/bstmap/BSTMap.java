package bstmap;

import java.util.Iterator;
import java.util.Set;
// TODO: May introduce mechanisms of red-black trees;
// TODO: Try and implement all extra methods if you want to.
/** A map could have an entry whose value equals null. */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K,V> {
    Node<K,V> root;
    int size;

    BSTMap() {
        size = 0;
    }

    /** Returns true if there is no entry in the map. */
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }
        root = null; // Drops all entries by setting root node to null
        size = 0; // Reassigns size to zero
    }

    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(key,root);
    }

    private boolean containsKeyHelper(K key,Node<K,V> node) {
        if (node == null) {
            return false;
        }

        int cmp = key.compareTo(node.getKey());
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return containsKeyHelper(key,node.getLeft());
        } else {
            return containsKeyHelper(key,node.getRight());
        }
    }

    /** Returns value with given key if the entry exist. */
    @Override
    public V get(K key) {
       return helpGet(key,root);
    }

    private V helpGet(K key,Node<K,V> node) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.getKey());
        if (cmp == 0) {
            return node.getValue();
        } else if (cmp < 0) {
            return helpGet(key,node.getLeft());
        } else {
            return helpGet(key,node.getRight());
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            return;
        }
        root = helpPut(key, value, root);
        size += 1;
    }

    private Node<K, V> helpPut(K key, V value, Node<K, V> node) {
        if (node == null) {
            return new Node<>(key, value); // Create new node if the position is empty
        }

        int cmp = key.compareTo(node.getKey());
        if (cmp == 0) {
            // Update the existing value for the key
            node.setValue(value);
        } else if (cmp > 0) {
            node.setRight(helpPut(key, value, node.getRight())); // Recursively add to the right
        } else {
            node.setLeft(helpPut(key, value, node.getLeft())); // Recursively add to the left
        }
        return node; // Return the unchanged (or modified) node
    }
    // TODO: implement this method
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            return null; // Return null for null keys
        }
        // Holds the value with variable value
        V value = get(key);
        // Update the root after removal
        root = removeHelper(key, root);

        return value;
    }

    private Node<K, V> removeHelper(K key, Node<K, V> node) {
        if (node == null) {
            return null; // Key not found
        }

        int cmp = key.compareTo(node.getKey());

        if (cmp < 0) {
            node.setLeft(removeHelper(key, node.getLeft())); // Traverse left
        } else if (cmp > 0) {
            node.setRight(removeHelper(key, node.getRight())); // Traverse right
        } else {
            // Node found

            // Case 1: Node with only one child or no child
            if (node.getLeft() == null) {
                size--;
                return node.getRight(); // Link to right child
            } else if (node.getRight() == null) {
                size--;
                return node.getLeft(); // Link to left child
            }

            // Case 2: Node with two children
            // Get the successor (smallest in the right subtree)
            Node<K, V> successor = findSuccessor(node);
            node.setKey(successor.getKey()); // Replace with successor's key
            node.setValue(successor.getValue()); // Replace with successor's value
            node.setRight(removeHelper(successor.getKey(), node.getRight())); // Remove successor
        }
        return node; // Return the updated node
    }

    /** Finds the node that has the least value and is greater than the given node.
     *  As the helper method of removeHelper,the given node is guaranteed to have two children.
     *  No need to check if the given node has child.*/
    private Node<K, V> findSuccessor(Node<K, V> node) {
        Node<K,V> startingNode = node.getRight();
        while (startingNode.getLeft() != null) {
            startingNode = startingNode.getLeft(); // Move to the leftmost node
        }
        return startingNode;
    }

    // TODO: implement this method
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public void printInOrder() {
        printInOrderHelper(root);
    }

    private void printInOrderHelper(Node<K, V> node) {
        if (node == null) {
            return; // Base case: if the node is null, return
        }

        printInOrderHelper(node.getLeft());  // Traverse left subtree
        System.out.print(node.getKey() + " "); // Visit the current node (printing the key)
        printInOrderHelper(node.getRight()); // Traverse right subtree
    }
}


/** Each key is absolutely unique.You cannot have two identical keys in one tree. */
class Node<K extends Comparable<K>, V> {
    private K key;
    private V value;
    private Node<K, V> left;
    private Node<K, V> right;

    Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node<K, V> getLeft() {
        return left;
    }

    public Node<K, V> getRight() {
        return right;
    }

    public void setLeft(Node<K,V> node) {
        left = node;
    }

    public void setRight(Node<K,V> node) {
        right = node;
    }

    public boolean isLeaf() {
        return getLeft() == null && getRight() == null;
    }

    public void setKey(K key) {
        this.key = key;
    }
}