package ru.nsu.khlebnikov;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private ArrayList<Point> snake;
    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }
    private Direction direction;

    public Snake(Point head) {
        snake = new ArrayList<>(List.of(head));
        direction = Direction.LEFT;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Point getHead() {
        return snake.get(0);
    }

    public Point getTail() {
        return snake.get(snake.size() - 1);
    }

    public List<Point> getBody() {
        return snake.subList(1, snake.size() - 1);
    }

    public int getOrientation(Point node) {
        int nodeIndex = snake.indexOf(node);

        Point prevNeighbor = snake.get(nodeIndex - 1);
        Point nextNeighbor = snake.get(nodeIndex + 1);

        if (prevNeighbor.y == nextNeighbor.y) {
            return 1; // horizontally
        }
        if (prevNeighbor.x == nextNeighbor.x) {
            return 2; // vertically
        }
        // rotate
    }
}
