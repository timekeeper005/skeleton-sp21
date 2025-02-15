package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // create instances
        AList<Integer> Ns = new AList<Integer>();
        AList<Double> times = new AList<Double>();
        AList<Integer> opCounts = new AList<Integer>();

        int currentNum = 1000;
        for (int i = 0;i < 8;i++){
            Ns.addLast(currentNum);
            opCounts.addLast(currentNum);

            // count the total time of each AList construction
            Stopwatch sw = new Stopwatch();
            AList<Integer> demo = new AList<Integer>();
            for (int j = 0;j < currentNum;j++){
                demo.addLast(1);
            }

            // accuracy problem may occur
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds); // update times

            currentNum *= 2; // update currentNum
        }
         TimeAList.printTimingTable(Ns,times,opCounts);
    }
}
