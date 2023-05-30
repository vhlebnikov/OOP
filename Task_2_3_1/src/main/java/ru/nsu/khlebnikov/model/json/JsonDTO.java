package ru.nsu.khlebnikov.model.json;

import java.awt.Point;
import java.util.List;

public record JsonDTO(int widthCells, int heightCells, double initSnakeSpeed, double initSnakeGrowthSpeed,
                      int initWatermelons, int initApples, int initLemons, int watermelonsGoal,
                      int applesGoal, int lemonsGoal, List<Point> walls) {
}