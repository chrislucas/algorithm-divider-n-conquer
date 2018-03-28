package xplore.dnq.geo;

import java.util.Comparator;

public class Point2Df implements Comparator<Point2Df> {
    private double x, y;
    public Point2Df(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compare(Point2Df o1, Point2Df o2) {
        return 0;
    }
}
