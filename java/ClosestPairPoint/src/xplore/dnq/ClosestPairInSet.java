package xplore.dnq;

import xplore.dnq.geo.Point2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * https://github.com/kartikkukreja/blog-codes/blob/master/src/Closest%20Pair%20of%20Points.cpp
 * Codigo para programacao competitiva
 * */

public class ClosestPairInSet {


    public static class Point2DF implements Comparator<Point2DF> {
        private double x, y;
        public Point2DF(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public static final double EPS =-1E9;
        public boolean almostXEquals(Point2DF that) {
            return this.x - that.x < EPS;
        }
        public boolean almostYEquals(Point2DF that) {
            return this.y - that.y < EPS;
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
        // prioriedade para X
        public boolean lessYX(Point2DF that) {
            if(diffY(that) == 0) {
                return diffX(that) < 0;
            }
            return diffY(that) < 0;
        }
        public boolean lessXY(Point2DF that) {
            if(diffX(that) == 0) {
                return diffY(that) < 0;
            }
            return diffX(that) < 0;
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

    public static Point2DF [] closestPair(List<Point2DF> sortedX
            , List<Point2DF> sortedY, List<Point2DF> aux, int lo, int hi) {
        if(hi <= lo) {
            double MAX = Double.MAX_VALUE;
            return new Point2DF[] {
                 new Point2DF(0, 0)
                ,new Point2DF(MAX, MAX)
            };
        }
        int mid = (hi - lo) / 2 + lo;
        Point2DF midPoint = sortedX.get(mid);
        Point2DF [] ppl = closestPair(sortedX, sortedY, aux, lo, mid);
        Point2DF [] ppr = closestPair(sortedX, sortedY, aux, mid+1, hi);
        double minDistanceL = ppl[0].euclidianDistance(ppl[1]);
        double minDistanceR = ppr[0].euclidianDistance(ppr[1]);
        double min;
        Point2DF [] pa;
        if(minDistanceL < minDistanceR) {
            min = minDistanceL;
            pa = ppl;
        }
        else {
            min = minDistanceR;
            pa = ppr;
        }
        merge(sortedY, aux, lo, mid, hi);
        int m=0;
        for(int i=lo; i<=hi; i++) {
            Point2DF p = sortedY.get(i);
            if(Math.abs(p.diffX(midPoint) ) < min) {
                aux.remove(m);
                aux.add(m++, p);
            }
        }
        /**
         * Sabendo quais os pontos que estao a uma distancia minima
         * em relacao a coordenada X do ponto medio, verificar qual
         * par de pontos possui a menor distancia
         * */
        Point2DF [] pb = new Point2DF[2];
        int limit = aux.size();
        for(int i=0; i<limit-1; i++) {
            int j = i+1;
            Point2DF currentA = aux.get(i);
            Point2DF currentB = aux.get(j);
            double distance = currentA.euclidianDistance(currentB);
            double diffY = currentA.diffY(currentB);
            for (;  j<limit && diffY < min ; j++) {
                if(distance < min) {
                    min = distance;
                    pb[0] = currentA;
                    pb[1] = currentB;
                }
            }
        }
        if(pb[0] != null) {
            double da = pa[0].euclidianDistance(pa[1]);
            double db = pb[0].euclidianDistance(pb[1]);
            return da < db ? pa : pb;
        }
        return pa;
    }


    /**
     * Ordenacao pelos valores de Y do menor para o maior
     * */
    public static void merge(List<Point2DF> list, List<Point2DF> aux, int lo, int mi, int hi) {
        for(int idx=lo; idx<=hi; idx++) {
            aux.remove(idx);
            aux.add(idx, list.get(idx));
        }
        int i=lo, j=mi+1;
        for(int idx=lo; idx<=hi; idx++) {
            list.remove(idx);
            if(i>mi) {
                list.add(idx, aux.get(j++));
            }
            else if(j>hi) {
                list.add(idx, aux.get(i++));
            }
            else if(aux.get(j).lessY(aux.get(i))) {
                list.add(idx, aux.get(j++));
            }
            else {
                list.add(idx, aux.get(i++));
            }
        }
    }

    public static void run() {
        List<Point2DF> sx = new ArrayList<>();
        List<Point2DF> sy = new ArrayList<>();
        //  {{2, 3}, {12, 30}, {40, 50}, {5, 1}, {12, 10}, {3, 4}};
        sx.add(new Point2DF(2,3));
        sx.add(new Point2DF(12,30));
        sx.add(new Point2DF(40,50));
        sx.add(new Point2DF(5,1));
        sx.add(new Point2DF(12,10));
        sx.add(new Point2DF(3,4));
        Collections.sort(sx, Point2DF.SortByX);
        sy.addAll(sx);
        //Collections.sort(sy, Point2DF.SortByY);
        Point2DF [] closestPair = closestPair(sx, sy, new ArrayList<>(), 0, sx.size());
        double distance = closestPair[0].euclidianDistance(closestPair[1]);
        System.out.printf("%s %s %f\n", closestPair[0], closestPair[1], distance);
    }

    public static void main(String[] args) {
        run();
    }

}
