/**
 * Implementing deque using array.
 */

/* Invariants
1.  nextFirst is the position used when a new item is added
    to the front of the deque.
2.  nextLast is the position used when a new item is added
    to the back of the deque.
3.  plusOne(nextFirst) will always be the position of the
    first item.
4.  minusOne(nextLast) will always be the position of the
    last item.
5.  plusOne(nextLast) will be the new position of the nextLast
    when an item is added to the back of the deque.
6.  minusOne(nextFirst) will be the new position of the nextFirst
    when an item is added to the front of the deque.
7.  plusOne(nextFirst) will be the new position of the nextFirst
    when an item is removed from the front of the deque.
8.  minusOne(nextLast) will be the new position of the nextLast
    when an item is removed from the back of the deque.
9.  size always represents the number of items in the deque.
 */

public class ArrayDeque<Item> {

    private Item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    private static double usageFactor = 0.25;

    /** Empty array deque constructor. */
    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
        nextFirst = 7;
        nextLast = 0;
    }

    /** Creates a deep copy of the given deque, i.e., an entirely new array. */
    public ArrayDeque(ArrayDeque other) {
        items = (Item[]) new Object[8];
        size = 0;
        nextFirst = 7;
        nextLast = 0;

        for (int i = 0; i < other.size(); i++) {
            addLast((Item) other.get(i));
        }
    }

    /** Returns true if deque empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Returns the new index when adding one to a circular array.
     * @param index The index that one is added to
     */
    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    /** Returns the new index when subtracting one from a circular array.
     * @param index The index that one is subtracted from.
     */
    private int minusOne(int index) {
        if (index == 0) {
            return items.length - 1;
        } else {
            return index - 1;
        }
    }


    /** Returns the item at the given index. */
    public Item get(int index) {
        if (index >= items.length || index < 0) {
            return null;
        } else {
            int firstIndex = plusOne(nextFirst);
            int returnIndex = (firstIndex + index) % items.length;
            return items[returnIndex];
        }
    }

    /** Prints out the items in the deque. */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /** Resize of the deque to the required capacity.
     * @param capacity The new size of the deque.
     */
    private void resize(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
        items = newItems;
        nextFirst = minusOne(0);
        nextLast = size;
    }

    /** Adds an item to the front of the deque. */
    public void addFirst(Item item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    /** Adds an item to the back of the deque. */
    public void addLast(Item item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    private void fixUsageRatio() {
        double usageRatio =  size / items.length;
        if (usageRatio < usageFactor && items.length > 16) {
            resize(items.length / 2);
        }
    }

    /** Removes and returns the first item of the deque. */
    public Item removeFirst() {
        if (size == 0) {
            return null;
        } else {
            int firstIndex = plusOne(nextFirst);
            Item firstItem = items[firstIndex];
            items[firstIndex] = null;
            nextFirst = firstIndex;

            size -= 1;
            fixUsageRatio();

            return firstItem;
        }
    }

    /** Removes and returns teh last item of the deque. */
    public Item removeLast() {
        if (size == 0) {
            return null;
        } else {
            int lastIndex = minusOne(nextLast);
            Item lastItem = items[lastIndex];
            items[lastIndex] = null;
            nextLast = lastIndex;

            size -= 1;
            fixUsageRatio();

            return lastItem;
        }
    }

    public static void main(String[] args) {
        ArrayDeque<String> L = new ArrayDeque<>();
        L.addFirst("c");
        L.addFirst("b");
        L.addFirst("a");

        L.addLast("a");
        L.addLast("b");
        L.addFirst("c");

        L.addLast("d");
        L.addLast("e");
         L.addFirst("f");
        L.removeFirst();
        L.removeFirst();
        L.removeFirst();
        L.addFirst("g");
        L.addLast("h");
        L.addFirst("i");
        ArrayDeque<String> L3 = new ArrayDeque<>(L);
        L.printDeque();
        L3.printDeque();


//        ArrayDeque<Integer> L2 = new ArrayDeque<>();
//        for (int i = 0; i < 20; i++) {
//            if (i % 2 == 0) {
//                L2.addLast(i);
//            } else {
//                L2.addFirst(i);
//            }
//        }
//        L2.removeFirst();
//        L2.removeFirst();
//        L2.removeFirst();
//        L2.removeFirst();
//        L2.removeFirst();
//        L2.removeFirst();
//        L2.removeFirst();
//        L2.removeFirst();
//        L2.removeFirst();
//        L2.removeFirst();
//        L2.removeFirst();
//        L2.removeFirst();
//        L2.removeLast();
//        L2.removeLast();
//        L2.printDeque();
    }

}