package ru.nsu.khlebnikov.controller;

import javafx.scene.input.KeyCode;
import ru.nsu.khlebnikov.Game;

/**
 * Class that contains method to handle button clicks by the user on game won scene.
 */
public class GameWinController {

    /**
     * Method to handle button clicks by user on game won game scene.
     *
     * @param keyCode - button game code
     * @param game - main class to initialize game state
     */
    public static void handler(KeyCode keyCode, Game game) {
        if (keyCode == KeyCode.SPACE && !Game.getFileName().equals("config/level3.json")) {
            if (Game.getFileName().equals("config/level1.json")) {
                Game.setFileName("config/level2.json");
            } else if (Game.getFileName().equals("config/level2.json")) {
                Game.setFileName("config/level3.json");
            }
            game.initialization();
            Game.setGameState(Game.GameState.GAME);
            Game.setGameScene();
        } else if (keyCode == KeyCode.R) {
            Game.setGameState(Game.GameState.GAME);
            Game.setGameScene();
        } else if (keyCode == KeyCode.ESCAPE) {
            Game.getPrimaryStage().close();
        }
    }
}
