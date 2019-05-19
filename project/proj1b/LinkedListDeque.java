public class LinkedListDeque<Item> implements Deque<Item> {

    public class Node {
        public Item item;
        public Node prev;
        public Node next;

        public Node(Item x, Node prevNode, Node nextNode) {
            item = x;
            prev = prevNode;
            next = nextNode;
        }
    }

    private Node sentinel;
    private int size;

    /** Empty linked list deque constructor. */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** Creates a deep copy, i.e., an entirely new LinkedListDeque
     *  with the exact same items as other.
     */
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
        for (int i = 0; i < other.size(); i++) {
            addLast((Item) other.get(i));
        }
    }

    @Override
    /** Adds an item of type T to the front of the deque. */
    public void addFirst(Item item) {
        sentinel.next.prev = new Node(item, sentinel, sentinel.next);
        sentinel.next = sentinel.next.prev;
        size += 1;
    }

    @Override
    /** Adds an item of type T to the back of the deque. */
    public void addLast(Item item) {
        sentinel.prev.next = new Node(item, sentinel.prev, sentinel);
        sentinel.prev = sentinel.prev.next;
        size += 1;
    }

    @Override
    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    @Override
    /** Prints the items in the deque from first to last. */
    public void printDeque() {
        Node temp = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(temp.item + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null.
     */
    @Override
    public Item removeFirst() {
        if (size == 0) {
            return null;
        } else {
            Item firstItem = sentinel.next.item;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size -= 1;
            return firstItem;
        }
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null.
     */
    @Override
    public Item removeLast() {
        if (size == 0) {
            return null;
        } else {
            Item lastItem = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size -= 1;
            return lastItem;
        }
    }

    /** Gets the item at the given index. */
    @Override
    public Item get(int index) {
        // Index out of range.
        if (index >= size || index < 0) {
            return null;
        } else {
            Node temp = sentinel.next;
            while (index > 0) {
                temp = temp.next;
                index -= 1;
            }
            return temp.item;
        }
    }

    /** Gets the item at the given index. (recursive approach) */
    private Item getRecursive(int index, Node p) {
        if (index >= size || index < 0) {
            return null;
        } else if (index == 0) {
            return p.item;
        } else {
            return getRecursive(index - 1, p.next);
        }
    }

    public Item getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }
}