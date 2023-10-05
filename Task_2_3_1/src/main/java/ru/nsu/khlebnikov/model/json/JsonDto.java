package ru.nsu.khlebnikov.model.json;

import java.awt.Point;
import java.util.List;

/**
 * Data transfer object that will be returned from json config file.
 *
 * @param widthCells - number of cells by width
 * @param heightCells - number of cells by height
 * @param initSnakeSpeed - initial snake speed
 * @param initSnakeGrowthSpeed - initial snake growth speed
 * @param initWatermelons - initial number of watermelons
 * @param initApples - initial number of apples
 * @param initLemons - initial number of lemons
 * @param watermelonsGoal - initial watermelons goal
 * @param applesGoal - initial apples goal
 * @param lemonsGoal - - initial lemons goal
 * @param walls - list of walls coordinates
 */
public record JsonDto(int widthCells, int heightCells, double initSnakeSpeed,
                      double initSnakeGrowthSpeed, int initWatermelons, int initApples,
                      int initLemons, int watermelonsGoal,
                      int applesGoal, int lemonsGoal, List<Point> walls) {
}