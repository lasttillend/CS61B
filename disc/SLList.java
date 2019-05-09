public class SLList {

    private class IntNode {
        public int item;
        public IntNode next;
        public IntNode(int item, IntNode next) {
            this.item = item;
            this.next = next;
        }
    }

    private IntNode first;

    public SLList(int x) {
        first = new IntNode(x, null);
    }

    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    /** Inserts item at the given position. If position is after the end of the list, insert the new node at the end. For example:
    SLList 5-->6-->2, insert(10, 1) results in 5-->10-->6-->2
    SLList 5-->6-->2, insert(10, 7) results in 5-->6-->2-->10.
     */
    public void insert(int item, int position) {
        if (first == null || position == 0) {
            addFirst(item);
            return;
        }
        IntNode curr = first;
        while (curr.next != null && position > 1) {
            position -= 1;
            curr = curr.next;
        }
        curr.next = new IntNode(item, curr.next);
    }

    /** Reverses the elements.(not using new) */
    public void reverseRecur() {
        first = reverseHelper(first);
    }

    private IntNode reverseHelper(IntNode lst) {
        if (lst == null || lst.next == null) {
            return lst;
        } else {
            IntNode endOfReversed = lst.next;
            IntNode reversed = reverseHelper(lst.next);
            endOfReversed.next = lst;
            lst.next = null;
            return reversed;
        }
    }

    /** Reverses the elements.(not using new) */
    public void reverseIter() {
        if (first == null || first.next == null) {
            return;
        }
        IntNode ptr = first.next;
        first.next = null;

        while (ptr != null) {
            IntNode temp = ptr.next;
            ptr.next = first;
            first = ptr;
            ptr = temp;
        }
    }

    public static void main(String[] args) {
        SLList L = new SLList(4);
        L.addFirst(3);
        L.addFirst(2);
        L.addFirst(1);
        L.reverseRecur();
    }
}
