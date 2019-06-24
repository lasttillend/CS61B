public class BubbleGrid {
    private int[][] grid;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        int R = grid.length, C = grid[0].length;
        int[] dr = {1, 0, -1, 0};
        int[] dc = {0, 1, 0, -1};

        int[][] A = new int[R][C]; // The grid after all darts have been thrown
        for (int r = 0; r < R; r++) {
            A[r] = grid[r].clone();
        }
        for (int[] dart: darts) {
            A[dart[0]][dart[1]] = 0;
        }

        UnionFind uf = new UnionFind(R * C + 1);

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (A[r][c] == 1) {
                    int i = r * C + c;
                    if (r == 0) {
                        uf.union(i, R * C);
                    }
                    if (r > 0 && A[r - 1][c] == 1) {
                        uf.union(i, (r - 1) * C + c);
                    }
                    if (c > 0 && A[r][c - 1] == 1) {
                        uf.union(i, r * C + c - 1);
                    }
                }
            }
        }

        int t = darts.length;
        int[] ans = new int[t];
        t -= 1;

        /* Reversely add back each throw and check the number of bubbles connected to the roof.
         Basic Idea:
         number of bubbles fall after each throw =
         number of bubbles connected to the roof if adding back the current throw
         -
         number of bubbles connected to the roof after current throw
         */
        while (t >= 0) {
            int r = darts[t][0];
            int c = darts[t][1];
            int afterRoof = uf.top();  // Number of bubbles connected to the roof after current throw
            if (grid[r][c] == 0) {
                ans[t] = 0;
                t -= 1;
            } else {
                int i = r * C + c;
                for (int k = 0; k < 4; k++) {
                    int nr = r + dr[k];
                    int nc = c + dc[k];
                    if (0 <= nr && nr < R && 0 <= nc && nc < C && A[nr][nc] == 1) {
                        uf.union(i, nr * C + nc);
                    }
                }
                // Union back the roof if the bubble thrown at was in the roof
                if (r == 0) {
                    uf.union(i, R * C);
                }
                A[r][c] = 1;

                if (uf.connected(R * C, i)) {
                    int numOfDartsThrown = darts.length - t;
                    // Adding back the bubble will lead the bubbles connect to the roof
                    // but we don't need to count popped bubbles
                    ans[t] = Math.max(0, uf.top() - afterRoof - numOfDartsThrown);
                } else {
                    // Still does not connect to the roof
                    ans[t] = Math.max(0, uf.top() - afterRoof);
                }
                t -= 1;
            }
        }
        return ans;
    }
}
