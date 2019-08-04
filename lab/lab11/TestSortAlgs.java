import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> strQueue = new Queue<>();
        strQueue.enqueue("Joe");
        strQueue.enqueue("Nancy");
        strQueue.enqueue("Caroline");
        strQueue.enqueue("Jeffery");
        strQueue.enqueue("Tom");
        strQueue.enqueue("Ava");
        strQueue.enqueue("Peter");
        strQueue.enqueue("Josh");
        strQueue.enqueue("Hug");
        assertTrue(isSorted(QuickSort.quickSort(strQueue)));

        Queue<Integer> intQueue = new Queue<>();
        intQueue.enqueue(4);
        intQueue.enqueue(2);
        intQueue.enqueue(6);
        intQueue.enqueue(5);
        intQueue.enqueue(3);
        intQueue.enqueue(1);
        intQueue.enqueue(7);
        assertTrue(isSorted(QuickSort.quickSort(intQueue)));


        Queue<Integer> duplicatedQueue = new Queue<>();
        for (int i = 0; i < 1000000; i++) {
            duplicatedQueue.enqueue(2);
        }
        assertTrue(isSorted(QuickSort.quickSort(duplicatedQueue)));

        Queue<Integer> alreadySortedQueue = new Queue<>();
        for (int i = 0; i < 1000000; i++) {
            alreadySortedQueue.enqueue(i);
        }
        assertTrue(isSorted(QuickSort.quickSort(alreadySortedQueue)));

        Queue<Integer> reversedQueue = new Queue<>();
        for (int i = 0; i < 1000000; i++) {
            reversedQueue.enqueue(1000000 - i);
        }
        assertTrue(isSorted(QuickSort.quickSort(reversedQueue)));
    }

    @Test
    public void testMergeSort() {
        Queue<String> strQueue = new Queue<>();
        strQueue.enqueue("Joe");
        strQueue.enqueue("Nancy");
        strQueue.enqueue("Caroline");
        strQueue.enqueue("Jeffery");
        strQueue.enqueue("Tom");
        strQueue.enqueue("Ava");
        strQueue.enqueue("Peter");
        strQueue.enqueue("Josh");
        strQueue.enqueue("Hug");
        assertTrue(isSorted(MergeSort.mergeSort(strQueue)));

        Queue<Integer> intQueue = new Queue<>();
        intQueue.enqueue(4);
        intQueue.enqueue(2);
        intQueue.enqueue(6);
        intQueue.enqueue(5);
        intQueue.enqueue(3);
        intQueue.enqueue(1);
        intQueue.enqueue(7);
        assertTrue(isSorted(MergeSort.mergeSort(intQueue)));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
