package ru.nsu.khlebnikov;

import java.awt.Point;
import java.util.List;
import java.util.Objects;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ru.nsu.khlebnikov.controller.GameController;
import ru.nsu.khlebnikov.model.food.Food;
import ru.nsu.khlebnikov.model.json.JsonDto;
import ru.nsu.khlebnikov.model.json.JsonGetter;
import ru.nsu.khlebnikov.model.snake.Snake;
import ru.nsu.khlebnikov.model.walls.Walls;
import ru.nsu.khlebnikov.view.GameField;
import ru.nsu.khlebnikov.view.GameOverScene;
import ru.nsu.khlebnikov.view.GameWinScene;
import ru.nsu.khlebnikov.view.Score;

/**
 * Main class that contains methods to initialize and update game state.
 */
public class Game extends Application {
    private static Stage primaryStage;
    private GameField gameField;
    private Score score;
    private static Scene scene;
    private GameOverScene gameOverScene;
    private GameWinScene gameWinScene;
    private static String fileName = "config/level1.json";
    private static JsonDto jsonDTO = new JsonGetter().getData(fileName);
    private int widthCells = jsonDTO.widthCells();
    private int heightCells = jsonDTO.heightCells();

    /**
     * Game state enum.
     */
    public enum GameState {
        GAME, GAME_OVER, STOPPED, WIN
    }

    private static GameState gameState = GameState.GAME;
    private double initSnakeSpeed = jsonDTO.initSnakeSpeed();
    private double initSnakeGrowthSpeed = jsonDTO.initSnakeGrowthSpeed();
    private int initWatermelons = jsonDTO.initWatermelons();
    private int initApples = jsonDTO.initApples();
    private int initLemons = jsonDTO.initLemons();
    private int watermelonsGoal = jsonDTO.watermelonsGoal();
    private int applesGoal = jsonDTO.applesGoal();
    private int lemonsGoal = jsonDTO.lemonsGoal();
    private Snake snake =
            new Snake(new Point(widthCells / 2, heightCells / 2),
                    initSnakeSpeed, initSnakeGrowthSpeed);
    private Snake hunterBotSnake =
            new Snake(List.of(new Point(0, 0), new Point(1, 0), new Point(2, 0)), 5, 0.5);
    private Snake gluttonBotSnake =
            new Snake(List.of(new Point(5, 0), new Point(6, 0)), 5, 0.5);
    private Walls walls = new Walls(jsonDTO.walls());
    private Food food = new Food(initWatermelons, initApples, initLemons,
            walls.getCoordinates(), snake.getSnake(), widthCells, heightCells);

