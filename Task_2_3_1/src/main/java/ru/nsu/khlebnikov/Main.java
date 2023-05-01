package ru.nsu.khlebnikov;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;


public class Main extends Application {
    private static GameField gameField;
    private static Score score;
    private int widthCells = 20;
    private int heightCells = 20;
    private double initWindowWidth = 800;
    private double initWindowHeight = 400;

    @Override
    public void start(Stage primaryStage) {
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
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(400);
        primaryStage.show();

        startGame(scene);
    }

    private void startGame(Scene scene) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameField.draw(scene.widthProperty().get() * 0.75, scene.heightProperty().get());
                score.draw(scene.widthProperty().get() * 0.25, scene.heightProperty().get());
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}