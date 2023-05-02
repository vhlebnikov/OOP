package ru.nsu.khlebnikov;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Objects;

public class GameField extends Pane {
    private static int WIDTH_CELLS_NUMBER;
    private static int HEIGHT_CELLS_NUMBER;
    private static Canvas canvas;
    private static GraphicsContext graphicsContext;

    public GameField(int width, int height, double windowWidth, double windowHeight) {
        WIDTH_CELLS_NUMBER = width;
        HEIGHT_CELLS_NUMBER = height;
        canvas = new Canvas(windowWidth, windowHeight);
        this.getChildren().add(canvas);
    }

    public void drawField(double windowWidth, double windowHeight) {
        graphicsContext = canvas.getGraphicsContext2D();

        double cellWidth = windowWidth / WIDTH_CELLS_NUMBER;
        double cellHeight = windowHeight / HEIGHT_CELLS_NUMBER;

        canvas.setWidth(windowWidth);
        canvas.setHeight(windowHeight);

        for (int h = 0; h < HEIGHT_CELLS_NUMBER; h++) {
            for (int w = 0; w < WIDTH_CELLS_NUMBER; w++) {
                if ((h + w) % 2 == 0) {
                    graphicsContext.setFill(Color.web("#AAD751"));
                } else {
                    graphicsContext.setFill(Color.web("#A2D149"));
                }

                graphicsContext.fillRect(w * cellWidth, h * cellHeight, cellWidth, cellHeight);
            }
        }
    }

    public void drawWalls(double windowWidth, double windowHeight, ArrayList<Point> wallsCoordinates) {
        double cellWidth = windowWidth / WIDTH_CELLS_NUMBER;
        double cellHeight = windowHeight / HEIGHT_CELLS_NUMBER;

        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/brickwall.png")));

        for (Point point : wallsCoordinates) {
            graphicsContext.drawImage(image, point.x * cellWidth, point.y * cellHeight, cellWidth, cellHeight);
        }
    }

    public void drawSnake(double windowWidth, double windowHeight, Snake snake) {
        double cellWidth = windowWidth / WIDTH_CELLS_NUMBER;
        double cellHeight = windowHeight / HEIGHT_CELLS_NUMBER;

        Image head = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_head.png")));
        Image body = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_body.png")));
        Image tail = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_tail.png")));
        Image rotation = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_rotation.png")));

        Point snakeHead = snake.getHead();

        ImageView headImageView = new ImageView(head);
        switch (snake.getDirection()) {
            case UP -> {
                headImageView.setRotate(180); // 0 - down, 90 - left, 180 - up, 270 - right
            }
            case DOWN -> {
                headImageView.setRotate(0); // 0 - down, 90 - left, 180 - up, 270 - right
            }
            case LEFT -> {
                headImageView.setRotate(90); // 0 - down, 90 - left, 180 - up, 270 - right
            }
            case RIGHT -> {
                headImageView.setRotate(270); // 0 - down, 90 - left, 180 - up, 270 - right
            }
        }

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedHead = headImageView.snapshot(params, null);

        graphicsContext.drawImage(rotatedHead, snakeHead.x * cellWidth, snakeHead.y * cellHeight, cellWidth, cellHeight);

        for (Point point : snake.getBody()) {

            graphicsContext.setFill(Color.GAINSBORO);
            graphicsContext.fillRect(point.x * cellWidth, point.y * cellHeight, cellWidth, cellHeight);
        }

        //snake.getTail(); - какая-то логика тут
    }
}