    @Override
    public void start(Stage primaryStage1) {
        primaryStage = primaryStage1;

        BorderPane root = new BorderPane();
        scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            GameController.handler(keyCode, snake, heightCells, widthCells, this);
        });

        gameField = new GameField(widthCells, heightCells,
                primaryStage.getWidth() * 0.75, primaryStage.getHeight());
        score = new Score(watermelonsGoal, applesGoal, lemonsGoal,
                primaryStage.getWidth() * 0.25, primaryStage.getHeight());

        root.setLeft(gameField);
        root.setRight(score);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader()
                .getResourceAsStream("img/snake.png")));
        primaryStage.getIcons().add(image);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.show();

        startGame();
    }

    /**
     * Method that starts the game and starts the animation timer
     * where objects will be updated and drew.
     */
    private void startGame() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1000000000 / snake.getSpeed()) {
                    lastUpdate = now;
                    if (!getGameState().equals(GameState.STOPPED)) {
                        if (getGameState().equals(GameState.GAME_OVER)) {
                            gameOverScene.draw();
                        } else if (getGameState().equals(GameState.WIN)) {
                            gameWinScene.draw();
                        } else {
                            snake.update(food, walls, score, Game.this, widthCells, heightCells);
                            hunterBotSnake.hunterBotUpdate(snake, walls, Game.this);
                            gluttonBotSnake.gluttonBotUpdate(walls, food, snake,
                                    widthCells, heightCells);
                            draw();
                        }
                    }
                }
            }
        };
        timer.start();
    }

    /**
     * Method that draws objects on scene.
     */
    private void draw() {
        gameField.drawField(scene.widthProperty().get() * 0.75, scene.heightProperty().get());
        gameField.drawWalls(scene.widthProperty().get() * 0.75, scene.heightProperty().get(),
                walls.getCoordinates());
        gameField.drawFood(scene.widthProperty().get() * 0.75, scene.heightProperty().get(), food);
        gameField.drawSnake(scene.widthProperty().get() * 0.75, scene.heightProperty().get(),
                snake, 0);
        gameField.drawSnake(scene.widthProperty().get() * 0.75, scene.heightProperty().get(),
                hunterBotSnake, 1);
        gameField.drawSnake(scene.widthProperty().get() * 0.75, scene.heightProperty().get(),
                gluttonBotSnake, 2);
        score.draw(scene.widthProperty().get() * 0.25, scene.heightProperty().get());
    }

    /**
     * Method that restarts the game.
     */
    public void restart() {
        initialization();
        setGameState(GameState.GAME);
    }

    /**
     * Method that initialize game state.
     */
    public void initialization() {
        setJsonDto();
        widthCells = jsonDTO.widthCells();
        heightCells = jsonDTO.heightCells();
        initSnakeSpeed = jsonDTO.initSnakeSpeed();
        initSnakeGrowthSpeed = jsonDTO.initSnakeGrowthSpeed();
        initWatermelons = jsonDTO.initWatermelons();
        initApples = jsonDTO.initApples();
        initLemons = jsonDTO.initLemons();
        watermelonsGoal = jsonDTO.watermelonsGoal();
        applesGoal = jsonDTO.applesGoal();
        lemonsGoal = jsonDTO.lemonsGoal();
        walls = new Walls(jsonDTO.walls());
        food = new Food(initWatermelons, initApples, initLemons,
                walls.getCoordinates(), snake.getSnake(), widthCells, heightCells);

        BorderPane root = new BorderPane();
        scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            GameController.handler(keyCode, snake, heightCells, widthCells, this);
        });

        gameField = new GameField(widthCells, heightCells,
                primaryStage.getWidth() * 0.75, primaryStage.getHeight());
        score = new Score(watermelonsGoal, applesGoal, lemonsGoal,
                primaryStage.getWidth() * 0.25, primaryStage.getHeight());

        root.setLeft(gameField);
        root.setRight(score);

        primaryStage.setScene(scene);

        snake = new Snake(new Point(widthCells / 2, heightCells / 2),
                initSnakeSpeed, initSnakeGrowthSpeed);
        hunterBotSnake = new Snake(List.of(new Point(0, 0), new Point(1, 0), new Point(2, 0)),
                5, 0.5);
        gluttonBotSnake = new Snake(List.of(new Point(5, 0), new Point(6, 0)),
                5, 0.5);

        score.setWatermelons(0);
        score.setApples(0);
        score.setLemons(0);
        food.regenerateFood(walls.getCoordinates(), snake.getSnake(), widthCells, heightCells);
    }

    /**
     * Method that sets game over state.
     */
    public void gameOver() {
        setGameState(GameState.GAME_OVER);
        Image snapshot = scene.snapshot(null);
        gameOverScene = new GameOverScene(new StackPane(), snapshot, scene.widthProperty().get(),
                scene.heightProperty().get(), score.getTotalScore(), score.getTotalGoal());

        initialization();

        primaryStage.setScene(gameOverScene);
    }

    /**
     * Method that sets game won state.
     */
    public void gameWin() {
        setGameState(GameState.WIN);
        Image snapshot = scene.snapshot(null);
        gameWinScene = new GameWinScene(new StackPane(), snapshot, scene.widthProperty().get(),
                scene.heightProperty().get(), score.getTotalScore(), score.getTotalGoal(), this);

        initialization();

        primaryStage.setScene(gameWinScene);
    }

    public static GameState getGameState() {
        return gameState;
    }

    public static void setGameState(GameState state) {
        gameState = state;
    }

    public static void setGameScene() {
        primaryStage.setScene(scene);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static String getFileName() {
        return fileName;
    }

    public static void setFileName(String fileName) {
        Game.fileName = fileName;
    }

    public static void setJsonDto() {
        Game.jsonDTO = new JsonGetter().getData(fileName);
    }

    public static void main(String[] args) {
        launch(args);
    }
}