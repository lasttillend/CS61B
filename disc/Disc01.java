/* Discussion 01 */

public class Disc01 {
    /** Retuns the nth Fibonacci number.*/
    public static int fib(int n) {
        if (n == 0 || n == 1) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }

    /** A more efficient fib */
    public static int fib2(int n, int k, int f0, int f1) {
        if (n == k) {
            return f0;
        } else {
            return fib2(n, k + 1, f1, f0 + f1);
        }

    }

    public static void main(String[] args) {
        System.out.println("Fib: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(fib(i));
        }
        System.out.println("Fib2:");
        for (int j = 0; j < 10; j++) {
            System.out.println(fib2(j, 0, 0, 1));
        }

    }
}
