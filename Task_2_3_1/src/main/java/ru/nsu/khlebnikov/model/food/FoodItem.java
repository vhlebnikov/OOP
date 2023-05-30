package ru.nsu.khlebnikov.model.food;

import java.awt.Point;

public record FoodItem(FoodItem.FoodType foodType, Point point) {
    public enum FoodType {
        WATERMELON, APPLE, LEMON
    }
}
