package ru.nsu.khlebnikov;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Main extends Application {
    private static GameField gameField;
    private static Score score;
    private int widthCells = 20;
    private int heightCells = 20;
    private double initWindowWidth = 800;
    private double initWindowHeight = 400;
    private Snake snake = new Snake(new Point(widthCells / 2, heightCells / 2));
    private Walls walls = new Walls(new ArrayList<>(List.of(
            new Point(1, 1), new Point(2, 1), new Point(2, 2), new Point(2, 3),
            new Point(1, 3)
    )));

    @Override
    public void start(Stage primaryStage) {
        snake.setSnake(new ArrayList<>(List.of(
                new Point(11, 10), new Point(12, 10), new Point(12, 11), new Point(12, 12),
                new Point(13, 12), new Point(14, 12)
        )));

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, initWindowWidth, initWindowHeight);

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

        startGame(scene);
    }

    private void startGame(Scene scene) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameField.drawField(scene.widthProperty().get() * 0.75, scene.heightProperty().get());
                gameField.drawWalls(scene.widthProperty().get() * 0.75, scene.heightProperty().get(), walls.getCoordinates());
                gameField.drawSnake(scene.widthProperty().get() * 0.75, scene.heightProperty().get(), snake);
                score.draw(scene.widthProperty().get() * 0.25, scene.heightProperty().get());
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}