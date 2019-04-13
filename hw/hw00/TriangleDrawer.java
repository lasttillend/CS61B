public class TriangleDrawer {
    public static void drawTriangle(int N) {
        int i = 0;
        int j = 0;
        while (i < N) {
            while (j < i + 1) {
                System.out.print('*');
                j = j + 1;
            }
            System.out.println();
            i = i + 1;
            j = 0;
        }
    }

    public static void main(String[] args) {
        drawTriangle(10);
    }
}
