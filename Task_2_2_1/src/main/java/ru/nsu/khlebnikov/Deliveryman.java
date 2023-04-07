package ru.nsu.khlebnikov;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Deliveryman class representing himself and his task.
 */
public class Deliveryman implements Callable<Void> {
    private final String name;
    private final Queue<Order> orders;
    private final int bagCapacity;

    public Deliveryman(String name, int bagCapacity) {
        this.name = name;
        this.bagCapacity = bagCapacity;
        this.orders = new ArrayDeque<>();
    }

    /**
     * Task of deliveryman: takes random number of orders [from 1 to his bag capacity].
     * Then sequentially delivers orders. If storage is empty waits until pizzas becomes available.
     *
     * @return - if was interrupted
     */
    private boolean deliver() {
        Order order = null;
        try {
            Pizzeria.takeFromStorage(bagCapacity, orders);
            while (!orders.isEmpty()) {
                order = orders.poll();
                order.setStatus(Order.Status.Delivery);
                System.out.println(this.name + "'s delivering " + order);
                TimeUnit.SECONDS.sleep((int) (Math.random() * 5));
                order.setStatus(Order.Status.Done);
            }

            return false;
        } catch (InterruptedException e) {
            System.err.println("Deliveryman " + this.name + " was interrupted");
            if (order != null && order.getStatus().equals(Order.Status.Delivery)) {
                order.setStatus(Order.Status.DeliveryInHurry);
                order.setStatus(Order.Status.Done);
            }
            Pizzeria.drainStorage(bagCapacity, orders);
            while (!orders.isEmpty()) {
                order = orders.poll();
                order.setStatus(Order.Status.DeliveryInHurry);
                System.out.println(this.name + "'s delivering " + order);
                order.setStatus(Order.Status.Done);
            }

            return true;
        }
    }

    @Override
    public Void call() {
        while (true) {
            if (deliver()) {
                return null;
            }
        }
    }
}