public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Return the size of the list using recursion! */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + rest.size();
    }

    /** Return the size of the list using no recursion! */
    public int iterativeSize() {
        IntList p = this;
        int totalSize = 0;
        while (p != null) {
            totalSize += 1;
            p = p.rest;
        }
        return totalSize;
    }

    /** Returns the ith value in this list.*/
    public int get(int i) {
        if (i == 0) {
            return first;
        }
        return rest.get(i - 1);
    }

    /** Adds the item to the front of the list. Really ugly and inefficient... Notice that the reference cannot be changed(i.e., the list L always points to the same object), so we have to iteratively change each number in the list. */
    public void addFirst(int x) {
        int temp1 = first;
        int temp2 = temp1;
        first = x;
        IntList p = this;

        while (p.rest != null) {
            p = p.rest;
            temp2 = p.first;
            p.first = temp1;
            temp1 = temp2;
        }
        p.rest = new IntList(temp1, null);
    }

    /** If 2 numbers in a row are the same, we add them together and make one larger node. For example:
    1-->1-->2-->3 becomes 2-->2-->3 which becomes 4-->3.
    */
    public void addAdjacent() {
        if (this.rest == null) {
            return;
        }
        this.rest.addAdjacent();
        if (this.rest.first == this.first) {
            this.first *= 2;
            this.rest = this.rest.rest;
            this.addAdjacent();
        }
    }

    public static void main(String[] args) {
        /* Test for addAdjacent */
        IntList L = new IntList(4, null);
        L = new IntList(2, L);
        L = new IntList(2, L);
        L = new IntList(8, L);
        L.addAdjacent();
    }
}
