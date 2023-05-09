package ru.nsu.khlebnikov;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Food {
    private List<FoodItem> food;
    private int watermelonsGoal;
    private int applesGoal;
    private int lemonsGoal;

    public Food(int watermelonsGoal, int applesGoal, int lemonsGoal, List<Point> walls, int widthCells, int heightCells) {
        food = new ArrayList<>();
        this.watermelonsGoal = watermelonsGoal;
        this.applesGoal = applesGoal;
        this.lemonsGoal = lemonsGoal;

        generateFood(watermelonsGoal, FoodItem.FoodType.WATERMELON, walls, widthCells, heightCells);
        generateFood(applesGoal, FoodItem.FoodType.APPLE, walls, widthCells, heightCells);
        generateFood(lemonsGoal, FoodItem.FoodType.LEMON, walls, widthCells, heightCells);
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

    public void regenerateFood(List<Point> walls, int widthCells, int heightCells) {
        food.clear();

        generateFood(watermelonsGoal, FoodItem.FoodType.WATERMELON, walls, widthCells, heightCells);
        generateFood(applesGoal, FoodItem.FoodType.APPLE, walls, widthCells, heightCells);
        generateFood(lemonsGoal, FoodItem.FoodType.LEMON, walls, widthCells, heightCells);
    }

    public void removeFood(FoodItem foodItem) {
        food.remove(foodItem);
    }

    public List<FoodItem> getFood() {
        return food;
    }
}
