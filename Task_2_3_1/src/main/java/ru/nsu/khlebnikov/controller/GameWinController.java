package ru.nsu.khlebnikov.controller;

import javafx.scene.input.KeyCode;
import ru.nsu.khlebnikov.Main;

/**
 * Class that contains method to handle button clicks by the user on game won scene.
 */
public class GameWinController {

    /**
     * Method to handle button clicks by user on game won game scene.
     *
     * @param keyCode - button game code
     * @param main - main class to initialize game state
     */
    public static void handler(KeyCode keyCode, Main main) {
        if (keyCode == KeyCode.SPACE && !Main.getFileName().equals("config/level3.json")) {
            if (Main.getFileName().equals("config/level1.json")) {
                Main.setFileName("config/level2.json");
            } else if (Main.getFileName().equals("config/level2.json")) {
                Main.setFileName("config/level3.json");
            }
            main.initialization();
            Main.setGameState(Main.GameState.GAME);
            Main.setGameScene();
        } else if (keyCode == KeyCode.R) {
            Main.setGameState(Main.GameState.GAME);
            Main.setGameScene();
        } else if (keyCode == KeyCode.ESCAPE) {
            Main.getPrimaryStage().close();
        }
    }
}
