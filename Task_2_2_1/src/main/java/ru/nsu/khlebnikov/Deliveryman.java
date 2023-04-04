package ru.nsu.khlebnikov;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Deliveryman class representing himself and his task.
 */
public class Deliveryman implements Callable<Void> {
    private final String name;
    private final BlockingQueue<Order> orders;
    private final int bagCapacity;
    private volatile boolean isInterrupted = false;

    /**
     * Constructor from deliveryman.
     *
     * @param name - his name
     * @param bagCapacity - his bag capacity
     */
    public Deliveryman(String name, int bagCapacity) {
        this.name = name;
        this.bagCapacity = bagCapacity;
        this.orders = new LinkedBlockingQueue<>(bagCapacity);
    }

    public void setInterrupted(boolean interrupted) {
        isInterrupted = interrupted;
    }

    /**
     * Task of deliveryman: takes random number of orders [from 1 to his bag capacity].
     * Then sequentially delivers orders. If storage is empty waits until pizzas becomes available.
     *
     * @throws InterruptedException - if interrupted while waiting
     */
    private void deliver() throws InterruptedException {
        int numberOfOrders = (int) Math.floor(Math.random() * bagCapacity + 1); // тут они при прерывании должны выгружать все заказы со склада
        orders.addAll(Pizzeria.takeFromStorage(numberOfOrders));                // иначе работают в штатном режиме
        for (Order order : orders) {
            order.setStatus(Order.Status.Delivery);
            System.out.println(this.name + "'s delivering " + order);
            TimeUnit.SECONDS.sleep((int) (Math.random() * 5));
            order.setStatus(Order.Status.Done);
        }
        orders.clear();
        if (isInterrupted) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted deliveryman " + this.name);
        }
    }

    @Override
    public Void call() throws Exception {
        while (true) {
            if (Thread.interrupted()) {
                return null;
            }
            deliver();
        }
    }
}
