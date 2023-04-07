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
    private boolean isWorking = false;

    public Deliveryman(String name, int bagCapacity) {
        this.name = name;
        this.bagCapacity = bagCapacity;
        this.orders = new LinkedBlockingQueue<>(bagCapacity);
    }

    /**
     * Task of deliveryman: takes random number of orders [from 1 to his bag capacity].
     * Then sequentially delivers orders. If storage is empty waits until pizzas becomes available.
     *
     * @return - if was interrupted
     */
    private boolean deliver() { // изменить наверняка, чтобы при Interrupted (возможно тупо присвоении переменной) они довозили всё что у них есть и всё
        int numberOfOrders = (int) Math.floor(Math.random() * bagCapacity + 1);
        orders.addAll(Pizzeria.takeFromStorage(numberOfOrders));
        try {
            isWorking = true;
            for (Order order : orders) {
                order.setStatus(Order.Status.Delivery);
                System.out.println(this.name + "'s delivering " + order);
                TimeUnit.SECONDS.sleep((int) (Math.random() * 5));
                order.setStatus(Order.Status.Done);
            }
            orders.clear();
            isWorking = false;
            return false;
        } catch (InterruptedException e) {
            return true;
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