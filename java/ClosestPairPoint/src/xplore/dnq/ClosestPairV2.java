package xplore.dnq;


import xplore.dnq.geo.Point2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClosestPairV2 {


    public static Point2D[] bruteForce(List<Point2D> points, int limit) {
        Point2D [] pair = new Point2D [2];
        pair[0] = points.get(0);
        pair[1] = points.get(1);
        double minDistance = Double.MAX_VALUE;
        for(int i=0; i<limit; i++) {
            for (int j=i+1; j<limit; j++) {
                Point2D a = points.get(i);
                Point2D b = points.get(j);
                double distance = a.eDistance(b);
                if(distance  < minDistance) {
                    pair[0] = a;
                    pair[1] = b;
                    minDistance = distance;
                }
            }
        }
        return pair;
    }

    public static Point2D [] solver(List<Point2D> sortedX, List<Point2D> sortedY, int size) {
        if(size < 4) {
            return bruteForce(sortedX, size);
        }
        int mid = size / 2;
        Point2D midPoint = sortedX.get(mid);
        List<Point2D> setLeft = new ArrayList<>();
        List<Point2D> setRight = new ArrayList<>();
        for(int i=0; i<size; i++) {
            Point2D spy = sortedY.get(i);
            if(midPoint.diffX(spy) <= 0)
                setLeft.add(spy);
            else
                setRight.add(spy);
        }

        Point2D [] ppl = solver(sortedX, setLeft, mid);
        List<Point2D> newPx = new ArrayList<>();
        for(int idx=mid; idx<sortedX.size(); idx++)
            newPx.add(sortedX.get(idx));

        Point2D [] ppr = solver(newPx, setRight, size-mid);
        double minDistanceLeft = ppl[0].eDistance(ppl[1]);
        double minDistanceRight = ppr[0].eDistance(ppr[1]);
        double min;
        Point2D [] pa = new Point2D[2];
        if(minDistanceLeft < minDistanceRight) {
            min = minDistanceLeft;
            pa[0] = ppl[0];
            pa[1] = ppl[1];
        }
        else {
            min = minDistanceRight;
            pa[0] = ppr[0];
            pa[1] = ppr[1];
        }

        /**
         * Escolher os pontos cuja a distancia na coordenada X entre eles e o ponto
         * que divide a lista de pontos em 2 lista seja menor do que a menor distancia
         * entre todos os pontos
         * */
        List<Point2D> strip = new ArrayList<>();
        for(int idx=0; idx<size; idx++) {
            Point2D p = sortedY.get(idx);
            if(Math.abs(p.diffX(midPoint)) < min) {
                strip.add(p);
            }
        }
        Point2D [] pb = minDistanceChoosedPoints(strip, min);
        if(pb[0] != null) {
            double mpa = pa[0].eDistance(pa[1]);
            double mpb = pb[0].eDistance(pb[1]);
            return mpa < mpb ? pa : pb;
        }
        return pa;
    }

    /**
     * Qual a menor distancia entre os pontos que se encontram na regiao
     * onde a distancia entre ponto escolhido e o ponto exatamente no meio
     * do array ordenada é a minima possivel ?
     *
     * Esse metodo recebe uma lista de pontos cuja diferença entre o valor X de cada ponto
     * e o ponto mediano da lista é menor do que a menor distancia encontrada entre o conjutno
     * de pontos.
     *
     * Tendo esse conjunto de pontos que estao a no maximo a uma distancia X do ponto mediano
     * vamos verificar quais deles formam o par com a menor distancia
     *
     * */
    public static Point2D [] minDistanceChoosedPoints(List<Point2D> sortedY, double minDistance) {
        Point2D [] pair = new Point2D[2];
        for(int i=0; i<sortedY.size()-1; i++) {
            int j = i + 1;
            Point2D a = sortedY.get(i);
            Point2D b = sortedY.get(j);
            int diffY = a.diffY(b);
            for (; j < sortedY.size() && diffY < minDistance ; j++) {
                double distance = a.eDistance(b);
                if(distance < minDistance) {
                    minDistance = distance;
                    pair[0] = a;
                    pair[1] = b;
                }
            }
        }
        return pair;
    }

    public static void run() {
        List<Point2D> sx = new ArrayList<>();
        List<Point2D> sy = new ArrayList<>();
        //  {{2, 3}, {12, 30}, {40, 50}, {5, 1}, {12, 10}, {3, 4}};
        sx.add(new Point2D(2,3));
        sx.add(new Point2D(12,30));
        sx.add(new Point2D(40,50));
        sx.add(new Point2D(5,1));
        sx.add(new Point2D(12,10));
        sx.add(new Point2D(3,4));
        Collections.sort(sx, Point2D.byX);
        sy.addAll(sx);
        Collections.sort(sy, Point2D.byY);
        Point2D [] closestPair = solver(sx, sy, sx.size());
        double distance = closestPair[0].eDistance(closestPair[1]);
        System.out.printf("%s %s %f\n", closestPair[0], closestPair[1], distance);
    }


    public static void main(String[] args) {
        run();
    }

}
