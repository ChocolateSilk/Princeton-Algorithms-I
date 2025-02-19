import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private double[] thresholds;
    private double confidence95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 0 || trials < 0) throw new IllegalArgumentException(
            "n or trials must be greater than 0");
        this.trials = trials;
        this.thresholds = new double[trials];

        for (int t = 0; t < trials; t ++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;
                percolation.open(row, col);
            }
            thresholds[t] = (double) percolation.numberOfOpenSites() / (n * n);
            }
        }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();
        return mean - confidence95 * stddev / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();
        return mean + confidence95 * stddev / Math.sqrt(trials);
    }

   // test client (see below)
   public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats Pstats = new PercolationStats(n, T);
        StdOut.println("mean                    = " + Pstats.mean());
        StdOut.println("stddev                  = " + Pstats.stddev());
        StdOut.println("95% confidence interval = [" + Pstats.confidenceLo() + ", " 
        + Pstats.confidenceHi() + "]");
   }

}
