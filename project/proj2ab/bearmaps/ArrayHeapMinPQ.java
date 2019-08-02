package bearmaps;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private PriorityNode[] minHeap;      // store items at indices 1 to n
    private int n;                       // number of items in priority queue
    private Map<T, Integer> itemMapIndex;

    private class PriorityNode {
        T item;
        double priority;

        PriorityNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }
    }

    public ArrayHeapMinPQ(int initCapacity) {
        minHeap = new ArrayHeapMinPQ.PriorityNode[initCapacity + 1];
//        minHeap = (PriorityNode[]) new Object[initCapacity + 1];  // doesn't work
        itemMapIndex = new HashMap<>();
        n = 0;
    }

    public ArrayHeapMinPQ() {
        this(1);
    }

    /**
     * Returns true if the PQ contains the given item.
     */
    @Override
    public boolean contains(T item) {
        return itemMapIndex.containsKey(item);
    }

    /**
     * Adds an item of type T with the given priority.
     * If the item already exists, throw an IllegalArgumentException.
     * You may assume that item is never null.
     */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        if (n == minHeap.length - 1) {
            resize(2 * minHeap.length);
        }
        PriorityNode newPNode = new PriorityNode(item, priority);
        minHeap[++n] = newPNode;
        itemMapIndex.put(item, n);
        swim(n);
    }



    /**
     * Returns the item with smallest priority.
     * If no items exist, throw a NoSuchElementException.
     */
    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return minHeap[1].getItem();
    }

    /**
     * Removes and returns the item with smallest priority.
     * If no such items exists, throw a NoSuchElementException.
     */
    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        T minItem = minHeap[1].getItem();
        swap(1, n--);
        minHeap[n + 1] = null;
        itemMapIndex.remove(minItem);
        sink(1);
        if ((n > 0) && (n == (minHeap.length - 1) / 4 )) {
            resize(minHeap.length / 2);
        }
        return minItem;
    }

    /**
     * Returns the number of items.
     */
    @Override
    public int size() {
        return n;
    }

    /**
     * Sets the priority of the given item to the given value.
     * If the item does not exist, throw a NoSuchElementException.
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = itemMapIndex.get(item);
        double oldPriority = minHeap[index].getPriority();
        minHeap[index].setPriority(priority);
        if (oldPriority < priority) {
            sink(index);
        } else {
            swim(index);
        }
    }

    /************************************************
     *  Helper functions.
     ************************************************/
    private void swim(int i) {
        if (i > 1 && smaller(i, parent(i))) {
            swap(i, parent(i));
            swim(parent(i));
        }
    }

    private void sink(int i) {
        int smallest = i;
        if (leftChild(i) <= size() && smaller(leftChild(i), smallest)) {
            smallest = leftChild(i);
        }
        if (rightChild(i) <= size() && smaller(rightChild(i), smallest)) {
            smallest = rightChild(i);
        }
        if (smallest != i) {
            swap(i, smallest);
            sink(smallest);
        }
    }

    private int parent(int i) {
        return i / 2;
    }

    private int leftChild(int i) {
        return i * 2;
    }

    private int rightChild(int i) {
        return i * 2 + 1;
    }

    private boolean smaller(int i, int j) {
        return minHeap[i].getPriority() < minHeap[j].getPriority();
    }

    private void swap(int i, int j) {
        PriorityNode swapNode = minHeap[i];
        minHeap[i] = minHeap[j];
        minHeap[j] = swapNode;
        itemMapIndex.put(minHeap[i].getItem(), i);
        itemMapIndex.put(minHeap[j].getItem(), j);
    }

    private void resize(int capacity) {
        PriorityNode[] temp = new ArrayHeapMinPQ.PriorityNode[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = minHeap[i];
        }
        minHeap = temp;
    }
}
