package xplore.dnq.geo;

import java.util.Comparator;

public class Point2D implements Comparator<Point2D> {
    private int x, y;
    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * b.x-a.x
     * */
    public int diffX(Point2D that) {
        return that.x - this.x;
    }

    /**
     * b,y-a.y
     * */
    public int diffY(Point2D that) {
        return that.y - this.y;
    }

    public double eDistance(Point2D that) {
        int dx = diffX(that);
        int dy = diffY(that);
        return Math.sqrt(dx*dx+dy*dy);
    }

    @Override
    public int compare(Point2D a, Point2D b) {
        return 0;
    }

    @Override
    public String toString() {
        return String.format("P(%d,%d)", x, y);
    }

    public static final Comparator<Point2D> byX = new Comparator<Point2D>() {
        @Override
        public int compare(Point2D a, Point2D b) {
            return a.x - b.x;
        }
    };
    public static final Comparator<Point2D> byY = new Comparator<Point2D>() {
        @Override
        public int compare(Point2D a, Point2D b) {
            return a.y - b.y;
        }
    };


}
