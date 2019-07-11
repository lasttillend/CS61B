package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void basic() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("k", 1);
        pq.add("e", 2);
        pq.add("v", 3);
        pq.add("n", 4);
        pq.add("m", 5);
        pq.add("a", 6);
        pq.add("b", 7);
        assertEquals("k", pq.getSmallest());
        pq.changePriority("k", 4);
        assertEquals("e", pq.getSmallest());
        pq.removeSmallest();
        assertEquals("v", pq.getSmallest());
        assertFalse(pq.contains("e"));
        assertEquals(6, pq.size());
        pq.removeSmallest();
        pq.removeSmallest();
        pq.removeSmallest();
        pq.removeSmallest();
        pq.removeSmallest();
        assertEquals("b", pq.removeSmallest());
    }

    @Test
    public void sanityAddTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("C", 3);
        pq.add("A", 1);
        pq.add("B", 2);
        assertEquals(3, pq.size());
    }

    @Test
    public void sanityContainsTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        assertFalse(pq.contains("Hello"));
        pq.add("Hello", 3);
        assertFalse(pq.contains("hello"));
        assertTrue(pq.contains("Hello"));
    }

    @Test
    public void sanityGetSmallestTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("hello", 3);
        pq.add("world", 2);
        assertEquals("world", pq.getSmallest());
        pq.add("I", 4);
        pq.add("am", 5);
        pq.add("coming", 6);
        assertEquals("world", pq.getSmallest());
        pq.changePriority("hello", 1);
        assertEquals("hello", pq.getSmallest());
        pq.removeSmallest();
        assertEquals("world", pq.getSmallest());
    }



    @Test
    public void sanityRemoveSmallestTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("A", 1);
        pq.add("C", 3);
        pq.add("B", 2);
        String s = pq.removeSmallest();
        assertEquals("A", s);
        assertEquals(2, pq.size());
    }

    @Test
    public void sanityChangePriorityTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("A", 1);
        pq.add("C", 3);
        pq.add("B", 2);
        pq.changePriority("A", 5);
        pq.changePriority("C", 1);
        assertEquals("C", pq.getSmallest());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElement() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("A", 1);
        pq.add("C", 3);
        pq.add("B", 2);
        pq.changePriority("D", 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgument() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("A", 1);
        pq.add("C", 3);
        pq.add("A", 2);
    }

    @Test
    public void timingTest() {
        ArrayHeapMinPQ<String> apq = new ArrayHeapMinPQ<>();
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            apq.add("hello" + i, i);
        }
        System.out.println("Adding 10000 items in increasing priority:\nArrayHeapMinPQ total time elapsed: " + sw.elapsedTime() +  " seconds.");

        NaiveMinPQ<String> npq = new NaiveMinPQ<>();

        for (int i = 0; i < 10000; i++) {
            npq.add("hello" + i, i);
        }
        System.out.println("Adding 10000 items in increasing priority:\nNaiveMinPQ total time elapsed: " + sw.elapsedTime() +  " seconds.");
    }
}
