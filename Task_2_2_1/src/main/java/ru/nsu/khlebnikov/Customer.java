package ru.nsu.khlebnikov;

import java.util.concurrent.Callable;

/**
 * Customer record representing himself and his task.
 *
 * @param name - name of customer
 */
public record Customer(String name) implements Callable<Void> {
    /**
     * Task of customer: creates new order and then puts it to the order queue.
     *
     * @throws InterruptedException - if interrupted while waiting
     */
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
