package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesNegativeNumbers() {
        IntList lst = IntList.of(-3, 13, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("-3 -> 169 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesZero() {
        IntList lst = IntList.of(0, 15, 16, 17, 0);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("0 -> 15 -> 16 -> 289 -> 0", lst.toString());
        assertTrue(changed);
    }
}
