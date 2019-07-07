import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are <= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    private PriorityQueue<TimeEntity> timePQ;
    private int maxNumPassengers;

    private class TimeEntity {
        int time;
        boolean timeFlag;  // true: if is startTime; false: if is endTime
        int numOfPassengers;

        public TimeEntity(int time, boolean timeFlag, int numOfPassengers) {
            this.time = time;
            this.timeFlag = timeFlag;
            this.numOfPassengers = numOfPassengers;
        }

        public int getTime() {
            return time;
        }

        public boolean isStartTime() {
            return timeFlag;
        }

        public int getNumOfPassengers() {
            return numOfPassengers;
        }
    }


    public FlightSolver(ArrayList<Flight> flights) {
        Comparator<TimeEntity> cmptr = (t1, t2) -> t1.getTime() - t2.getTime();
        timePQ = new PriorityQueue<>(cmptr);
        maxNumPassengers = 0;
        for (Flight x : flights) {
            timePQ.add(new TimeEntity(x.startTime(), true, x.passengers()));
            timePQ.add(new TimeEntity(x.endTime(), false, x.passengers()));
        }

    }

    public int solve() {
        int passengers = 0;
        while (timePQ.size() != 0) {
            TimeEntity time = timePQ.remove();
            if (time.isStartTime()) {
                passengers += time.getNumOfPassengers();
            } else {
                passengers -= time.getNumOfPassengers();
            }
            if (passengers > maxNumPassengers) {
                maxNumPassengers = passengers;
            }
        }
        return maxNumPassengers;
    }
}
