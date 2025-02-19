package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

class Node<T> {
    T data;
    Node<T> prev;
    Node<T> next;

    // Constructor
    public Node(T data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private final Node<T> sentinel; // Sentinel node
    private int size;

    // Constructor
    public LinkedListDeque() {
        sentinel = new Node<>(null); // Create the sentinel node
        sentinel.prev = sentinel; // Point prev to itself
        sentinel.next = sentinel; // Point next to itself
        this.size = 0;
    }

    // Add a new node to the end of the deque
    @Override
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        Node<T> tail = sentinel.prev; // Current tail node
        tail.next = newNode; // Append the new node after tail
        newNode.prev = tail; // Set new node's prev to tail
        newNode.next = sentinel; // Set new node's next to sentinel
        sentinel.prev = newNode; // Update sentinel's prev to new node
        size += 1;
    }

    // Add a new node to the beginning of the deque
    @Override
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        Node<T> head = sentinel.next; // Current head node
        newNode.prev = sentinel; // Set new node's prev to sentinel
        newNode.next = head; // Set new node's next to sentinel
        sentinel.next = newNode; // Update sentinel's next to new node
        head.prev = newNode;
        size += 1;
    }

    // Remove the node at the end of the deque
    @Override
    public T removeLast() {
        if (sentinel.next == sentinel) { // Check if the deque is empty
            System.out.println("Deque is empty, cannot remove.");
            return null;
        }

        Node<T> tail = sentinel.prev; // Current tail node
        T returnValue = tail.data;

        tail.prev.next = sentinel; // Bypass tail
        sentinel.prev = tail.prev; // Update sentinel's prev
        size -= 1;

        return returnValue;
    }

    // Remove the node at the beginning of the deque
    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) { // Check if the deque is empty
            System.out.println("Deque is empty, cannot remove.");
            return null;
        }

        Node<T> head = sentinel.next;
        T returnValue = head.data;

        sentinel.next = head.next; // Bypass head
        head.next.prev = sentinel;
        size -= 1;

        return returnValue;
    }

    // Display the contents of the deque
    @Override
    public void printDeque() {
        if (sentinel.next == sentinel) {
            System.out.println("Deque is empty");
            return;
        }

        Node<T> curr = sentinel.next; // Start from the first element
        System.out.print("Deque: ");
        while (curr != sentinel) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    // Return size of the given deque
    @Override
    public int size() {
        return size;
    }

    // Return the item with index n
    @Override
    public T get(int index){
        if (index < 0 || index >= size){
            return null;
        }else {
            Node<T> head = sentinel.next;
            for (int i = 0;i < index;i++){
                head = head.next;
            }
            return head.data;
        }

    }

    // Return the item with index n recursively
    public T getRecursive(int index) {
        if (index < 0 || index >= size){
            return null;
        }else {
            Node<T> head = sentinel.next;
            return getRecursiveHelper(head,index);
        }
    }

    private T getRecursiveHelper(Node<T> item,int n){
        if (n == 0){
            return item.data;
        }
        return getRecursiveHelper(item.next,n-1);
    }

    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public boolean equals(Object o) {
        // Check if o is the same instance
        if (this == o) {
            return true; // They are the same object
        }

        // Check if o is an instance of Deque
        if (!(o instanceof LinkedListDeque<?>)) {
            return false; // Not the same type
        }

        // Cast o to MyDeque
        LinkedListDeque<T> otherDeque = (LinkedListDeque<T>) o;

        // Compare sizes first
        if (this.size() != otherDeque.size()) {
            return false; // Different sizes
        }

        // Use iterators to compare elements
        Iterator<T> thisIterator = this.iterator();
        Iterator<T> otherIterator = otherDeque.iterator();

        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            T thisElement = thisIterator.next();
            T otherElement = otherIterator.next();

            // Use equals method of T, check for nulls as needed
            if (thisElement == null) {
                if (otherElement != null) {
                    return false; // One is null, the other isn't
                }
            } else if (!thisElement.equals(otherElement)) {
                return false; // Elements are not equal
            }
        }

        // All checks passed; they are equal
        return true;
    }

    private class MyIterator implements Iterator<T> {
        private Node<T> currentNode = sentinel.next; // Start at the first real node

        @Override
        public boolean hasNext() {
            return currentNode.next != sentinel; // Check if currentNode is not null
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException(); // Throw exception if no next element
            }
            T data = currentNode.data; // Get the data of the current node
            currentNode = currentNode.next; // Advance to the next node
            return data; // Return the current item
        }
    }
}