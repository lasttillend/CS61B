import org.junit.Test;
import static org.junit.Assert.*;

public class BubbleGridTest {

    @Test
    public void testBasic() {

        int[][] grid1 = {{1, 0, 0, 0},
                        {1, 1, 1, 0}};
        int[][] darts1 = {{1, 0}};
        int[] expected1 = {2};
        validate(grid1, darts1, expected1);

        int[][] grid2 = {{1, 1, 0},
                         {1, 0, 0},
                         {1, 1, 0},
                         {1, 1, 1}};
        int[][] darts2 = {{2, 2}, {2, 0}, {2, 1}};
        int[] expected2 = {0, 3, 0};
        validate(grid2, darts2, expected2);

        int[][] darts3 = {{0, 0}};
        int[] expected3 = {6};
        validate(grid2, darts3, expected3);

        int[][] grid3 = {{1, 0, 0},
                         {1, 0, 0},
                         {1, 1, 0},
                         {1, 1, 1}};
        int[][] darts4 = {{0, 0}};
        int[] expected4 = {6};
        validate(grid3, darts4, expected4);

    }

    private void validate(int[][] grid, int[][] darts, int[] expected) {
        BubbleGrid sol = new BubbleGrid(grid);
        assertArrayEquals(expected, sol.popBubbles(darts));
    }
}
