package bearmaps.proj2ab;
import java.util.List;

public class KDTree implements PointSet {
    private static final boolean SPLIT_X = true;    // split x-axis
    private Node root;

    private class Node {
        private Point p;
        private boolean split;        // true: split x axis; false: split y axis
        private Node left;            // also refers to "up" node
        private Node right;           // also refers to "down" node

        public Node(Point p, boolean split) {
            this.p = p;
            this.split = split;
        }

        public Point getPoint() {
            return p;
        }

        public boolean getSplit() {
            return split;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public void setLeft(Node x) {
            left = x;
        }

        public void setRight(Node x) {
            right = x;
        }

    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(root, p, SPLIT_X);
        }
    }

    private Node add(Node x, Point p, boolean split) {
        if (x == null) {
            return new Node(p, split);
        }
        // duplicated points are not added
        if (p.equals(x.getPoint())) {
            return x;
        }

        int cmp = pointComparator(p, x.getPoint(), split);
        if (cmp < 0) {
            x.setLeft(add(x.getLeft(), p, !x.getSplit()));
        } else {
            x.setRight(add(x.getRight(), p, !x.getSplit()));
        }
        return x;
    }

    private int pointComparator(Point p, Point q, boolean split) {
        int diff;
        if (split == SPLIT_X) {
            diff = Double.compare(p.getX(), q.getX());
        } else {
            diff = Double.compare(p.getY(), q.getY());
        }
        return diff;
    }

    @Override
    public Point nearest(double x, double y) {
        Node nearestNode = nearest(root, new Point(x, y), root);
        return nearestNode.getPoint();
    }

    private Node nearest(Node x, Point goal, Node best) {
        if (x == null) {
            return best;
        }
        if (Point.distance(x.getPoint(), goal) < Point.distance(best.getPoint(), goal)) {
            best = x;
        }
        Node goodSide, badSide;
        if (pointComparator(goal, x.getPoint(), x.getSplit()) < 0) {
            goodSide = x.getLeft();
            badSide = x.getRight();
        } else {
            goodSide = x.getRight();
            badSide = x.getLeft();
        }
        best = nearest(goodSide, goal, best);
        // search if bad side could still have something useful, otherwise prune
        if (worthToSearchBadSide(x, goal, best)) {
            best = nearest(badSide, goal, best);
        }
        return best;
    }

    private boolean worthToSearchBadSide(Node x, Point goal, Node best) {
        Point temp;  // best possible point on the bad sad
        if (x.getSplit() == SPLIT_X) {
            temp = new Point(x.getPoint().getX(), goal.getY());
        } else {
            temp = new Point(goal.getX(), x.getPoint().getY());
        }
        return Point.distance(temp, goal) < Point.distance(best.getPoint(), goal);
    }

}
