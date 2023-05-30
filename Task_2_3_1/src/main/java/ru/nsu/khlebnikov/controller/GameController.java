package ru.nsu.khlebnikov.controller;

import javafx.scene.input.KeyCode;
import ru.nsu.khlebnikov.Main;
import ru.nsu.khlebnikov.model.snake.Snake;

public class GameController {

    public static void handler(KeyCode keyCode, Snake snake, int heightCells, int widthCells, Main main) {
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
            Main.getPrimaryStage().close();
        } else if (keyCode == KeyCode.SPACE) {
            main.restart();
        } else if (keyCode == KeyCode.P) {
            if (Main.getGameState().equals(Main.GameState.GAME)) {
                Main.setGameState(Main.GameState.STOPPED);
            } else {
                Main.setGameState(Main.GameState.GAME);
            }
        }
    }
}
