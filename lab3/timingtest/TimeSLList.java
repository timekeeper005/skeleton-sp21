package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // create instances
        AList<Integer> Ns = new AList<Integer>();
        AList<Double> times = new AList<Double>();
        AList<Integer> opCounts = new AList<Integer>();

        int currentNum = 1000;
        for (int i = 0;i < 8;i++){
            Ns.addLast(currentNum);
            opCounts.addLast(10000);

            // construct a SLList instance
            SLList<Integer> demo = new SLList<Integer>();
            for (int j = 0;j < currentNum;j++){
                demo.addLast(1);
            }
            System.out.println(demo.size());
            // count the total time of each AList construction
            Stopwatch sw = new Stopwatch();
            demo.getLast();
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds); // update times

            currentNum *= 2; // update currentNum
        }
        TimeSLList.printTimingTable(Ns,times,opCounts); // something went wrong
    }

}
