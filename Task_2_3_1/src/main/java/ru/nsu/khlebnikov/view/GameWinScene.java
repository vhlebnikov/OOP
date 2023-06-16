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
import ru.nsu.khlebnikov.Game;
import ru.nsu.khlebnikov.controller.GameWinController;

/**
 * Class of the game won scene.
 */
public class GameWinScene extends Scene {
    private final Canvas canvas;
    private final Image snapshot;

    /**
     * Game won scene constructor.
     *
     * @param root - pane where we will store all our elements
     * @param snapshot - snapshot of the game won moment
     * @param windowWidth - window width
     * @param windowHeight - window height
     * @param totalScore - total score
     * @param totalGoal - total goal
     * @param game - game class
     */
    public GameWinScene(StackPane root, Image snapshot, double windowWidth, double windowHeight,
                        double totalScore, double totalGoal, Game game) {
        super(root, windowWidth, windowHeight);

        Label gameOverText = new Label("YOU WON!");
        Font font = Font.loadFont(getClass()
                .getClassLoader().getResourceAsStream("fonts/Mario-Kart-DS.ttf"), 60);
        gameOverText.setFont(font);

        Label scoreText = new Label((int) totalScore + " of " + (int) totalGoal);
        scoreText.setFont(font);

        Label helperText;

        if (Game.getFileName().equals("config/level3.json")) {
            helperText = new Label("Press [R] to restart.");
        } else {
            helperText = new Label("Press [Space] for the next level!\nPress [R] to restart.");
        }

        VBox verticalBox = new VBox(gameOverText, scoreText, helperText);
        verticalBox.setAlignment(Pos.CENTER);

        this.snapshot = snapshot;
        canvas = new Canvas(windowWidth, windowHeight);

        root.getChildren().addAll(canvas, verticalBox);

        this.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            GameWinController.handler(keyCode, game);
        });
    }

    /**
     * Method that draws game won scene.
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
