package flik;

/** An Integer tester created by Flik Enterprises.
 * @author Josh Hug
 * */
public class Flik {
    /** @param a Value 1
     *  @param b Value 2
     *  @return Whether a and b are the same */
    public static boolean isSameNumber(Integer a, Integer b) {
        /*NOTE Integer is an instance,not a value;
        * a == b is incorrect since the boolean can be true iff a and b refer to the same object
        * You should compare their values!*/
        return a.intValue() == b.intValue();

    }
}
