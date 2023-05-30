package ru.nsu.khlebnikov.model.snake;

import ru.nsu.khlebnikov.Main;
import ru.nsu.khlebnikov.model.food.Food;
import ru.nsu.khlebnikov.model.food.FoodItem;
import ru.nsu.khlebnikov.model.walls.Walls;
import ru.nsu.khlebnikov.view.Score;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private ArrayList<Point> snake;

    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    private FoodItem foodTarget = null;

    private Direction direction;
    private double speed;
    private double growthSpeed;

    public enum BodyOrientation {
        HORIZONTALLY, VERTICALLY, ROTATED
    }

    public Snake(Point head, double speed, double growthSpeed) {
        snake = new ArrayList<>(List.of(head));
        direction = Direction.LEFT;
        this.speed = speed;
        this.growthSpeed = growthSpeed;
    }

    public Snake(List<Point> body, double speed, double growthSpeed) {
        snake = new ArrayList<>(body);
        direction = Direction.LEFT;
        this.speed = speed;
        this.growthSpeed = growthSpeed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public double getGrowthSpeed() {
        return growthSpeed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public ArrayList<Point> getSnake() {
        return snake;
    }

    public void setSnake(List<Point> newSnake) {
        snake = new ArrayList<>(newSnake);
    }

    public void cutSnake(Point part) {
        this.setSnake(this.getSnake().subList(0, this.getSnake().indexOf(part)));
    }

    public int getSize() {
        return snake.size();
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

    public void addNode(Point node) {
        this.snake.add(node);
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

        int x = prevNeighbor.x + nextNeighbor.x - node.x * 2;
        int y = prevNeighbor.y + nextNeighbor.y - node.y * 2;

        if (y < -1) {
            y = 1;
        }
        if (y > 1) {
            y = -1;
        }
        if (x < -1) {
            x = 1;
        }
        if (x > 1) {
            x = -1;
        }
        int res = 2 * x + y;
        switch (res) {
            case -1 -> {    // 00   180 deg
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
            if (y == 1 || y < -1) {
                return 180; // DOWN
            } else if (y == -1 || y > 1) {
                return 0; // UP
            }
        }
        if (y == 0) {
            if (x == 1 || x < -1) {
                return 90; // RIGHT
            } else if (x == -1 || x > 1) {
                return 270; // LEFT
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

    public void update(Food food, Walls walls, Score score, Main main, int widthCells, int heightCells) {
        Point head = new Point(this.getHead());

        switch (this.getDirection()) {
            case UP -> head.setLocation(head.x, head.y - 1);
            case DOWN -> head.setLocation(head.x, head.y + 1);
            case LEFT -> head.setLocation(head.x - 1, head.y);
            case RIGHT -> head.setLocation(head.x + 1, head.y);
        }

        if (head.x < 0) {
            head.setLocation(widthCells - 1, head.y);
        }
        if (head.y < 0) {
            head.setLocation(head.x, heightCells - 1);
        }
        if (head.x >= widthCells) {
            head.setLocation(0, head.y);
        }
        if (head.y >= heightCells) {
            head.setLocation(head.x, 0);
        }

        List<FoodItem> eatenFood = food.getFood().stream().filter(f -> f.point().equals(head)).toList();

        if (!eatenFood.isEmpty()) {
            Point newTail = new Point(this.getTail());
            this.addNode(newTail);
            food.removeFood(eatenFood.get(0));
            food.generateFood(1, eatenFood.get(0).foodType(), walls.getCoordinates(), this.getSnake(), widthCells, heightCells);
            switch (eatenFood.get(0).foodType()) {
                case WATERMELON -> score.setWatermelons(score.getWatermelons() + 1);
                case APPLE -> score.setApples(score.getApples() + 1);
                case LEMON -> score.setLemons(score.getLemons() + 1);
            }
            this.setSpeed(this.getSpeed() + this.getGrowthSpeed());
        }

        if (this.getSize() > 2 && (this.getBody().stream().anyMatch(x -> x.equals(head))
                || this.getTail().equals(head))
                || walls.getCoordinates().stream().anyMatch(w -> w.equals(head))) {
            main.gameOver();
            return;
        }

        if (score.getTotalScore() == score.getTotalGoal()) {
            main.gameWin();
            return;
        }

        for (Point part : this.getSnake()) {
            Point temp = new Point(part);
            part.setLocation(head);
            head.setLocation(temp);
        }
    }

    public void hunterBotUpdate(Snake player, Walls walls, Main main) {
        Point playerHead = new Point(player.getHead());
        Point botHead = new Point(this.getHead());

        if (Math.random() > 0.5) {
            if (playerHead.x > botHead.x && walls.getCoordinates().stream().noneMatch(w -> w.x == botHead.x + 1 && w.y == botHead.y)) {
                botHead.setLocation(botHead.x + 1, botHead.y);
                this.setDirection(Direction.RIGHT);
            } else if (playerHead.x < botHead.x && walls.getCoordinates().stream().noneMatch(w -> w.x == botHead.x - 1 && w.y == botHead.y)) {
                botHead.setLocation(botHead.x - 1, botHead.y);
                this.setDirection(Direction.LEFT);
            }
        } else {
            if (playerHead.y > botHead.y && walls.getCoordinates().stream().noneMatch(w -> w.x == botHead.x && w.y == botHead.y + 1)) {
                botHead.setLocation(botHead.x, botHead.y + 1);
                this.setDirection(Direction.DOWN);
            } else if (playerHead.y < botHead.y && walls.getCoordinates().stream().noneMatch(w -> w.x == botHead.x && w.y == botHead.y - 1)) {
                botHead.setLocation(botHead.x, botHead.y - 1);
                this.setDirection(Direction.UP);
            }
        }

        for (Point s : player.getSnake()) {
            if (s.equals(botHead)) {
                player.cutSnake(s);
                if (player.getSnake().size() == 0) {
                    main.gameOver();
                    return;
                }
            }
        }

        if (!botHead.equals(this.getHead())) {
            for (Point part : this.getSnake()) {
                Point temp = new Point(part);
                part.setLocation(botHead);
                botHead.setLocation(temp);
            }
        }
    }

    public void gluttonBotUpdate(Walls walls, Food food, Snake player, int widthCells, int heightCells) {
        Point botHead = new Point(this.getHead());

        if (foodTarget == null) {
            foodTarget = food.getFood().get((int) (Math.random() * food.getFood().size()));
        }

        if (Math.random() > 0.5) {
            if (foodTarget.point().x > botHead.x && walls.getCoordinates().stream().noneMatch(w -> w.x == botHead.x + 1 && w.y == botHead.y)) {
                botHead.setLocation(botHead.x + 1, botHead.y);
                this.setDirection(Direction.RIGHT);
            } else if (foodTarget.point().x < botHead.x && walls.getCoordinates().stream().noneMatch(w -> w.x == botHead.x - 1 && w.y == botHead.y)) {
                botHead.setLocation(botHead.x - 1, botHead.y);
                this.setDirection(Direction.LEFT);
            }
        } else {
            if (foodTarget.point().y > botHead.y && walls.getCoordinates().stream().noneMatch(w -> w.x == botHead.x && w.y == botHead.y + 1)) {
                botHead.setLocation(botHead.x, botHead.y + 1);
                this.setDirection(Direction.DOWN);
            } else if (foodTarget.point().y < botHead.y && walls.getCoordinates().stream().noneMatch(w -> w.x == botHead.x && w.y == botHead.y - 1)) {
                botHead.setLocation(botHead.x, botHead.y - 1);
                this.setDirection(Direction.UP);
            }
        }

        if (foodTarget.point().equals(botHead)) {
            food.removeFood(foodTarget);
            food.generateFood(1, foodTarget.foodType(), walls.getCoordinates(), player.getSnake(), widthCells, heightCells);
            foodTarget = null;
        }

        if (!botHead.equals(this.getHead())) {
            for (Point part : this.getSnake()) {
                Point temp = new Point(part);
                part.setLocation(botHead);
                botHead.setLocation(temp);
            }
        }
    }
}
