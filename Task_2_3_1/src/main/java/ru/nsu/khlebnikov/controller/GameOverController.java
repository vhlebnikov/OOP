package ru.nsu.khlebnikov.controller;

import javafx.scene.input.KeyCode;
import ru.nsu.khlebnikov.Main;

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
            Main.setGameState(Main.GameState.GAME);
            Main.setGameScene();
        } else if (keyCode == KeyCode.ESCAPE) {
            Main.getPrimaryStage().close();
        }
    }
}
