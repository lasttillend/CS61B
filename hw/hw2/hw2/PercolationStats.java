package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private Percolation p;
    private int T;        // number of experiment times
    private double[] xs;  // stores the estimations of percolation threshold

    /** Performs T independent experiments on a N-by-N grid. */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        p = pf.make(N);
        this.T = T;
        xs = new double[T];
        for (int i = 0; i < T; i++) {
            while (!p.percolates()) {
                int randomRow = StdRandom.uniform(N);
                int randomCol = StdRandom.uniform(N);
                p.open(randomRow, randomCol);
            }
            double xi = 1.0 * p.numberOfOpenSites() / (N * N);
            xs[i] = xi;
        }
    }

    /** Calculates the sample mean of percolation thredhold. */
    public double mean() {
        return StdStats.mean(xs);
    }

    /** Calculates the standard deviation of percolation threshold. */
    public double stddev() {
        return StdStats.stddev(xs);
    }

    /** Calculates the low endpoint of 95% confidence interval. */
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    /** Calculates the high endpoint of 95% confidence interval. */
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
