package ru.nsu.khlebnikov.view;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ru.nsu.khlebnikov.model.food.Food;
import ru.nsu.khlebnikov.model.food.FoodItem;
import ru.nsu.khlebnikov.model.snake.Snake;

import java.awt.Point;
import java.util.List;
import java.util.Objects;

public class GameField extends Pane {
    private final int widthCells;
    private final int heightCells;
    private final Canvas canvas;
    private GraphicsContext graphicsContext;

    public GameField(int width, int height, double windowWidth, double windowHeight) {
        widthCells = width;
        heightCells = height;
        canvas = new Canvas(windowWidth, windowHeight);
        this.getChildren().add(canvas);
    }

    public void drawField(double windowWidth, double windowHeight) {
        graphicsContext = canvas.getGraphicsContext2D();

        double cellWidth = windowWidth / widthCells;
        double cellHeight = windowHeight / heightCells;

        canvas.setWidth(windowWidth);
        canvas.setHeight(windowHeight);

        for (int h = 0; h < heightCells; h++) {
            for (int w = 0; w < widthCells; w++) {
                if ((h + w) % 2 == 0) {
                    graphicsContext.setFill(Color.web("#AAD751"));
                } else {
                    graphicsContext.setFill(Color.web("#A2D149"));
                }

                graphicsContext.fillRect(w * cellWidth, h * cellHeight, cellWidth, cellHeight);
            }
        }
    }

    public void drawWalls(double windowWidth, double windowHeight, List<Point> wallsCoordinates) {
        double cellWidth = windowWidth / widthCells;
        double cellHeight = windowHeight / heightCells;

        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/brickwall.png")));

        for (Point point : wallsCoordinates) {
            graphicsContext.drawImage(image, point.x * cellWidth, point.y * cellHeight, cellWidth, cellHeight);
        }
    }

    public void drawSnake(double windowWidth, double windowHeight, Snake snake, int botId) {
        double cellWidth = windowWidth / widthCells;
        double cellHeight = windowHeight / heightCells;

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        Image head;
        Image body;
        Image tail;
        Image rotation;

        if (botId == 0) {
            head = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_head.png")));
            body = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_body.png")));
            tail = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_tail.png")));
            rotation = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_rotation.png")));
        } else if (botId == 1) {
            head = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_head_bot.png")));
            body = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_body_bot.png")));
            tail = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_tail_bot.png")));
            rotation = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_rotation_bot.png")));
        } else if (botId == 2) {
            head = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_head_pink.png")));
            body = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_body_pink.png")));
            tail = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_tail_pink.png")));
            rotation = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_rotation_pink.png")));
        } else {
            head = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_head.png")));
            body = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_body.png")));
            tail = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_tail.png")));
            rotation = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake_rotation.png")));
        }

        Point snakeHead = snake.getHead();
        ImageView headImageView = new ImageView(head);
        headImageView.setRotate(snake.getHeadAngle());
        Image rotatedHead = headImageView.snapshot(params, null);
        graphicsContext.drawImage(rotatedHead, snakeHead.x * cellWidth, snakeHead.y * cellHeight, cellWidth, cellHeight);

        int snakeSize = snake.getSize();

        if (snakeSize == 1) {
            return;
        }

        for (Point point : snake.getBody()) {
            Snake.BodyOrientation orientation = snake.getBodyOrientation(point);
            switch (orientation) {
                case HORIZONTALLY -> {
                    ImageView bodyImageView = new ImageView(body);
                    bodyImageView.setRotate(90);
                    Image rotatedBody = bodyImageView.snapshot(params, null);
                    graphicsContext.drawImage(rotatedBody, point.x * cellWidth, point.y * cellHeight, cellWidth, cellHeight);
                }
                case VERTICALLY ->
                        graphicsContext.drawImage(body, point.x * cellWidth, point.y * cellHeight, cellWidth, cellHeight);
                case ROTATED -> {
                    ImageView rotationImageView = new ImageView(rotation);
                    rotationImageView.setRotate(snake.getBodyAngle(point));
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

    public void drawFood(double windowWidth, double windowHeight, Food food) {
        double cellWidth = windowWidth / widthCells;
        double cellHeight = windowHeight / heightCells;

        Image watermelon = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/watermelon.png")));
        Image apple = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/apple.png")));
        Image lemon = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/lemon.png")));

        for (FoodItem foodItem : food.getFood()) {
            Point point = foodItem.point();
            switch (foodItem.foodType()) {
                case WATERMELON ->
                        graphicsContext.drawImage(watermelon, point.x * cellWidth, point.y * cellHeight, cellWidth, cellHeight);
                case APPLE ->
                        graphicsContext.drawImage(apple, point.x * cellWidth, point.y * cellHeight, cellWidth, cellHeight);
                case LEMON ->
                        graphicsContext.drawImage(lemon, point.x * cellWidth, point.y * cellHeight, cellWidth, cellHeight);
            }
        }
    }
}
