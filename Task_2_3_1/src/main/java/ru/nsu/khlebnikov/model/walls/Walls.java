package ru.nsu.khlebnikov.model.walls;

import java.awt.Point;
import java.util.List;

/**
 * Class of the walls.
 */
public class Walls {
    private List<Point> coordinates;

    public Walls(List<Point> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Point> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Point> coordinates) {
        this.coordinates = coordinates;
    }
}
