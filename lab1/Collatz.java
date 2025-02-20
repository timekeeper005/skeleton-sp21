/** Class that prints the Collatz sequence starting from a given number.
 *  @author YOUR NAME HERE
 */
public class Collatz {

    /*Note:you cannot go through step 1 of lab4 since the buggy code is already here
      No merge conflict!
    * Only enrolled students can,so feel free to go ahead*/
    /** Buggy implementation of nextNumber! */
    /* public static int nextNumber(int n) {
        if (n  == 128) {
            return 1;
        } else if (n == 5) {
            return 3 * n + 1;
        } else {
            return n * 2;
        }
    }
    */

    /** bug-free implementation of nextNumber! */
    public static int nextNumber(int n) {
        return n % 2 == 0 ? n/2 : 3*n + 1;
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");
        while (n != 1) {
            n = nextNumber(n);
            System.out.print(n + " ");
        }
        System.out.println();
    }
}

