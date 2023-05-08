package ru.nsu.khlebnikov;

import java.awt.Point;

public class FoodItem {
    public enum FoodType {
        WATERMELON, APPLE, LEMON
    }
    private final FoodType foodType;
    private final Point point;

    public FoodItem(FoodType foodType, Point point) {
        this.foodType = foodType;
        this.point = point;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public Point getPoint() {
        return point;
    }
}
