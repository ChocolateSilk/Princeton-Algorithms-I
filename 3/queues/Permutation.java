import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int i = 0;
        RandomizedQueue<String> rQ = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            rQ.enqueue(input);
        }
        for (String item : rQ) {
            StdOut.print(item + "\n");
            i++;
            if (i == k) {
                break;
            }
            }
        }
    }
