package ru.nsu.khlebnikov.model.food;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Class of food that contains certain number of each fruit.
 */
public class Food {
    private final List<FoodItem> food;
    private final int initWatermelons;
    private final int initApples;
    private final int initLemons;

    /**
     * Food constructor.
     *
     * @param initWatermelons - initial number of watermelons that spawn on start of the game
     * @param initApples - initial number of apples that spawn on start of the game
     * @param initLemons - initial number of lemons that spawn on start of the game
     * @param walls - walls object in order not ot spawn food on the walls
     * @param snake - snake object in order not to spawn food on the snake
     * @param widthCells - number of cells by width
     * @param heightCells - number of cells by height
     */
    public Food(int initWatermelons, int initApples, int initLemons,
                List<Point> walls, List<Point> snake, int widthCells, int heightCells) {
        food = new ArrayList<>();
        this.initWatermelons = initWatermelons;
        this.initApples = initApples;
        this.initLemons = initLemons;

        generateFood(initWatermelons, FoodItem.FoodType.WATERMELON,
                walls, snake, widthCells, heightCells);
        generateFood(initApples, FoodItem.FoodType.APPLE, walls, snake, widthCells, heightCells);
        generateFood(initLemons, FoodItem.FoodType.LEMON, walls, snake, widthCells, heightCells);
    }

    /**
     * Method to generate food on the game field.
     *
     * @param count - how much food we need to spawn
     * @param foodType - type of food to spawn
     * @param walls - walls object
     * @param snake - snake object
     * @param widthCells - number of cells by width
     * @param heightCells - number of cells by height
     */
    public void generateFood(int count, FoodItem.FoodType foodType, List<Point> walls,
                             List<Point> snake,int widthCells, int heightCells) {
        for (int i = 0; i < count; i++) {
            Point point = new Point((int) (Math.random() * widthCells),
                    (int) (Math.random() * heightCells));
            while (food.stream().anyMatch(f -> f.point().x == point.x && f.point().y == point.y)
                    || walls.stream().anyMatch(w -> w.x == point.x && w.y == point.y)
                    || snake.stream().anyMatch(s -> s.x == point.x && s.y == point.y)) {
                point.setLocation((int) (Math.random() * widthCells),
                        (int) (Math.random() * heightCells));
            }
            food.add(new FoodItem(foodType, point));
        }
    }

    /**
     * Method to regenerate food on the game field with certain number of fruits.
     *
     * @param walls - walls object
     * @param snake - snake object
     * @param widthCells - number of cells by width
     * @param heightCells - number of cells by height
     */
    public void regenerateFood(List<Point> walls, List<Point> snake,
                               int widthCells, int heightCells) {
        food.clear();

        generateFood(initWatermelons, FoodItem.FoodType.WATERMELON, walls,
                snake, widthCells, heightCells);
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
