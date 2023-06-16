package ru.nsu.khlebnikov.controller;

import javafx.scene.input.KeyCode;
import ru.nsu.khlebnikov.Game;
import ru.nsu.khlebnikov.model.snake.Snake;

/**
 * Class that contains method to handle button clicks by the user on game scene.
 */
public class GameController {

    /**
     * Method to handle button clicks by user on game scene.
     *
     * @param keyCode - button key code
     * @param snake - user's snake
     * @param heightCells - number of cells by height
     * @param widthCells - number of cells by width
     * @param game - game class to restart the game
     */
    public static void handler(KeyCode keyCode, Snake snake, int heightCells, int widthCells,
                               Game game) {
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
            Game.getPrimaryStage().close();
        } else if (keyCode == KeyCode.SPACE) {
            game.restart();
        } else if (keyCode == KeyCode.P) {
            if (Game.getGameState().equals(Game.GameState.GAME)) {
                Game.setGameState(Game.GameState.STOPPED);
            } else {
                Game.setGameState(Game.GameState.GAME);
            }
        }
    }
}
