package ru.nsu.khlebnikov;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Baker class representing himself and his task.
 */
public class Baker implements Callable<Void> {
    private final String name;
    private final int experience;
    private boolean isWorking;
    private Order order;

    /**
     * Constructor for baker.
     *
     * @param name - baker's name
     * @param experience - baker's experience
     */
    public Baker(String name, int experience) {
        this.name = name;
        this.experience = experience;
        this.isWorking = false;
    }

    /**
     * Task of baker: takes order from the queue then cooks pizza and transfers it to the storage.
     * If queue is empty waits until order becomes available.
     * If storage is full waits until it becomes free.
     *
     * @return - if was interrupted
     */
    private boolean takeOrder() {
        try {
            order = Pizzeria.takeFromQueue();
            isWorking = true;
            order.setStatus(Order.Status.Cooking);
            System.out.println(this.name + "'s cooking " + order);
            TimeUnit.SECONDS.sleep(10 - experience);
            order.setStatus(Order.Status.PizzaIsDone);
            Pizzeria.putToStorage(order);
            order.setStatus(Order.Status.DeliveredToStorage);
            isWorking = false;
            return false;
        } catch (InterruptedException e) {
            System.err.println("Baker " + this.name + " was interrupted");
            if (isWorking) {
                if (order.getStatus().equals(Order.Status.Cooking)) {
                    order.setStatus(Order.Status.PizzaIsDoneInHurry);
                }
                Pizzeria.addToStorage(order);
                order.setStatus(Order.Status.DeliveredToStorage);
                isWorking = false;
            }
            return true;
        }
    }

    @Override
    public Void call() {
        while (true) {
            if (takeOrder()) {
                return null;
            }
        }
    }
}
