package ru.nsu.khlebnikov.model.food;

import java.awt.Point;

/**
 * Class of one fruit.
 *
 * @param foodType - type of fruit
 * @param point - coordinate of the food on the game field
 */
public record FoodItem(FoodItem.FoodType foodType, Point point) {
    /**
     * Enum for the food type.
     */
    public enum FoodType {
        WATERMELON, APPLE, LEMON
    }
}
