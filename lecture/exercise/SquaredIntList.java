public class SquaredIntList {
    public int first;
    public SquaredIntList rest;

    public SquaredIntList(int f, SquaredIntList r) {
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

    /** Returns the ith value in this list.*/
    public int get(int i) {
        if (i == 0) {
            return first;
        }
        return rest.get(i - 1);
    }

    /** Adds the item to the front of the list. Really ugly and inefficient... Notice that the reference cannot be changed(i.e., the list L always points to the same object), so we have to iteratively change each number in the list. */
    public void addFirst(int x) {
        // this.squareList();
        int temp1 = first;
        int temp2 = temp1;
        first = x;
        SquaredIntList p = this;

        while (p.rest != null) {
            p = p.rest;
            temp2 = p.first;
            p.first = temp1;
            temp1 = temp2;
        }
        p.rest = new SquaredIntList(temp1, null);
    }

    /** Adds the item to the end of the list. */
    public void addLast(int x) {
        // this.squareList();
        SquaredIntList p = this;
        while (p.rest != null) {
            p = p.rest;
        }
        p.rest = new SquaredIntList(x, null);
    }

    /** Square the list every time when adding a value. For example, upon the insertion of 5, the list below would transform from:
    1-->2 to 1-->1--2-->4-->5
    and if 7 was added to the latter list, it would become
    1-->1-->1-->1-->2-->4-->4-->16-->5-->25-->7
    */
    public void squareList() {
        if (this.rest == null) {
            this.rest = new SquaredIntList(this.first * this.first, null);
            return;
        }
        this.rest.squareList();
        SquaredIntList temp = this.rest;
        this.rest = new SquaredIntList(this.first * this.first, null);
        this.rest.rest = temp;
    }

    public static void main(String[] args) {
        // /* Test for addAdjacent */
        // SquaredIntList L = new SquaredIntList(4, null);
        // L = new SquaredIntList(2, L);
        // L = new SquaredIntList(2, L);
        // L = new SquaredIntList(8, L);
        // L.addAdjacent();

        /* Test for squareList */
        SquaredIntList L2 = new SquaredIntList(2, null);
        L2 = new SquaredIntList(1, L2);
        L2.addLast(5);
        L2.addLast(7);

    }
}
