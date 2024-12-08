package com.cgvsu.protocurvefxapp;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import java.util.ArrayList;

class BezierCurve {
    private final ArrayList<Point2D> controlPoints;

    public BezierCurve() {
        this.controlPoints = new ArrayList<>();
    }

    public void addPoint(Point2D point) {
        controlPoints.add(point);
    }

    public ArrayList<Point2D> getControlPoints() {
        return controlPoints; //возвр список всех точек которые добавлены
    }

    public static Point2D calculateBezierPoint(double t, ArrayList<Point2D> points) {
        int n = points.size() - 1; //получаем колич точек, где n - кол-во отрезков
        double x = 0;
        double y = 0;
        for (int i = 0; i <= n; i++) {
            double binomialCoefficient = combination(n, i);
            double bernsteinPolynomial = binomialCoefficient * Math.pow(1 - t, n - i) * Math.pow(t, i);
            //полином берштейна
            x += bernsteinPolynomial * points.get(i).getX();
            y += bernsteinPolynomial * points.get(i).getY();
        }
        return new Point2D(x, y);
    }

    private static double combination(int n, int k) {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    private static double factorial(int num) {
        if (num == 0) {
            return 1;
        }
        double result = 1;
        for (int i = 1; i <= num; i++) {
            result *= i;
        }
        return result;
    }
}




    /*public static Point2D calculateBezierPoint(double t, ArrayList<Point2D> points) {
        if (points.size() == 1) {
            return points.get(0);
        }

        ArrayList<Point2D> newPoints = new ArrayList<>();//новый список,куда собираем новые точки,
        //вычесленные между парами
        for (int i = 0; i < points.size() - 1; i++) {
            newPoints.add(interpolate(points.get(i), points.get(i + 1), t));
        }

        return calculateBezierPoint(t, newPoints);
    }

    private static Point2D interpolate(Point2D p1, Point2D p2, double t) {
        double x = (1 - t) * p1.getX() + t * p2.getX();
        double y = (1 - t) * p1.getY() + t * p2.getY();
        return new Point2D(x, y);
    }

     */
