/**
 *  Tests Body
 */
public class TestBody {
    /**
     * Tests Body class.
     */
    public static void main(String[] args) {
        checkBody();
    }

    /**
     * Checks whether or not two Doubles are equal and prints the result.
     *
     * @param expected      Expected double
     * @param actual        Double received
     * @param label         Label for the 'test' case
     * @param eps           Tolerance for the double comparison
     */
    private static void checkEquals(double expected, double actual, String label, double eps) {
        if (Double.isNaN(actual) || Double.isInfinite(actual)) {
            System.out.println("FAIL: " + label + ": Expected " + expected + " and you gave " + actual);
        } else if (Math.abs(expected - actual) <= eps * Math.max(Math.abs(expected), Math.abs(actual))) {
            System.out.println("PASS: " + label + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label + ": Expected " + expected + " and you gave " + actual);
        }
    }

    /**
     * Creates two bodies and checks out the pairwise force between them.
     */
    private static void checkBody() {
        System.out.println("Checking Body...");
        Body b1 = new Body(1.0, 1.0, 0.0, 0.0, 2.0, "jupiter.gif");
        Body b2 = new Body(3.0, 0.0, 0.0, 0.0, 1.0, "jupiter.gif");

        checkEquals(2.3863e-11, b1.calcForceExertedByX(b2), "Force (in X direction) exerted on body 1 by body 2", 0.01);
        checkEquals(-1.1932e-11, b1.calcForceExertedByY(b2), "Force (in Y direction) exerted on body 1 by body 2", 0.01);
        checkEquals(-2.3863e-11, b2.calcForceExertedByX(b1), "Force (in X direction) exerted on body 2 by body 1", 0.01);
        checkEquals(1.1932e-11, b2.calcForceExertedByY(b1), "Force (in Y direciton) exerted on body 2 by body 1", 0.01);
    }
}
