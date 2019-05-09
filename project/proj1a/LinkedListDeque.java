public class LinkedListDeque<T> {

    public class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node(T x, Node prevNode, Node nextNode) {
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
            addLast((T) other.get(i));
        }
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        sentinel.next.prev = new Node(item, sentinel, sentinel.next);
        sentinel.next = sentinel.next.prev;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        sentinel.prev.next = new Node(item, sentinel.prev, sentinel);
        sentinel.prev = sentinel.prev.next;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

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
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            T firstItem = sentinel.next.item;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size -= 1;
            return firstItem;
        }
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null.
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            T lastItem = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size -= 1;
            return lastItem;
        }
    }

    /** Gets the item at the given index. */
    public T get(int index) {
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
    private T getRecursive(int index, Node p) {
        if (index >= size || index < 0) {
            return null;
        } else if (index == 0) {
            return p.item;
        } else {
            return getRecursive(index - 1, p.next);
        }
    }

    public T getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }



    public static void main(String[] args) {
        LinkedListDeque<Integer> L = new LinkedListDeque<>();
        L.addFirst(5);
        L.addLast(10);
        L.addLast(15);
        LinkedListDeque<Integer> L3 = new LinkedListDeque<>(L);
        System.out.println(L3.getRecursive(2));

//        LinkedListDeque<String> L2 = new LinkedListDeque<>();
//        System.out.println(L2.isEmpty());
//        L2.addFirst("very");
//        L2.addFirst("is");
//        L2.addLast("cool!");
//        L2.addFirst("Java");
//        LinkedListDeque<String> L4 = new LinkedListDeque<>(L2);
//        System.out.println(L2.isEmpty());
//        System.out.println(L2.size());
//        System.out.println(L2.get(0));
//        System.out.println(L2.get(3));
//        System.out.println(L2.get(4));
    }


}