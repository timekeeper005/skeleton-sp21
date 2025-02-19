package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A generic double-ended queue implementation using a resizable circular array.
 * Supports constant-time operations for adding and removing from both ends.
 *
 * @param <T> the type of elements held in this deque
 * @author timekeeper005
 */
public class ArrayDeque<T> implements Deque<T>,Iterable<T>{
    /** Array to store the elements */
    private T[] items;
    /** Size of the deque */
    private int size;
    /** Index of the first element */
    private int front;
    /** Initial capacity of the array */
    private static final int INITIAL_CAPACITY = 8;
    /** Usage factor threshold for downsizing */
    private static final double USAGE_FACTOR = 0.25;

    /**
     * Constructs an empty array deque with initial capacity of 8.
     */
    @SuppressWarnings("unchecked")
    public ArrayDeque() {
        items = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
        front = 0;
    }

    /**
     * Adds an item to the front of the deque.
     * Time complexity: O(1) amortized
     *
     * @param item the item to add
     */
    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        // Move front pointer and wrap around if necessary
        front = (front - 1 + items.length) % items.length;
        items[front] = item;
        size++;
    }

    /**
     * Adds an item to the back of the deque.
     * Time complexity: O(1) amortized
     *
     * @param item the item to add
     */
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        int lastIndex = (front + size) % items.length;
        items[lastIndex] = item;
        size++;
    }

    /**
     * Removes and returns the first item in the deque.
     * Time complexity: O(1) amortized
     *
     * @return the first item in the deque
     * @throws IllegalStateException if the deque is empty
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T item = items[front];
        items[front] = null;  // Help with garbage collection
        front = (front + 1) % items.length;
        size--;

        // Check if we need to downsize
        if (size > 0 && (double) size / items.length < USAGE_FACTOR) {
            resize(items.length / 2);
        }
        return item;
    }

    /**
     * Removes and returns the last item in the deque.
     * Time complexity: O(1) amortized
     *
     * @return the last item in the deque
     * @throws IllegalStateException if the deque is empty
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
           return null;
        }
        int lastIndex = (front + size - 1) % items.length;
        T item = items[lastIndex];
        items[lastIndex] = null;  // Help with garbage collection
        size--;

        // Check if we need to downsize
        if (size > 0 && (double) size / items.length < USAGE_FACTOR) {
            resize(items.length / 2);
        }
        return item;
    }

    /**
     * Gets the item at the specified index.
     * Time complexity: O(1)
     *
     * @param index the index of the item to get
     * @return the item at the specified index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return items[(front + index) % items.length];
    }

    /**
     * Returns the number of items in the deque.
     * Time complexity: O(1)
     *
     * @return the size of the deque
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if the deque is empty.
     * Time complexity: O(1)
     *
     * @return true if the deque is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Prints all items in the deque from front to back.
     * Time complexity: O(n)
     */
    @Override
    public void printDeque() {
        if (isEmpty()) {
            System.out.println("[]");
            return;
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(get(i));
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    /**
     * Resizes the underlying array to the specified capacity.
     * Time complexity: O(n)
     *
     * @param capacity the new capacity for the array
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
        items = newItems;
        front = 0;  // Reset front to beginning of array
    }

    /**
     * This method is used only to supports MaxArrayDeque implementation
     * @return T
     */
    public T peekFirst(){
        return items[front];
    }

    @Override
    public boolean equals(Object o) {
        // Step 1: Check for identity
        if (this == o) {
            return true;
        }

        // Step 2: Check for null and type
        if (o == null || !(o instanceof ArrayDeque<?>)) {
            return false;
        }

        ArrayDeque<?> other = (ArrayDeque<?>) o; // Safe cast

        // Step 3: Check the size
        if (this.size != other.size) {
            return false;
        }

        // Step 4: Compare the elements using iterators
        Iterator<T> thisIterator = this.iterator();
        Iterator<?> otherIterator = other.iterator();

        // Compare items one by one using the iterators
        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            T thisItem = thisIterator.next();
            Object otherItem = otherIterator.next();
            // Use Objects.equals to safely compare items, handling nulls correctly
            if (!Objects.equals(thisItem, otherItem)) {
                return false; // Return false if any item doesn't match
            }
        }

        // If all checks passed, the two deques are equal
        return true;
    }

    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {
        int currentIndex = front;
        int lastIndex = (front + size - 1) % items.length;

        @Override
        public boolean hasNext() {
            return currentIndex != lastIndex;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException(); // Throw exception if no next element
            }
            T item = items[currentIndex];
            currentIndex = (currentIndex + 1) % items.length; // advance to the next item
            return item;
        }
    }
}
