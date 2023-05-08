package ru.nsu.khlebnikov;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Main extends Application {
    private static GameField gameField;
    private static Score score;
    private static Scene scene;
    private int widthCells = 20;
    private int heightCells = 20;
    private double initWindowWidth = 800;
    private double initWindowHeight = 400;
    private Snake snake = new Snake(new Point(widthCells / 2, heightCells / 2));
    private Walls walls = new Walls(new ArrayList<>(List.of(
            new Point(1, 1), new Point(2, 1), new Point(2, 2), new Point(2, 3),
            new Point(1, 3)
    )));
    private Food food = new Food(10, 10, 10, walls.getCoordinates(), widthCells, heightCells);

    @Override
    public void start(Stage primaryStage) {
        snake.addNode(new Point(11, 10));
        snake.addNode(new Point(12, 10));
        snake.addNode(new Point(13, 10));
        snake.addNode(new Point(14, 10));
        snake.addNode(new Point(15, 10));

        BorderPane root = new BorderPane();
        scene = new Scene(root, initWindowWidth, initWindowHeight);

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.UP && snake.getDirection() != Snake.Direction.DOWN) {
                snake.setDirection(Snake.Direction.UP);
            } else if (keyCode == KeyCode.RIGHT && snake.getDirection() != Snake.Direction.LEFT) {
                snake.setDirection(Snake.Direction.RIGHT);
            } else if (keyCode == KeyCode.DOWN && snake.getDirection() != Snake.Direction.UP) {
                snake.setDirection(Snake.Direction.DOWN);
            } else if (keyCode == KeyCode.LEFT && snake.getDirection() != Snake.Direction.RIGHT) {
                snake.setDirection(Snake.Direction.LEFT);
            } else if (keyCode == KeyCode.ESCAPE) {
                primaryStage.close();
            }
        });

        gameField = new GameField(widthCells, heightCells, initWindowWidth * 0.75, initWindowHeight);
        score = new Score(10, 10, 10, initWindowWidth * 0.25, initWindowHeight);

        root.setLeft(gameField);
        root.setRight(score);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("img/snake.png")));
        primaryStage.getIcons().add(image);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.show();

        startGame();
    }

    private void startGame() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1000000000 / 5) {
                    lastUpdate = now;
                    update();
                    draw();
                }
            }
        };
        timer.start();
    }

    private void draw() {
        gameField.drawField(scene.widthProperty().get() * 0.75, scene.heightProperty().get());
        gameField.drawWalls(scene.widthProperty().get() * 0.75, scene.heightProperty().get(), walls.getCoordinates());
        gameField.drawFood(scene.widthProperty().get() * 0.75, scene.heightProperty().get(), food);
        gameField.drawSnake(scene.widthProperty().get() * 0.75, scene.heightProperty().get(), snake);
        score.draw(scene.widthProperty().get() * 0.25, scene.heightProperty().get());
    }

    private void update() {
        Point head = new Point(snake.getHead());

        switch (snake.getDirection()) {
            case UP -> head.setLocation(head.x, head.y-1);
            case DOWN -> head.setLocation(head.x, head.y+1);
            case LEFT -> head.setLocation(head.x-1, head.y);
            case RIGHT -> head.setLocation(head.x+1, head.y);
        }

        if (head.x < 0) {
            head.setLocation(widthCells - 1, head.y);
        }
        if (head.y < 0) {
            head.setLocation(head.x, heightCells - 1);
        }
        if (head.x >= widthCells) {
            head.setLocation(0, head.y);
        }
        if (head.y >= heightCells) {
            head.setLocation(head.x, 0);
        }

        for (Point part : snake.getSnake()) {
            Point temp = new Point(part);
            part.setLocation(head);
            head.setLocation(temp);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}