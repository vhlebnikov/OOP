package ru.nsu.khlebnikov;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Main extends Application {
    private static Stage primaryStage;
    private GameField gameField;
    private Score score;
    private static Scene scene;
    private GameOverScene gameOverScene;

    private int widthCells = 20;
    private int heightCells = 20;
    public enum GameState {
        GAME, GAME_OVER, STOPPED, WIN
    }
    private static GameState gameState = GameState.GAME;
    private double initSnakeSpeed = 5;
    private Snake snake = new Snake(new Point(widthCells / 2, heightCells / 2), initSnakeSpeed);
    private Walls walls = new Walls(new ArrayList<>(List.of(
            new Point(1, 1), new Point(2, 1), new Point(2, 2), new Point(2, 3),
            new Point(1, 3)
    )));
    private Food food = new Food(3, 3, 3, walls.getCoordinates(), widthCells, heightCells);

    @Override
    public void start(Stage primaryStage1) {
        primaryStage = primaryStage1;

        BorderPane root = new BorderPane();
        scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.UP && snake.getDirection() != Snake.Direction.DOWN) {
                if ((snake.getSize() > 1 && snake.getHead().getY() != snake.getSnake().get(1).getY() + 1
                        && heightCells - snake.getHead().getY() != snake.getSnake().get(1).getY() + 1)
                        || snake.getSize() == 1) {
                    snake.setDirection(Snake.Direction.UP);
                }
            } else if (keyCode == KeyCode.RIGHT && snake.getDirection() != Snake.Direction.LEFT) {
                if ((snake.getSize() > 1 && snake.getHead().getX() != snake.getSnake().get(1).getX() - 1
                        && widthCells - snake.getHead().getX() != snake.getSnake().get(1).getX() + 1)
                        || snake.getSize() == 1) {
                    snake.setDirection(Snake.Direction.RIGHT);
                }
            } else if (keyCode == KeyCode.DOWN && snake.getDirection() != Snake.Direction.UP) {
                if ((snake.getSize() > 1 && snake.getHead().getY() != snake.getSnake().get(1).getY() - 1
                        && heightCells - snake.getHead().getY() != snake.getSnake().get(1).getY() + 1)
                        || snake.getSize() == 1) {
                    snake.setDirection(Snake.Direction.DOWN);
                }
            } else if (keyCode == KeyCode.LEFT && snake.getDirection() != Snake.Direction.RIGHT) {
                if ((snake.getSize() > 1 && snake.getHead().getX() != snake.getSnake().get(1).getX() + 1
                        && widthCells - snake.getHead().getX() != snake.getSnake().get(1).getX() + 1)
                        || snake.getSize() == 1) {
                    snake.setDirection(Snake.Direction.LEFT);
                }
            } else if (keyCode == KeyCode.ESCAPE) {
                primaryStage.close();
            } else if (keyCode == KeyCode.SPACE) {
                restart();
            }
        });

        gameField = new GameField(widthCells, heightCells, primaryStage.getWidth() * 0.75, primaryStage.getHeight());
        score = new Score(10, 10, 10, primaryStage.getWidth() * 0.25, primaryStage.getHeight());

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
                if (now - lastUpdate >= 1000000000 / snake.getSpeed()) {
                    lastUpdate = now;
                    if (getGameState().equals(GameState.GAME_OVER)) {
                        gameOverScene.draw();
                    } else {
                        update();
                        draw();
                    }
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
            case UP -> head.setLocation(head.x, head.y - 1);
            case DOWN -> head.setLocation(head.x, head.y + 1);
            case LEFT -> head.setLocation(head.x - 1, head.y);
            case RIGHT -> head.setLocation(head.x + 1, head.y);
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

        List<FoodItem> eatenFood = food.getFood().stream().filter(f -> f.getPoint().equals(head)).toList();

        if (!eatenFood.isEmpty()) {
            Point newTail = new Point(snake.getTail());
            snake.addNode(newTail);
            food.removeFood(eatenFood.get(0));
            food.generateFood(1, eatenFood.get(0).getFoodType(), walls.getCoordinates(), widthCells, heightCells);
            switch (eatenFood.get(0).getFoodType()) {
                case WATERMELON -> score.setWatermelons(score.getWatermelons() + 1);
                case APPLE -> score.setApples(score.getApples() + 1);
                case LEMON -> score.setLemons(score.getLemons() + 1);
            }
            snake.setSpeed(snake.getSpeed() + 0.5);
        }

        if (snake.getSize() > 2 && (snake.getBody().stream().anyMatch(x -> x.equals(head))
                || snake.getTail().equals(head))
                || walls.getCoordinates().stream().anyMatch(w -> w.equals(head))) {
            gameOver();
            return;
        }

        for (Point part : snake.getSnake()) {
            Point temp = new Point(part);
            part.setLocation(head);
            head.setLocation(temp);
        }
    }

    public void restart() {
        snake = new Snake(new Point(widthCells / 2, heightCells / 2), initSnakeSpeed);
        score.setWatermelons(0);
        score.setApples(0);
        score.setLemons(0);
        food.regenerateFood(walls.getCoordinates(), widthCells, heightCells);
        setGameState(GameState.GAME);
    }

    public void gameOver() {
        snake = new Snake(new Point(widthCells / 2, heightCells / 2), initSnakeSpeed);
        score.setWatermelons(0);
        score.setApples(0);
        score.setLemons(0);
        food.regenerateFood(walls.getCoordinates(), widthCells, heightCells);
        setGameState(GameState.GAME_OVER);
        Image snapshot = scene.snapshot(null);
        gameOverScene = new GameOverScene(new StackPane(), snapshot, scene.widthProperty().get(), scene.heightProperty().get());
        primaryStage.setScene(gameOverScene);
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

    public static void main(String[] args) {
        launch(args);
    }
}