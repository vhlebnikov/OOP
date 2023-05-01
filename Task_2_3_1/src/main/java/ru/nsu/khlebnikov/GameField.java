package ru.nsu.khlebnikov;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameField extends Pane {
    private static int WIDTH_CELLS_NUMBER;
    private static int HEIGHT_CELLS_NUMBER;

    private static Canvas canvas;

    public GameField(int width, int height, double windowWidth, double windowHeight) {
        WIDTH_CELLS_NUMBER = width;
        HEIGHT_CELLS_NUMBER = height;
        canvas = new Canvas(windowWidth, windowHeight);
        this.getChildren().add(canvas);
    }

    public void draw(double windowWidth, double windowHeight) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double cellWidth = windowWidth / WIDTH_CELLS_NUMBER;
        double cellHeight = windowHeight / HEIGHT_CELLS_NUMBER;

        canvas.setWidth(windowWidth);
        canvas.setHeight(windowHeight);

        for (int h = 0; h < HEIGHT_CELLS_NUMBER; h++) {
            for (int w = 0; w < WIDTH_CELLS_NUMBER; w++) {
                if ((h + w) % 2 == 0) {
                    gc.setFill(Color.web("#AAD751"));
                } else {
                    gc.setFill(Color.web("#A2D149"));
                }

                gc.fillRect(w * cellWidth, h * cellHeight, cellWidth, cellHeight);
            }
        }
    }
}
