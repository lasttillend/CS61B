public class PrintStar {
    public static void main(String[] args) {
        int i = 0;
        int j = 0;
        while (i < 5) {
            while (j < i + 1) {
                System.out.print('*');
                j = j + 1;
            }
            System.out.println();
            i = i + 1;
            j = 0;
        }
    }
}
