public class OffByN implements CharacterComparator {

    private int offBy = 0;
    /** Constructors */
    public OffByN(int N) {
        offBy = N;
    }

    /** Returns true if x and y are off by N. */
    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return (diff == offBy || diff == -offBy);
    }
}


