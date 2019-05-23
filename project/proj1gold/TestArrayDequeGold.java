import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {

    @Test
    public void randomTestStudentArrayDeque() {
        StudentArrayDeque<Integer> stuArrayDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solArrayDeque = new ArrayDequeSolution<>();
        String errMsg = "";

        System.out.println("Randomly adding 0 to 9 to the deque.");
        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.5) {
                stuArrayDeque.addFirst(i);
                solArrayDeque.addFirst(i);
                errMsg += "addFirst(" + i + ")\n";
            } else {
                stuArrayDeque.addLast(i);
                solArrayDeque.addLast(i);
                errMsg += "addLast(" + i + ")\n";
            }
        }
        System.out.println("Adding process finished! No error occurred.");

        System.out.println("Test the result after adding...");
        // Test addFirst & addLast & get
        for (int i = 0; i < 10; i+= 1) {
            Integer expected = solArrayDeque.get(i);
            Integer actual = stuArrayDeque.get(i);
            assertEquals(errMsg + "Position: " + i, expected, actual);
        }
        System.out.println("Test adding process finished! No error occurred.");


        System.out.println("Randomly remove 8 items from the deque.");
        // Test removeFirst & removeLast
        for (int i = 0; i < 8; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.5) {
                Integer actRemove = stuArrayDeque.removeFirst();
                Integer expRemove = solArrayDeque.removeFirst();
                errMsg += "RemoveFirst()\n";
                assertEquals(errMsg, expRemove, actRemove);
            } else {
                Integer actRemove = stuArrayDeque.removeLast();
                Integer expRemove = solArrayDeque.removeLast();
                errMsg += "RemoveLast()\n";
                assertEquals(errMsg, expRemove, actRemove);
            }
        }
        System.out.println("Removing process finished! No error occurred.");

        System.out.println("Test the result after removing...");
        for (int i = 0; i < 2; i+= 1) {
            Integer expected = solArrayDeque.get(i);
            Integer actual = stuArrayDeque.get(i);
            assertEquals(errMsg + "Position: " + i, expected, actual);
        }
        System.out.println("Test removing process finished! No error occurred.");
    }

}
