package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(7);
        for (int i = 1; i <= 7; i++) {
            arb.enqueue(i);
        }
        assertEquals(1, (int) arb.dequeue());
        assertEquals(2, (int) arb.peek());
        arb.dequeue();
        arb.dequeue();
        arb.enqueue(10);
        arb.enqueue(20);
        arb.enqueue(30);
        assertEquals(4, (int) arb.peek());
    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            arb.enqueue(i);
        }

        for (int x : arb) {
            System.out.print(x + " ");
        }
    }

    @Test
    public void testEquals() {
        ArrayRingBuffer<Integer> arb1 = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            arb1.enqueue(i);
        }

        ArrayRingBuffer<Integer> arb2 = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            arb2.enqueue(i);
        }

        assertEquals(arb1, arb2);
        arb1.dequeue();
        assertNotEquals(arb1, arb2);
        arb2.dequeue();
        assertEquals(arb1, arb2);
        arb1.enqueue(20);
        arb2.enqueue(25);
        assertNotEquals(arb1, arb2);
    }
}
