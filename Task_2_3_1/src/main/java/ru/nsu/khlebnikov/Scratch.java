package ru.nsu.khlebnikov;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.print.attribute.standard.PrinterMakeAndModel;


public class Scratch extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label lbl_size = new Label();
        lbl_size.setTextFill(Color.WHITE);

        Canvas canvas = new Canvas(800, 800);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(canvas);
        stackPane.getChildren().add(lbl_size);

        Scene scene = new Scene(stackPane, 800, 800);


        primaryStage.setTitle("Window Size");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
//        primaryStage.setMaxHeight(850);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                    GraphicsContext gc = canvas.getGraphicsContext2D();

                    gc.setFill(Color.WHITE);
                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                    canvas.setWidth(primaryStage.widthProperty().get());
                    canvas.setHeight(primaryStage.heightProperty().get());
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, primaryStage.widthProperty().get(), primaryStage.heightProperty().get());
                    lbl_size.textProperty().bind(Bindings.format("%1$.0fx%2$.0f", primaryStage.widthProperty(), primaryStage.heightProperty()));
            }
        };
        timer.start();
    }
}
