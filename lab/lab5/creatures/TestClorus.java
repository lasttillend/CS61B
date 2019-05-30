package creatures;

import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

import org.junit.Test;

import java.awt.Color;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(3);
        assertEquals(3, c.energy(), 0.001);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(2.97, c.energy(), 0.001);
        c.stay();
        assertEquals(2.96, c.energy(), 0.001);
    }

    @Test
    public void testAttack() {
        Plip p = new Plip(2);
        Clorus c = new Clorus(1);
        c.attack(p);
        assertEquals(3, 3, 0.001);
    }

    @Test
    public void testReplicate() {
        Clorus c = new Clorus(1);
        Clorus baby = c.replicate();
        assertEquals(0.5, c.energy(), 0.001);
        assertEquals(0.5, baby.energy(), 0.001);
    }

    @Test
    public void testChoose() {
        // No empty adjacent spaces; stay
        Clorus c = new Clorus(1.1);
        HashMap<Direction, Occupant> surrounded = new HashMap<>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Plip(2));
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        // There are empty squares nearby and plips are seen
        c = new Clorus(1.1);
        HashMap<Direction, Occupant> onePlip = new HashMap<>();
        onePlip.put(Direction.TOP, new Empty());
        onePlip.put(Direction.BOTTOM, new Empty());
        onePlip.put(Direction.LEFT, new Impassible());
        onePlip.put(Direction.RIGHT, new Plip(1.3));

        actual = c.chooseAction(onePlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.RIGHT);
        assertEquals(expected, actual);

        // Plips are not seen and energy >= 1; replicate
        c = new Clorus(1.1);
        HashMap<Direction, Occupant> noPlips = new HashMap<>();
        noPlips.put(Direction.TOP, new Empty());
        noPlips.put(Direction.BOTTOM, new Impassible());
        noPlips.put(Direction.LEFT, new Impassible());
        noPlips.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(noPlips);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);
        assertEquals(expected, actual);

        // Energy < 1; move
        c = new Clorus(0.9);

        actual = c.chooseAction(noPlips);
        expected = new Action(Action.ActionType.MOVE, Direction.TOP);
        assertEquals(expected, actual);

        // Energy < 1; move
        c = new Clorus(0.9);
        HashMap<Direction, Occupant> allEmpty = new HashMap<>();
        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());

        Action unexpected = new Action(Action.ActionType.STAY);
        assertNotEquals(unexpected, actual);
    }
}
