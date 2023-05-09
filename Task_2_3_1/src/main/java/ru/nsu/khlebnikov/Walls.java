package ru.nsu.khlebnikov;

import java.awt.Point;
import java.util.List;

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
