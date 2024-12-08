package com.cgvsu.protocurvefxapp;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseButton;

import java.util.ArrayList;

public class ProtoCurveController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Canvas canvas;
    private BezierCurve bezierCurve;

    @FXML
    private void initialize() {
        bezierCurve = new BezierCurve();
        setupCanvasDimensions();
        setupMouseEvents();
    }

    private void setupCanvasDimensions() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
    }

    private void setupMouseEvents() {
        canvas.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                handlePrimaryClick(event);
            }
        });
    }

    private void handlePrimaryClick(MouseEvent event) {
        Point2D clickPoint = new Point2D(event.getX(), event.getY());
        bezierCurve.addPoint(clickPoint);
        draw(canvas.getGraphicsContext2D(), bezierCurve.getControlPoints());
    }

    private static void draw(GraphicsContext gc, ArrayList<Point2D> controlPoints) {
        if (controlPoints.size() < 2) return;

        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        gc.beginPath();
        gc.moveTo(controlPoints.get(0).getX(), controlPoints.get(0).getY());

        for (float t = 0; t <= 1; t += 1e-3f) {
            Point2D bezierPoint = BezierCurve.calculateBezierPoint(t, new ArrayList<>(controlPoints));
            gc.lineTo(bezierPoint.getX(), bezierPoint.getY());
        }
        gc.stroke();
        //Очищаем предыдущие рисунки, рисует кривую на основе контрольных точек
        // и вызывает drawControlPoints, чтобы нарисовать контрольные точки

        drawControlPoints(gc, controlPoints);
    }

    private static void drawControlPoints(GraphicsContext gc, ArrayList<Point2D> controlPoints) {
        for (Point2D point : controlPoints) {
            gc.fillOval(point.getX() - 3, point.getY() - 3, 6, 6);
        }
    }
}