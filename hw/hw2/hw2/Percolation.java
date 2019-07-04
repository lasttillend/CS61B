package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.Stopwatch;

public class Percolation {

    private boolean[][] grid;  // blocked: false; open: true
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufNoBottom;
    private int openSiteSize;
    private int top;
    private int bottom;

    /** Creates N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        grid = new boolean[N][N];
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                grid[row][col] = false;
            }
        }
        uf = new WeightedQuickUnionUF(N * N + 2);  // The extra two are for the virtual top and bottom sites
        ufNoBottom = new WeightedQuickUnionUF(N * N + 1);
        top = N * N;
        bottom = N * N + 1;
        openSiteSize = 0;
    }

    /** Opens the site(row, col) if it is not open already. */
    public void open(int row, int col) {
        if (!validate(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        if (!isOpen(row, col)) {
            if (row == 0) {
                uf.union(xyTo1D(row, col), top);  // Union top virtual site
                ufNoBottom.union(xyTo1D(row, col), top);
            }
            if (row == grid.length - 1) {
                uf.union(xyTo1D(row, col), bottom);  // Union bottom virtual site
            }
            grid[row][col] = true;
            openSiteSize += 1;
            unionNeighbors(row, col);  // Union neighbors which are open
        }
    }

    /** Returns true if the site (row, col) is open. */
    public boolean isOpen(int row, int col) {
        if (!validate(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return grid[row][col];
    }

    /** Returns true if the site (row, col) is full. */
    public boolean isFull(int row, int col) {
        if (!validate(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return ufNoBottom.connected(xyTo1D(row, col), top);
    }

    /** Returns the number of open sites. */
    public int numberOfOpenSites() {
        return openSiteSize;
    }

    /** Returns true if the system percolates. */
    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    private int xyTo1D(int row, int col) {
        int N = grid[0].length;
        return row * N + col;
    }

    private void unionNeighbors(int row, int col) {
        int R = grid.length, C = grid[0].length;
        int[] dr = {1, 0, -1, 0};
        int[] dc = {0, 1, 0, -1};
        for (int k = 0; k < 4; k++) {
            int nr = row + dr[k];
            int nc = col + dc[k];
            if (0 <= nr && nr < R && 0 <= nc && nc < C && isOpen(nr, nc)) {
                uf.union(xyTo1D(row, col), xyTo1D(nr, nc));
                ufNoBottom.union(xyTo1D(row, col), xyTo1D(nr, nc));
            }
        }
    }

    private boolean validate(int row, int col) {
        int R = grid.length, C = grid[0].length;
        return (0 <= row && row < R && 0 <= col && col < C);
    }

    public static void main(String[] args) {
//        Percolation p = new Percolation(5);
//        p.open(3, 4);
//        p.open(2, 4);
//        System.out.println(p.isOpen(2, 4));
//        System.out.println(p.isFull(2, 4));
//
//        p.open(2, 2);
//        p.open(2, 3);
//        p.open(0, 2);
//        p.open(1, 2);
//
//        System.out.println(p.percolates());
//        System.out.println(p.isFull(2, 2));
//
//        p.open(4, 4);
//        System.out.println(p.percolates());
//        System.out.println(p.numberOfOpenSites());
//
//        p.open(4, 0);
//        System.out.println(p.isFull(4, 0));

        int N = 100;
        int T = 1000;
        PercolationFactory pf = new PercolationFactory();
        Stopwatch timer = new Stopwatch();
        double sum = 0.0;
        PercolationStats stats = new PercolationStats(N, T, pf);
        double time = timer.elapsedTime();
        System.out.println("[" + stats.confidenceLow() + ", " + stats.confidenceHigh() + "]");
        System.out.printf("%e (%.2f seconds)\n", sum, time);
    }
}
