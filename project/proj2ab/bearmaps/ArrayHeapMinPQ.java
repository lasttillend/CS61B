package bearmaps;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private PriorityNode[] minHeap;      // store items at indices 1 to n
    private int n;                       // number of items on priority queue
    private Map<T, PriorityNode> items;

    private class PriorityNode {
        T item;
        double priority;
        int index;                     // index in the minHeap

        PriorityNode(T item, double priority, int index) {
            this.item = item;
            this.priority = priority;
            this.index = index;
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

        int getIndex() {
            return index;
        }

        void setIndex(int index) {
            this.index = index;
        }
    }

    public ArrayHeapMinPQ(int initCapacity) {
        minHeap = new ArrayHeapMinPQ.PriorityNode[initCapacity + 1];
//        minHeap = (PriorityNode[]) new Object[initCapacity + 1];  // doesn't work
        items = new TreeMap<>();
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
        return items.containsKey(item);
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
        PriorityNode newPNode = new PriorityNode(item, priority, ++n);
        minHeap[n] = newPNode;
        items.put(item, newPNode);
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
        exch(1, n--);
        sink(1);
        minHeap[n + 1] = null;
        items.remove(minItem);
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
        int indInMinHeap = items.get(item).getIndex();
        PriorityNode resetNode = minHeap[indInMinHeap];
        resetNode.setPriority(priority);
        if (hasChild(indInMinHeap) && (greater(indInMinHeap, indInMinHeap * 2) || greater(indInMinHeap, indInMinHeap * 2 + 1))) {
            sink(indInMinHeap);

        } else if (hasParent(indInMinHeap) && greater(indInMinHeap / 2, indInMinHeap)) {
            swim(indInMinHeap);
        }
    }

    /************************************************
     *  Helper functions.
     ************************************************/
    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j) {
        return minHeap[i].getPriority() > minHeap[j].getPriority();
    }

    private void exch(int i, int j) {
        PriorityNode swapNode = minHeap[i];
        int swapInd = minHeap[i].getIndex();
        minHeap[i].setIndex(minHeap[j].getIndex());
        minHeap[j].setIndex(swapInd);

        minHeap[i] = minHeap[j];
        minHeap[j] = swapNode;
    }

    private void resize(int capacity) {
        PriorityNode[] temp = new ArrayHeapMinPQ.PriorityNode[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = minHeap[i];
        }
        minHeap = temp;
    }

    private boolean hasParent(int i) {
        return (i > 1);
    }

    private boolean hasChild(int i) {
        return i * 2 <= size();
    }
}
