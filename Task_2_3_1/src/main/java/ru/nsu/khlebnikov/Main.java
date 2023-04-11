package ru.nsu.khlebnikov;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    private static final int CEIL_SIZE = 20;
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;
    private static double SPEED = 5;

    private List<Point> snake;
    private final Point food = new Point(0, 0);
    private enum Direction {
        UP, RIGHT, DOWN, LEFT
    }
    private Direction direction;
    private boolean gameOver;
    private Canvas canvas;
    private Label scoreLabel;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        canvas = new Canvas(WIDTH * CEIL_SIZE, HEIGHT * CEIL_SIZE);
        root.setCenter(canvas);

        scoreLabel = new Label("Score: 0");
        scoreLabel.setAlignment(Pos.CENTER);
        scoreLabel.setFont(new Font("Digital-7", 30));
        root.setTop(scoreLabel);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.UP && direction != Direction.DOWN) {
                direction = Direction.UP;
            } else if (keyCode == KeyCode.RIGHT && direction != Direction.LEFT) {
                direction = Direction.RIGHT;
            } else if (keyCode == KeyCode.DOWN && direction != Direction.UP) {
                direction = Direction.DOWN;
            } else if (keyCode == KeyCode.LEFT && direction != Direction.RIGHT) {
                direction = Direction.LEFT;
            } else if (keyCode == KeyCode.SPACE) {
                snake = new ArrayList<>();
                snake.add(new Point(WIDTH / 2, HEIGHT / 2));
                direction = Direction.RIGHT;
                gameOver = false;
                scoreLabel.setText("Score: 0");
                spawnFood();
            } else if (keyCode == KeyCode.ESCAPE) {
                primaryStage.close();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
        primaryStage.show();

        startGame();
    }

    private void startGame() {
        snake = new ArrayList<>();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));
        direction = Direction.RIGHT;
        gameOver = false;

        spawnFood();

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1000000000 / SPEED) {
                    lastUpdate = now;
                    update();
                    draw();
                }
            }
        };
        timer.start();
    }

    private void update() {
        if (gameOver) {
            return;
        }

        Point head = snake.get(0);

        double x = head.getX();
        double y = head.getY();

        switch (direction) {
            case UP -> y--;
            case RIGHT -> x++;
            case DOWN -> y++;
            case LEFT -> x--;
        }

        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            gameOver = true;
            return;
        }

        for (Point part : snake) {
            if (part.getX() == x && part.getY() == y) {
                gameOver = true;
                return;
            }
        }

        if (x == food.getX() && y == food.getY()) {
            Point tail = snake.get(snake.size() - 1);
            snake.add(new Point((int) tail.getX(), (int) tail.getY()));
            spawnFood();
            int score = snake.size() - 1;
            SPEED += 0.3;
            scoreLabel.setText("Score: " + score);
        }

        for (Point part : snake) {
            double tempX = part.getX();
            double tempY = part.getY();
            part.setLocation(x, y);
            x = tempX;
            y = tempY;
        }
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);

        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        for (Point part : snake) {
            gc.fillRect(part.getX() * CEIL_SIZE, part.getY() * CEIL_SIZE, CEIL_SIZE, CEIL_SIZE);
            gc.strokeRect(part.getX() * CEIL_SIZE, part.getY() * CEIL_SIZE, CEIL_SIZE, CEIL_SIZE);
        }

        gc.setFill(Color.RED);
        gc.fillRect(food.getX() * CEIL_SIZE, food.getY() * CEIL_SIZE, CEIL_SIZE, CEIL_SIZE);
    }

    private void spawnFood() {
        Random random = new Random();
        food.setLocation(random.nextInt(WIDTH), random.nextInt(HEIGHT));
        for (Point part : snake) {
            if (part.getX() == food.getX() && part.getY() == food.getY()) {
                spawnFood();
                return;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}