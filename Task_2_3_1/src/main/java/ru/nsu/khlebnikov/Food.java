package ru.nsu.khlebnikov;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Food {
    private List<FoodItem> food;

    public Food(int watermelonsCount, int applesCount, int lemonsCount, List<Point> walls, int widthCells, int heightCells) {
        food = new ArrayList<>();

        generateFood(watermelonsCount, FoodItem.FoodType.WATERMELON, walls, widthCells, heightCells);
        generateFood(applesCount, FoodItem.FoodType.APPLE, walls, widthCells, heightCells);
        generateFood(lemonsCount, FoodItem.FoodType.LEMON, walls, widthCells, heightCells);
    }

    public void generateFood(int count, FoodItem.FoodType foodType, List<Point> walls, int widthCells, int heightCells) {
        for (int i = 0; i < count; i++) {
            Point point = new Point((int) (Math.random() * widthCells), (int) (Math.random() * heightCells));
            while (food.stream().anyMatch(f -> f.getPoint().x == point.x && f.getPoint().y == point.y)
                    || walls.stream().anyMatch(w -> w.x == point.x && w.y == point.y)) {
                point.setLocation((int) (Math.random() * widthCells), (int) (Math.random() * heightCells));
            }
            food.add(new FoodItem(foodType, point));
        }
    }

    public List<FoodItem> getFood() {
        return food;
    }
}
