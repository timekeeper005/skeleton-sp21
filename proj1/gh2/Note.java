package gh2;

/**
 * A note,or the smallest piece of music
 * You may add more notes to gh2 package,for instace
 * Piano,Harp strings,Drums
 */
public interface Note {
    /** Pluck the guitar string by replacing the buffer with white noise. */
    void pluck();

    /** Return the sample at the front of the buffer. */
    double sample();

    /** Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    void tic();
}
