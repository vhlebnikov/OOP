package ru.nsu.khlebnikov;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Food {
    private List<Point> watermelonsCoordinates;
    private List<Point> applesCoordinates;
    private List<Point> lemonsCoordinates;
    public Food(int watermelonsCount, int applesCount, int lemonsCount, List<Point> walls, int widthCells, int heightCells) {
        watermelonsCoordinates = new ArrayList<>();
        applesCoordinates = new ArrayList<>();
        lemonsCoordinates = new ArrayList<>();

        List<Double> x = new ArrayList<>(IntStream.rangeClosed(0, widthCells - 1).asDoubleStream().boxed().toList());
        x.removeAll(walls.stream().map(Point::getX).toList());
        List<Double> y = new ArrayList<>(IntStream.rangeClosed(0, heightCells - 1).asDoubleStream().boxed().toList());
        y.removeAll(walls.stream().map(Point::getY).toList());

        
    }
    public static void main(String[] args) {
        Food food = new Food(5,6,7,new ArrayList<>(List.of(
                new Point(1,2), new Point(3,4)
        )), 20, 20);
    }
}
