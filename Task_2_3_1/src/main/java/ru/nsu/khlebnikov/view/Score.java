package ru.nsu.khlebnikov.view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class for the score panel.
 */
public class Score extends Pane {
    private double watermelons;
    private final double watermelonsGoal;
    private double apples;
    private final double applesGoal;
    private double lemons;
    private final double lemonsGoal;
    private final GridPane gridPane;
    private final ColumnConstraints column;
    private final Canvas canvas;
    private final ProgressBar pbWatermelons;
    private final ProgressBar pbApples;
    private final ProgressBar pbLemons;
    private final Label labelWatermelons;
    private final Label labelApples;
    private final Label labelLemons;

    /**
     * Score panel constructor.
     *
     * @param watermelonsGoal - watermelons goal
     * @param applesGoal apples goal
     * @param lemonsGoal - lemons goal
     * @param windowWidth - window width
     * @param windowHeight - window height
     */
    public Score(int watermelonsGoal, int applesGoal, int lemonsGoal,
                 double windowWidth, double windowHeight) {
        watermelons = 0;
        apples = 0;
        lemons = 0;
        this.lemonsGoal = lemonsGoal;
        this.applesGoal = applesGoal;
        this.watermelonsGoal = watermelonsGoal;

        canvas = new Canvas(windowWidth, windowHeight);

        pbWatermelons = new ProgressBar();
        pbWatermelons.setPadding(new Insets(5, 0, 5, 0));

        pbApples = new ProgressBar();
        pbApples.setPadding(new Insets(5, 0, 5, 0));

        pbLemons = new ProgressBar();
        pbLemons.setPadding(new Insets(5, 0, 5, 0));

        labelWatermelons = new Label("watermelons\n              goal");
        labelWatermelons.setAlignment(Pos.CENTER);
        labelWatermelons.setPadding(new Insets(5, 0, 0, 0));

        Font font = Font.loadFont(getClass()
                .getClassLoader().getResourceAsStream("fonts/Mario-Kart-DS.ttf"), 20);

        labelWatermelons.setFont(font);
        labelWatermelons.setTextFill(Color.WHITE);

        labelApples = new Label("apples goal");
        labelApples.setAlignment(Pos.CENTER);
        labelApples.setPadding(new Insets(5, 0, 0, 0));
        labelApples.setFont(font);
        labelApples.setTextFill(Color.WHITE);

        labelLemons = new Label("lemons goal");
        labelLemons.setAlignment(Pos.CENTER);
        labelLemons.setPadding(new Insets(5, 0, 0, 0));
        labelLemons.setFont(font);
        labelLemons.setTextFill(Color.WHITE);

        VBox watermelonBox = new VBox(labelWatermelons, pbWatermelons);
        watermelonBox.setBackground(Background.fill(Color.web("#606060")));
        watermelonBox.setAlignment(Pos.CENTER);

        VBox appleBox = new VBox(labelApples, pbApples);
        appleBox.setBackground(Background.fill(Color.web("#606060")));
        appleBox.setAlignment(Pos.CENTER);

        VBox lemonBox = new VBox(labelLemons, pbLemons);
        lemonBox.setBackground(Background.fill(Color.web("#606060")));
        lemonBox.setAlignment(Pos.CENTER);

        gridPane = new GridPane();
        gridPane.setVgap(7);
        gridPane.setPadding(new Insets(10, 0, 10, 0));

        column = new ColumnConstraints();
        column.setHalignment(HPos.CENTER);
        column.setPrefWidth(windowWidth * 0.9);
        gridPane.getColumnConstraints().add(column);

        gridPane.add(watermelonBox, 0, 0);
        gridPane.add(appleBox, 0, 1);
        gridPane.add(lemonBox, 0, 2);

        this.getChildren().addAll(canvas, gridPane);
    }

    public double getTotalGoal() {
        return watermelonsGoal + applesGoal + lemonsGoal;
    }

    public double getTotalScore() {
        return watermelons + apples + lemons;
    }

    /**
     * Method to set current number of eaten watermelons.
     *
     * @param watermelons - eaten watermelons
     */
    public void setWatermelons(double watermelons) {
        if (watermelons <= watermelonsGoal) {
            this.watermelons = watermelons;
        }
    }

    /**
     * Method to set current number of eaten apples.
     *
     * @param apples - eaten apples
     */
    public void setApples(double apples) {
        if (apples <= applesGoal) {
            this.apples = apples;
        }
    }

    /**
     * Method to set current number of eaten lemons.
     *
     * @param lemons - eaten lemons
     */
    public void setLemons(double lemons) {
        if (lemons <= lemonsGoal) {
            this.lemons = lemons;
        }
    }

    public double getWatermelons() {
        return watermelons;
    }

    public double getApples() {
        return apples;
    }

    public double getLemons() {
        return lemons;
    }

    /**
     * Method that draws score panel.
     *
     * @param windowWidth - window width
     * @param windowHeight - window height
     */
    public void draw(double windowWidth, double windowHeight) {
        gridPane.setTranslateX(windowWidth * 0.05);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.setWidth(windowWidth);
        canvas.setHeight(windowHeight);

        gc.setFill(Color.web("#757780"));
        gc.fillRect(0, 0, windowWidth, windowHeight);

        column.setPrefWidth(windowWidth * 0.9);

        pbWatermelons.setPrefWidth(windowWidth * 0.75);
        pbApples.setPrefWidth(windowWidth * 0.75);
        pbLemons.setPrefWidth(windowWidth * 0.75);

        labelWatermelons.setPrefWidth(windowWidth * 0.75);
        labelApples.setPrefWidth(windowWidth * 0.75);
        labelLemons.setPrefWidth(windowWidth * 0.75);

        pbWatermelons.setProgress(watermelons / watermelonsGoal);
        pbApples.setProgress(apples / applesGoal);
        pbLemons.setProgress(lemons / lemonsGoal);
    }
}
