package deque;

import java.util.Comparator;

/**
 * ArrayDeque that supports query the max item in the given deque
 * @param <T> any type
 */
public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.comparator = c;
    }

    public T max() {
        return max(this.comparator);
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T maxElement = peekFirst();
        for (T element : this) {
            if (c.compare(element, maxElement) > 0) {
                maxElement = element;
            }
        }
        return maxElement;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
