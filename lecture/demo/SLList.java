public class SLList {

    public static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    /* The first item (if it exists) is at sentinel.next .*/
    private IntNode sentinel;
    private int size;

    /** Empty list. */
    public SLList() {
        sentinel = new IntNode(21, null);
        size = 0;
    }

    public SLList(int x) {
        sentinel = new IntNode(21, null);
        sentinel.next = new IntNode(x, null);
        size = 1;
    }

    /** A constructor that takes in an array of integers as input. */
    public SLList(int[] inputArray) {
        sentinel = new IntNode(21, null);
        IntNode p = sentinel;
        for (int n : inputArray) {
            p.next = new IntNode(n, null);
            p = p.next;
            size += 1;
        }
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int n) {
        sentinel.next = new IntNode(n, sentinel.next);
        size += 1;
    }

    /** Retrieves the front item from the list. */
    public int getFirst() {
        return sentinel.next.item;
    }

    /** Caching. */
    public int size() {
        return size;
    }

    // /** Adds an item to the end of the list. (recursive approach) */
    // private void addLastRecur(int x, IntNode p) {
    //     if (p.next == null) {
    //         p.next = new IntNode(x, null);
    //     } else {
    //         addLast(x, p.next);
    //     }
    // }

    // public void addLastRecur(int x) {
    //     addLast(x, sentinel);
    //     size += 1;
    // }

    public void addLast(int x) {
        int i = 0;
        IntNode temp = sentinel;
        while (i < size) {
            temp = temp.next;
            i += 1;
        }
        temp.next = new IntNode(x, null);
    }

    /** Deletes the first element in list. */
    public void deleteFirst() {
        if (sentinel.next == null) {
            return;
        }
        sentinel.next = sentinel.next.next;
    }

    public static void main(String[] args){
        int[] s = new int[]{4, 5, 6};
        SLList L = new SLList(s);
        L.addLast(10);
    }
}
