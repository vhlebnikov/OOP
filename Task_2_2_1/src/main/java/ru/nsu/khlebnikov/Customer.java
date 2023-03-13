package ru.nsu.khlebnikov;

import java.util.concurrent.Callable;

public record Customer(String name) implements Callable<Void> {
    private void makeOrder() {
        Order order = new Order(this);
        Pizzeria.putToQueue(order);
    }

    @Override
    public Void call() {
        makeOrder();
        return null;
    }
}
