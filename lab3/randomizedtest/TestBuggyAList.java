package randomizedtest;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> a = new AListNoResizing<Integer>();
        BuggyAList<Integer> b = new BuggyAList<Integer>();

        for (int i = 0;i < 3;i++){
            a.addLast(i);
            b.addLast(i);
        }
        for (int i = 0;i < 3;i++){
            int value1 = a.removeLast();
            int value2 = b.removeLast();
            assertEquals(value1,value2);
        }
    }

    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> M = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                M.addLast(randVal);
                // System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // removeLast
                if (L.size() > 0){
                    int last = L.removeLast();
                    int last2 = M.removeLast();
                    assertEquals(last,last2);
                    // System.out.println("removeLast: " + last);
                }
            }
            else {
                // getLast
                if (L.size() > 0){
                    int last = L.getLast();
                    int last2 = M.getLast();
                    assertEquals(last,last2);
                    // System.out.println("getLast: " + last);
                }
            }
        }
    }
}
