package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> myPoints;

    public NaivePointSet(List<Point> points) {
        myPoints = new ArrayList<>();
        for (Point point : points) {
            myPoints.add(point);
        }
    }

    /**
     * Linear-time search to find the nearest point to a given coordinate.
     */
    @Override
    public Point nearest(double x, double y) {
        double nearestDist = Double.POSITIVE_INFINITY;
        Point result = myPoints.get(0);
        Point target = new Point(x, y);
        for (Point point : myPoints) {
            double dist = Point.distance(point, target);
            if (dist < nearestDist) {
                nearestDist = dist;
                result = point;
            }
        }
        return result;
    }

}
