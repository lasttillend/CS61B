/** Deque Interface */

public interface Deque<Item> {

    /** Adds an item of type T to the front of the deque. */
    void addFirst(Item item);

    /** Adds an item of type T to the back of the deque. */
    void addLast(Item item);

    /** Returns true if deque is empty. */
    default public boolean isEmpty() {
        return size() == 0;
    }

    /** Returns the number of items in the deque. */
    int size();

    /** Prints the items in the deque. */
    void printDeque();

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null.
     */
    Item removeFirst();

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null.
     */
    Item removeLast();

    /** Gets the item at the given index. */
    Item get(int index);

}