package ru.nsu.khlebnikov.model.food;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Food {
    private final List<FoodItem> food;
    private final int initWatermelons;
    private final int initApples;
    private final int initLemons;

    public Food(int initWatermelons, int initApples, int initLemons, List<Point> walls, List<Point> snake, int widthCells, int heightCells) {
        food = new ArrayList<>();
        this.initWatermelons = initWatermelons;
        this.initApples = initApples;
        this.initLemons = initLemons;

        generateFood(initWatermelons, FoodItem.FoodType.WATERMELON, walls, snake, widthCells, heightCells);
        generateFood(initApples, FoodItem.FoodType.APPLE, walls, snake, widthCells, heightCells);
        generateFood(initLemons, FoodItem.FoodType.LEMON, walls, snake, widthCells, heightCells);
    }

    public void generateFood(int count, FoodItem.FoodType foodType, List<Point> walls, List<Point> snake, int widthCells, int heightCells) {
        for (int i = 0; i < count; i++) {
            Point point = new Point((int) (Math.random() * widthCells), (int) (Math.random() * heightCells));
            while (food.stream().anyMatch(f -> f.point().x == point.x && f.point().y == point.y)
                    || walls.stream().anyMatch(w -> w.x == point.x && w.y == point.y)
                    || snake.stream().anyMatch(s -> s.x == point.x && s.y == point.y)) {
                point.setLocation((int) (Math.random() * widthCells), (int) (Math.random() * heightCells));
            }
            food.add(new FoodItem(foodType, point));
        }
    }

    public void regenerateFood(List<Point> walls, List<Point> snake, int widthCells, int heightCells) {
        food.clear();

        generateFood(initWatermelons, FoodItem.FoodType.WATERMELON, walls, snake, widthCells, heightCells);
        generateFood(initApples, FoodItem.FoodType.APPLE, walls, snake, widthCells, heightCells);
        generateFood(initLemons, FoodItem.FoodType.LEMON, walls, snake, widthCells, heightCells);
    }

    public void removeFood(FoodItem foodItem) {
        food.remove(foodItem);
    }

    public List<FoodItem> getFood() {
        return food;
    }
}
