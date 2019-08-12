package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private LinkedList<Vertex> solution;
    private double timeSpent;
    private int numStatesExplore;

    /**
     * Constructor which finds the solution, computing everything necessary
     * for all other methods to return their results in constant time.
     * Note that timeout passed in is in seconds.
     *
     * @param input
     * @param start
     * @param end
     * @param timeout
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        ArrayHeapMinPQ<Vertex> pq = new ArrayHeapMinPQ<>();
        Map<Vertex, Double> distTo = new HashMap<>();  // distance to the start vertex
        Map<Vertex, Vertex> edgeTo = new HashMap<>();  // edge to the parent vertex

        Stopwatch sw = new Stopwatch();
        numStatesExplore = 0;
        distTo.put(start, 0.0);
        pq.add(start, distTo.get(start) + input.estimatedDistanceToGoal(start, end));
        edgeTo.put(start, null);

        while (pq.size() != 0) {
            Vertex p = pq.removeSmallest();
            numStatesExplore += 1;
            timeSpent = sw.elapsedTime();
            // check to see if we have ran out of time
            if (timeSpent > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                solution = new LinkedList<>();
                solutionWeight = 0;
                return;
            }
            // check to see if we have reached the goal
            if (p.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                solution = new LinkedList<>();
                Vertex v = end;
                while (v != null) {
                    solution.addFirst(v);
                    v = edgeTo.get(v);
                }
                solutionWeight = distTo.get(end);
                return;
            }
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(p);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                relax(e, distTo, edgeTo, input, pq, end);
            }
        }
        // the problem is UNSOLVABLE
        outcome = SolverOutcome.UNSOLVABLE;
        solution = new LinkedList<>();
        solutionWeight = 0;

    }

    private void relax(WeightedEdge<Vertex> e, Map<Vertex, Double> distTo, Map<Vertex, Vertex> edgeTo, AStarGraph<Vertex> G,
                       ArrayHeapMinPQ<Vertex> pq, Vertex goal) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();
        if (!distTo.containsKey(q)) {
            distTo.put(q, Double.POSITIVE_INFINITY);
        }
        if (distTo.get(p) + w < distTo.get(q)) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            if (pq.contains(q)) {
                pq.changePriority(q, distTo.get(q) + G.estimatedDistanceToGoal(q, goal));
            } else {
                pq.add(q, distTo.get(q) + G.estimatedDistanceToGoal(q, goal));
            }
        }
    }


    /**
     * Returns one of SolverOutcome.SOLVED, SolverOutcome.TIMEOUT, or SolverOutcome.UNSOLVABLE.
     * Should be SOLVED if the AStarSolver was able to complete all work in the time given.
     * UNSOLVABLE if the priority queue became empty. TIMEOUT if the solver ran out of time.
     * You should check to see if you have run out of time every time you dequeue.
     *
     * @return the outcome
     */
    public SolverOutcome outcome() {
        return outcome;
    }

    /**
     * A list of vertices corresponding to a solution.
     * Should be empty if result was TIMEOUT or UNSOLVABLE.
     *
     * @return a list of vertices corresponding to a solution
     */
    public List<Vertex> solution() {
        return solution;
    }

    /**
     * The total weight of the given solution, taking into account edge weights.
     * Should be 0 if result was TIMEOUT or UNSOLVABLE.
     *
     * @return the total weight of the solution
     */
    public double solutionWeight() {
        return solutionWeight;
    }

    /**
     * The total number of priority queue dequeue operations.
     */
    public int numStatesExplored() {
        return numStatesExplore;
    }

    /**
     *  The total time spent in seconds by the constructor.
     */
    public double explorationTime() {
        return timeSpent;
    }
}
