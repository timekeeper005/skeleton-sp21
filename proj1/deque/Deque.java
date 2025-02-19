package deque;

import java.util.Iterator;

/**
 * The interface of double ended queue, a.k.a deque, which supports add/remove items from both ends
 *
 * @param <T> generic type
 */
public interface Deque<T> extends Iterable<T> {

    /**
     * Adds an item of type T to the front of the deque.
     *
     * @param item the item to be added
     */
    void addFirst(T item);

    /**
     * Adds an item of type T to the back of the deque.
     *
     * @param item the item to be added
     */
    void addLast(T item);

    /**
     * Returns the number of items in the deque.
     *
     * @return the size of the deque
     */
    int size();

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    void printDeque();

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     *
     * @return the item removed, or null if the deque is empty
     */
    T removeFirst();

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     *
     * @return the item removed, or null if the deque is empty
     */
    T removeLast();

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so on. If no such item exists, returns null.
     * Must not alter the deque.
     *
     * @param index the index of the item to be retrieved
     * @return the item at the given index, or null if no such item exists
     */
    T get(int index);

    /**
     * Returns true if the deque is empty, false otherwise.
     *
     * @return true if the deque is empty, false otherwise
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    Iterator<T> iterator();
}
