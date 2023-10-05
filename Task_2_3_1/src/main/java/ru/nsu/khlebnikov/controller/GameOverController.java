package ru.nsu.khlebnikov.controller;

import javafx.scene.input.KeyCode;
import ru.nsu.khlebnikov.Game;

/**
 * Class that contains method to handle button clicks by the user on game over scene.
 */
public class GameOverController {

    /**
     * Method to handle button clicks by user on game over scene.
     *
     * @param keyCode - button key code.
     */
    public static void handler(KeyCode keyCode) {
        if (keyCode == KeyCode.SPACE) {
            Game.setGameState(Game.GameState.GAME);
            Game.setGameScene();
        } else if (keyCode == KeyCode.ESCAPE) {
            Game.getPrimaryStage().close();
        }
    }
}
