package ru.nsu.khlebnikov.controller;

import javafx.scene.input.KeyCode;
import ru.nsu.khlebnikov.Main;

public class GameOverController {

    public static void handler(KeyCode keyCode) {
        if (keyCode == KeyCode.SPACE) {
            Main.setGameState(Main.GameState.GAME);
            Main.setGameScene();
        } else if (keyCode == KeyCode.ESCAPE) {
            Main.getPrimaryStage().close();
        }
    }
}
