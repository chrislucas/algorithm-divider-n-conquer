package xplore.dnq;


import java.util.Comparator;
import java.util.List;

/**
 *
 * */
public class ClosestPairPointCompetitive {

    public static class Point2DF implements Comparator<Point2DF> {
        private double x, y;
        public Point2DF(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public double diffX(Point2DF that) { return  this.x - that.x; }
        public double diffY(Point2DF that) { return  this.y - that.y; }
        public double euclidianDistance(Point2DF that) { return Math.sqrt(diffX(that)*diffX(that)+diffY(that)*diffY(that)); }

        @Override
        public String toString() {
            return String.format("P(%f,%f)", x, y);
        }

        public boolean lessX(Point2DF that) {
            return diffX(that) < 0;
        }

        public boolean lessY(Point2DF that) {
            return diffY(that) < 0;
        }

        @Override
        public int compare(Point2DF a, Point2DF b) {
            return (int)(a.x - b.x);
        }

        public static final Comparator<Point2DF> SortByX = new Comparator<Point2DF>() {
            @Override
            public int compare(Point2DF a, Point2DF b) {
                return (int) (a.x - b.x);
            }
        };

        public static final Comparator<Point2DF> SortByY = new Comparator<Point2DF>() {
            @Override
            public int compare(Point2DF a, Point2DF b) {
                return (int) (a.x - b.x);
            }
        };
    }


    public static Point2DF[] bruteForce(List<Point2DF> points, int limit) {
        Point2DF [] pair = new Point2DF [2];
        pair[0] = points.get(0);
        pair[1] = points.get(1);
        double minDistance = Double.MAX_VALUE;
        for(int i=0; i<limit; i++) {
            for (int j=i+1; j<limit; j++) {
                Point2DF a = points.get(i);
                Point2DF b = points.get(j);
                double distance = a.euclidianDistance(b);
                if(distance  < minDistance) {
                    pair[0] = a;
                    pair[1] = b;
                    minDistance = distance;
                }
            }
        }
        return pair;
    }

    public void run() {

    }


    public static void main(String[] args) {

    }
}
