package ru.nsu.khlebnikov;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameOverScene extends Scene {
    private Label gameOverText;
    private Canvas canvas;
    private Image snapshot;
    private GraphicsContext graphicsContext;

    public GameOverScene(StackPane root, Image snapshot, double windowWidth, double windowHeight) {
        super(root, windowWidth, windowHeight);

        gameOverText = new Label("GAME OVER");
        Font font = Font.loadFont(getClass().getClassLoader().getResourceAsStream("fonts/Mario-Kart-DS.ttf"), 60);
        gameOverText.setFont(font);
        this.snapshot = snapshot;
        canvas = new Canvas(windowWidth, windowHeight);

        root.getChildren().addAll(canvas, gameOverText);

        this.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.SPACE) {
                Main.setGameState(Main.GameState.GAME);
                Main.setGameScene();
            }
        });
    }

    public void draw() {
        double windowWidth = this.widthProperty().get();
        double windowHeight = this.heightProperty().get();

        graphicsContext = canvas.getGraphicsContext2D();
        canvas.setWidth(windowWidth);
        canvas.setHeight(windowHeight);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        ImageView snapshotImageView = new ImageView(snapshot);
        snapshotImageView.setFitWidth(windowWidth);
        snapshotImageView.setFitHeight(windowHeight);
        Image image = snapshotImageView.snapshot(params, null);

        graphicsContext.drawImage(image, 0,0 );
    }
}
