package ru.nsu.khlebnikov;

import java.util.concurrent.Callable;

public record Customer(String name) implements Callable<Void> {
    private void makeOrder() throws InterruptedException {
        Order order = new Order(this);
        Pizzeria.putToQueue(order);
    }

    @Override
    public Void call() throws InterruptedException {
        makeOrder();
        return null;
    }
}
