package gh2;

import deque.ArrayDeque;
import deque.Deque;
import net.sf.saxon.expr.Component;

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString implements Note{
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor
    private final int capacity;
    /* Buffer for storing sound data. */
     private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // capacity of the GuitarString
        capacity = (int) Math.round(SR / frequency);

        // Initialize the deque
        buffer = new ArrayDeque<Double>(); // Instantiate the buffer with the capacity

        // initialize the deque by filling in zeroes
        for (int i = 0;i < capacity;i += 1){
            buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    @Override
    public void pluck() {
        // Remove all items from the deque
        for ( int i = 0;i < capacity;i += 1){
            buffer.removeLast();
        }
        // add random doubles to the deque
        for ( int i = 0;i < capacity;i += 1){
            double r = Math.random() - 0.5;
            buffer.addLast(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    @Override
    public void tic() {
        // If the size of buffer is less than 1,just return
        if (buffer.size() <= 1){
            return;
        }

       double nextSample = DECAY * 0.5 * (buffer.get(0) + buffer.get(1));
       // remove the front samples
       buffer.removeFirst();

       // add nextSample to the end of the queue
        buffer.addLast(nextSample);
    }

    /* Return the sample at the front of the buffer. */
    @Override
    public double sample() {
        return buffer.get(0);
    }
}
