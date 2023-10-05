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
import ru.nsu.khlebnikov.controller.GameOverController;

/**
 * Class of the game over scene.
 */
public class GameOverScene extends Scene {
    private final Canvas canvas;
    private final Image snapshot;

    /**
     * Game over scene constructor.
     *
     * @param root - pane where we will store all our elements
     * @param snapshot - snapshot of the game over moment
     * @param windowWidth - window width
     * @param windowHeight - window height
     * @param totalScore - total score
     * @param totalGoal - total goal
     */
    public GameOverScene(StackPane root, Image snapshot, double windowWidth, double windowHeight,
                         double totalScore, double totalGoal) {
        super(root, windowWidth, windowHeight);

        Label gameOverText = new Label("GAME OVER");
        Font font = Font.loadFont(getClass()
                .getClassLoader().getResourceAsStream("fonts/Mario-Kart-DS.ttf"), 60);
        gameOverText.setFont(font);

        Label scoreText = new Label((int) totalScore + " of " + (int) totalGoal);
        scoreText.setFont(font);

        Label helperText = new Label("Press [Space] to restart!");

        VBox verticalBox = new VBox(gameOverText, scoreText, helperText);
        verticalBox.setAlignment(Pos.CENTER);

        this.snapshot = snapshot;
        canvas = new Canvas(windowWidth, windowHeight);

        root.getChildren().addAll(canvas, verticalBox);

        this.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            GameOverController.handler(keyCode);
        });
    }

    /**
     * Method that draws game over scene.
     */
    public void draw() {
        double windowWidth = this.widthProperty().get();
        double windowHeight = this.heightProperty().get();

        canvas.setWidth(windowWidth);
        canvas.setHeight(windowHeight);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        ImageView snapshotImageView = new ImageView(snapshot);
        snapshotImageView.setFitWidth(windowWidth);
        snapshotImageView.setFitHeight(windowHeight);
        Image image = snapshotImageView.snapshot(params, null);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.drawImage(image, 0, 0);
    }
}
