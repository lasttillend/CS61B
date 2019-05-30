package creatures;

import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * An implementation of a fierce blue-colored predator
 */
public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * Creates clorus with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /**
     * Should return a color with red = 34, green = 0, blue = 231.
     */
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    /**
     * If a Clorus attacks another creature, it should gain that creature's energy.
     */
    public void attack(Creature c) {
        energy += c.energy();
    }

    /**
     * Cloruses should lose 0.03 units of energy on a MOVE action.
     */
    public void move() {
        energy -= 0.03;
    }

    /**
     * Cloruses should lose 0.01 units of energy on a STAY action.
     */
    public void stay() {
        energy -= 0.01;
    }

    /**
     * Cloruses and their offspring each get 50% of the energy, with none
     * lost to the process. Returns a baby Clorus.
     */
    public Clorus replicate() {
        double babyEnergy = energy * 0.5;
        energy *= 0.5;
        return new Clorus(babyEnergy);
    }

    /**
     * Cloruses take exactly the following actions based on NEIGHBORS:
     * 1. If there are no empty squares, the Clorus will stay (even if
     * there are Plips nearby they could attack since plip squares do not
     * count as empty squares).
     * 2. Otherwise, if any Plips are seen, the Clorus will attack one of
     * them randomly.
     * 3. Otherwise, if the Clorus has energy greater than or equal to one,
     * it will replicate to a random empty square.
     * 4. Otherwise, the Clorus will move to a random empty square.
     * <p>
     * Returns an object of type Action.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipsNearby = new ArrayDeque<>();
        for (Direction dir : neighbors.keySet()) {
            if (neighbors.get(dir).name().equals("empty")) {
                emptyNeighbors.add(dir);
            } else if (neighbors.get(dir).name().equals("plip")) {
                plipsNearby.add(dir);
            }
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if (plipsNearby.size() > 0) {
            Direction randomPlipDir = HugLifeUtils.randomEntry(plipsNearby);
            return new Action(Action.ActionType.ATTACK, randomPlipDir);
        }

        // Rule 3
        if (energy >= 1) {
            Direction randomReplicateDir = HugLifeUtils.randomEntry(emptyNeighbors);
            return new Action(Action.ActionType.REPLICATE, randomReplicateDir);
        }

        // Rule 4
        Direction randomMoveDir = HugLifeUtils.randomEntry(emptyNeighbors);
        return new Action(Action.ActionType.MOVE, randomMoveDir);
    }

}
