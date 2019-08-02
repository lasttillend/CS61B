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
    private Map<Vertex, Double> distTo;
    private Map<Vertex, Vertex> edgeTo;
    private Vertex goal;
    private ArrayHeapMinPQ<Vertex> pq;
    private AStarGraph<Vertex> G;
    private boolean hasCalculatedSol = false;

    /**
     * Constructor which finds the solution, computing everything necessary
     * for all other methods to return their results in constant time.
     * Note that timeout passed in is in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        G = input;
        pq = new ArrayHeapMinPQ<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        solution = new LinkedList<>();
        goal = end;
        numStatesExplore = 0;
        pq.add(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);
        edgeTo.put(start, null);
        while (pq.size() != 0) {
            Vertex p = pq.removeSmallest();
            numStatesExplore += 1;
            timeSpent = sw.elapsedTime();
            if (timeSpent > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                return;
            }
            if (p.equals(goal)) {
                outcome = SolverOutcome.SOLVED;
                return;
            }
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(p);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                relax(e);
            }
        }
        outcome = SolverOutcome.UNSOLVABLE;
    }

    private void relax(WeightedEdge<Vertex> e) {
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
     */
    public SolverOutcome outcome() {
        return outcome;
    }

    /**
     * A list of vertices corresponding to a solution.
     * Should be empty if result was TIMEOUT or UNSOLVABLE.
     */
    public List<Vertex> solution() {
        if (outcome == SolverOutcome.UNSOLVABLE || outcome == SolverOutcome.TIMEOUT) {
            solution = new LinkedList<>();
        } else if (hasCalculatedSol) {
            return solution;
        } else {
            Vertex v = goal;
            while (v != null) {
                solution.addFirst(v);
                v = edgeTo.get(v);
            }
            hasCalculatedSol = true;
        }
        return solution;
    }

    /**
     * The total weight of the given solution, taking into account edge weights.
     * Should be 0 if result was TIMEOUT or UNSOLVABLE.
     */
    public double solutionWeight() {
        if (outcome == SolverOutcome.UNSOLVABLE || outcome == SolverOutcome.TIMEOUT) {
            solutionWeight = 0;
        } else {
            solutionWeight = distTo.get(goal);
        }
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


//package bearmaps.hw4;
//
//import bearmaps.proj2ab.ArrayHeapMinPQ;
//import edu.princeton.cs.algs4.Stopwatch;
//import java.util.LinkedList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
//    private SolverOutcome outcome;
//    private LinkedList<Vertex> solution = new LinkedList<>();
//    private double solutionWeight;
//    private int numStatesExplored;
//    private double explorationTime;
//    private final double INF = Double.POSITIVE_INFINITY;
//
//    /**
//     * Constructor which finds the solution, computing everything necessary
//     * for all other methods to return their results in constant time. Note
//     * that timeout passed in is in seconds.
//     *
//     * @param input
//     * @param start
//     * @param end
//     * @param timeout
//     */
//    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
//        ArrayHeapMinPQ<Vertex> PQ = new ArrayHeapMinPQ<>();
//        Map<Vertex, Double> distToStart = new HashMap<>();
//        Map<Vertex, Double> distToEnd = new HashMap<>();
//        Map<Vertex, Vertex> edgeTo = new HashMap<>();
//
//        Stopwatch sw = new Stopwatch();
//        distToStart.put(start, 0.0);
//        PQ.add(start, distToStart.get(start));
//
//        while (PQ.size() != 0) {
//
//            // Check whether the end is reached or not.
//            if (PQ.getSmallest().equals(end)) {
//                outcome = SolverOutcome.SOLVED;
//                solutionWeight = distToStart.get(end);
//
//                // Add vertex to solution.
//                Vertex curVertex = PQ.getSmallest();
//                solution.addFirst(curVertex);
//                while (!curVertex.equals(start)) {
//                    solution.addFirst(edgeTo.get(curVertex));
//                    curVertex = edgeTo.get(curVertex);
//                }
//
//                explorationTime = sw.elapsedTime();
//                return;
//            }
//
//            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(PQ.removeSmallest());
//            numStatesExplored += 1;
//
//            // Check time spent, if exceeds the timeout, return.
//            explorationTime = sw.elapsedTime();
//            if (explorationTime > timeout) {
//                outcome = SolverOutcome.TIMEOUT;
//                solution = new LinkedList<>();
//                solutionWeight = 0;
//                return;
//            }
//
//            for (WeightedEdge<Vertex> edge : neighborEdges) {
//                Vertex source = edge.from();
//                Vertex dest = edge.to();
//                double weight = edge.weight();
//
//                if (!distToStart.containsKey(dest)) {
//                    distToStart.put(dest, INF);
//                }
//
//                if (!distToEnd.containsKey(dest)) {
//                    distToEnd.put(dest, input.estimatedDistanceToGoal(dest, end));
//                }
//
//                // Relax all edges outgoing from source one at a time.
//                if (distToStart.get(source) + weight < distToStart.get(dest)) {
//                    distToStart.put(dest, distToStart.get(source) + weight);
//
//                    // Update the edge used by the dest vertex.
//                    edgeTo.put(dest, source);
//
//                    if (PQ.contains(dest)) {
//                        PQ.changePriority(dest, distToStart.get(dest) + distToEnd.get(dest));
//                    } else {
//                        PQ.add(dest, distToStart.get(dest) + distToEnd.get(dest));
//                    }
//                }
//            }
//        }
//        outcome = SolverOutcome.UNSOLVABLE;
//        solution = new LinkedList<>();
//        solutionWeight = 0;
//        explorationTime = sw.elapsedTime();
//    }
//
//    /**
//     * Returns one of SolverOutcome.SOLVED, SolverOutcome.TIMEOUT,
//     * or SolverOutcome.UNSOLVABLE. Should be SOLVED if the AStarSolver
//     * was able to complete all work in the time given. UNSOLVABLE if
//     * the priority queue became empty. TIMEOUT if the solver ran out
//     * of time. You should check to see if you have run out of time
//     * every time you dequeue.
//     *
//     * @return the outcome
//     */
//    public SolverOutcome outcome() {
//        return outcome;
//    }
//
//    /**
//     * A list of vertices corresponding to a solution. Should be empty
//     * if result was TIMEOUT or UNSOLVABLE.
//     *
//     * @return a list of vertices corresponding to a solution
//     */
//    public List<Vertex> solution() {
//        return solution;
//    }
//
//    /**
//     * The total weight of the given solution, taking into account edge
//     * weights. Should be 0 if result was TIMEOUT or UNSOLVABLE.
//     *
//     * @return the total weight of the given solution
//     */
//    public double solutionWeight() {
//        return solutionWeight;
//    }
//
//    /**
//     * The total number of priority queue dequeue operations.
//     *
//     * @return the total number of priority queue dequeue operations
//     */
//    public int numStatesExplored() {
//        return numStatesExplored;
//    }
//
//    /**
//     * The total time spent in seconds by the constructor.
//     *
//     * @return the total time spent in seconds by the constructor
//     */
//    public double explorationTime() {
//        return explorationTime;
//    }
//}