import java.util.LinkedList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    List<Bear> bearsMatched;
    List<Bed> bedsMatched;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        List<Pair<Bear, Bed>> matched = new LinkedList<>();  // store matching result
        bearsMatched = new LinkedList<>();
        bedsMatched = new LinkedList<>();
        match(bears, beds, matched);
        for (Pair pair : matched) {
            bearsMatched.add((Bear) pair.first());
            bedsMatched.add((Bed) pair.second());
        }
    }

    private void partitionBeds(List<Bed> unsortedBeds, Bear pivotBear,
                              List<Bed> less, List<Bed> equal, List<Bed> greater) {
        for (Bed bed : unsortedBeds) {
            if (bed.compareTo(pivotBear) < 0) {
                less.add(bed);
            } else if (bed.compareTo(pivotBear) > 0) {
                greater.add(bed);
            } else {
                equal.add(bed);
            }
        }
    }

    private void partitionBears(List<Bear> unsortedBears, Bed pivotBed,
                                List<Bear> less, List<Bear> equal, List<Bear> greater) {
        for (Bear bear : unsortedBears) {
            if (bear.compareTo(pivotBed) < 0) {
                less.add(bear);
            } else if (bear.compareTo(pivotBed) > 0) {
                greater.add(bear);
            } else {
                equal.add(bear);
            }
        }
    }

    /**
     * The idea is to use the first bear to classify beds according to its size
     * then use its matched bed and let other bears come and compare.
     * After this process, we can classify bears and beds into two groups,
     * respectively(i.e., less than the first bear and greater than the first bear).
     * Finally, recursively call on smaller and greater groups.
     */
    private void match(List<Bear> bears, List<Bed> beds, List<Pair<Bear, Bed>> matched) {
        if (bears.size() <= 0 || beds.size() <= 0) {
            return;
        }

        Bear pivotBear = bears.remove(0);
        List<Bed> lessBed = new LinkedList<>();
        List<Bed> equalBed = new LinkedList<>();
        List<Bed> greaterBed = new LinkedList<>();

        List<Bear> lessBear = new LinkedList<>();
        List<Bear> equalBear = new LinkedList<>();
        List<Bear> greaterBear = new LinkedList<>();

        // use first bear to partition beds and find its matched bed
        partitionBeds(beds, pivotBear, lessBed, equalBed, greaterBed);
        Bed pivotBed = equalBed.get(0);
        matched.add(new Pair(pivotBear, pivotBed));
        // use the matched bed to partition bears
        partitionBears(bears, pivotBed, lessBear, equalBear, greaterBear);

        // we've matched the first bear and classified bears and beds according to its size
        match(lessBear, lessBed, matched);
        match(greaterBear, greaterBed, matched);
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return bearsMatched;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return bedsMatched;
    }
}
