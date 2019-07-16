package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class KDTreeTest {

    @Test
    public void basicTest() {
        Point p1 = new Point(5, 6);
        Point p2 = new Point(1, 5);
        Point p3 = new Point(7, 3);
        Point p4 = new Point(2, 2);
        Point p5 = new Point(4, 9);
        Point p6 = new Point(9, 1);
        Point p7 = new Point(8, 7);

        KDTree kdt = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
    }

    @Test
    public void testConstructor() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);  // duplicates are not added
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kdt = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
    }

    @Test
    public void randomizedTest() {
        List<Point> points = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < 100000; i++) {
            Point randPoint = new Point(rnd.nextDouble(), rnd.nextDouble());
            points.add(randPoint);
        }
        List<Point> query = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Point randQueryPoint = new Point(rnd.nextDouble(), rnd.nextDouble());
            query.add(randQueryPoint);
        }

        KDTree kdt = new KDTree(points);
        NaivePointSet nps = new NaivePointSet(points);
        for (Point p : query) {
            Point p1 = kdt.nearest(p.getX(), p.getY());
            Point p2 = nps.nearest(p.getX(), p.getY());
//            System.out.println("KDTree: " + p1);
//            System.out.println("NaivePointSet: " + p1);
            assertTrue(p1.equals(p2));
        }



        Stopwatch timer1 = new Stopwatch();
        for (Point p : query) {
            kdt.nearest(p.getX(), p.getY());
        }
        System.out.println("KDTree total time elapsed: " + timer1.elapsedTime() +  " seconds.");

        Stopwatch timer2 = new Stopwatch();
        for (Point p : query) {
            nps.nearest(p.getX(), p.getY());
        }
        System.out.println("NaivePointSet total time elapsed: " + timer2.elapsedTime() +  " seconds.");
    }
}
