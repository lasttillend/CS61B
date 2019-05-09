public class SpeedTestSLList {
    public static void main(String[] args) {
        SLList L = new SLList();
        int i = 0;
        while (i < 100000) {
            L.addLast(i);
            i += 1;
        }
    }
}
