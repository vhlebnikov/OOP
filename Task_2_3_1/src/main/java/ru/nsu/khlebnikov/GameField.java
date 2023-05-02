package ru.nsu.khlebnikov;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ru.nsu.khlebnikov.Snake;

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

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        Image head = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_head.png")));
        Image body = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_body.png")));
        Image tail = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_tail.png")));
        Image rotation = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_rotation.png")));

        Point snakeHead = snake.getHead();
        ImageView headImageView = new ImageView(head);
        headImageView.setRotate(snake.getHeadAngle());
        Image rotatedHead = headImageView.snapshot(params, null);
        graphicsContext.drawImage(rotatedHead, snakeHead.x * cellWidth, snakeHead.y * cellHeight, cellWidth, cellHeight);

        for (Point point : snake.getBody()) {
            int res = snake.getBodyOrientationOrAngle(point);
            switch (res) {
                case (1) -> {
                    ImageView bodyImageView = new ImageView(body);
                    bodyImageView.setRotate(90);
                    Image rotatedBody = bodyImageView.snapshot(params, null);
                    graphicsContext.drawImage(rotatedBody, point.x * cellWidth, point.y * cellHeight, cellWidth, cellHeight);
                }
                case (2) -> {
                    graphicsContext.drawImage(body, point.x * cellWidth, point.y * cellHeight, cellWidth, cellHeight);
                }
                default -> {
                    ImageView rotationImageView = new ImageView(rotation);
                    rotationImageView.setRotate(res);
                    Image rotatedRotation = rotationImageView.snapshot(params, null);
                    graphicsContext.drawImage(rotatedRotation, point.x * cellWidth, point.y * cellHeight, cellWidth, cellHeight);
                }
            }
        }

        Point snakeTail = snake.getTail();
        ImageView tailImageView = new ImageView(tail);
        tailImageView.setRotate(snake.getTailAngle());
        Image rotatedTail = tailImageView.snapshot(params, null);
        graphicsContext.drawImage(rotatedTail, snakeTail.x * cellWidth, snakeTail.y * cellHeight, cellWidth, cellHeight);
    }
}
