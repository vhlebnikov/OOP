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
    public enum BodyOrientation {
        HORIZONTALLY, VERTICALLY, ROTATED
    }

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

    public void setSnake(ArrayList<Point> snake) {
        this.snake.addAll(snake);
    }

    public BodyOrientation getBodyOrientation(Point node) {
        int nodeIndex = snake.indexOf(node);

        Point prevNeighbor = snake.get(nodeIndex - 1);
        Point nextNeighbor = snake.get(nodeIndex + 1);

        if (prevNeighbor.y == nextNeighbor.y) {
            return BodyOrientation.HORIZONTALLY;
        }
        if (prevNeighbor.x == nextNeighbor.x) {
            return BodyOrientation.VERTICALLY;
        }
        return BodyOrientation.ROTATED;
    }

    public int getBodyAngle(Point node) {
        int nodeIndex = snake.indexOf(node);

        Point prevNeighbor = snake.get(nodeIndex - 1);
        Point nextNeighbor = snake.get(nodeIndex + 1);

        int res = (prevNeighbor.x + nextNeighbor.x - node.x * 2) * 2 + prevNeighbor.y + nextNeighbor.y - node.y * 2;
        switch (res) {
            case (-1) -> {  // 00   180 deg
                return 180; //  0
            }
            case (1) -> {  // 0    0 deg
                return 0;  // 00
            }
            case (-3) -> {  //  0   270 deg
                return 270; // 00
            }
            case (3) -> {  // 00   90 deg
                return 90; // 0
            }
        }
        throw new IllegalStateException("Wrong body angle");
    }

    public double getTailAngle() {
        Point tail = getTail();

        Point prevElement = snake.get(snake.size() - 2);

        int x = prevElement.x - tail.x;
        int y = prevElement.y - tail.y;
        if (x == 0) {
            if (y == 1) {
                return 0;
            } else if (y == -1) {
                return 180;
            }
        }
        if (y == 0) {
            if (x == 1) {
                return 90;
            } else if (x == -1) {
                return 270;
            }
        }
        throw new IllegalStateException("Wrong tail angle");
    }

    public double getHeadAngle() {
        switch (direction) {
            case UP -> {
                return 180;
            }
            case DOWN -> {
                return 0;
            }
            case LEFT -> {
                return 90;
            }
            case RIGHT -> {
                return 270;
            }
        }
        throw new IllegalStateException("Wrong head angle");
    }
}
