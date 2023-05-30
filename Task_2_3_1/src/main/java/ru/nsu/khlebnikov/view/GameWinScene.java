package ru.nsu.khlebnikov.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ru.nsu.khlebnikov.Main;
import ru.nsu.khlebnikov.controller.GameWinController;

public class GameWinScene extends Scene {
    private final Canvas canvas;
    private final Image snapshot;

    public GameWinScene(StackPane root, Image snapshot, double windowWidth, double windowHeight, double totalScore, double totalGoal, Main main) {
        super(root, windowWidth, windowHeight);

        Label gameOverText = new Label("YOU WON!");
        Font font = Font.loadFont(getClass().getClassLoader().getResourceAsStream("fonts/Mario-Kart-DS.ttf"), 60);
        gameOverText.setFont(font);

        Label scoreText = new Label((int) totalScore + " of " + (int) totalGoal);
        scoreText.setFont(font);

        Label helperText;

        if (Main.getFileName().equals("config/level3.json")) {
            helperText = new Label("Press [R] to restart.");
        } else {
            helperText = new Label("Press [Space] for the next level!\nPress [R] to restart.");
        }

        VBox vBox = new VBox(gameOverText, scoreText, helperText);
        vBox.setAlignment(Pos.CENTER);

        this.snapshot = snapshot;
        canvas = new Canvas(windowWidth, windowHeight);

        root.getChildren().addAll(canvas, vBox);

        this.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            GameWinController.handler(keyCode, main);
        });
    }

    public void draw() {
        double windowWidth = this.widthProperty().get();
        double windowHeight = this.heightProperty().get();

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        canvas.setWidth(windowWidth);
        canvas.setHeight(windowHeight);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        ImageView snapshotImageView = new ImageView(snapshot);
        snapshotImageView.setFitWidth(windowWidth);
        snapshotImageView.setFitHeight(windowHeight);
        Image image = snapshotImageView.snapshot(params, null);

        graphicsContext.drawImage(image, 0, 0);
    }
}
