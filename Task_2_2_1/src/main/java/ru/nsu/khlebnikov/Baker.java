package ru.nsu.khlebnikov;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Baker class representing himself and his task.
 */
public class Baker implements Callable<Void> {
    private final String name;
    private final int experience;
    private boolean isWorking = false;

    public Baker(String name, int experience) {
        this.name = name;
        this.experience = experience;
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    /**
     * Task of baker: takes order from the queue then cooks pizza and transfers it to the storage.
     * If queue is empty waits until order becomes available.
     * If storage is full waits until it becomes free.
     *
     * @throws InterruptedException - if interrupted while waiting
     */
    private void takeOrder() throws InterruptedException {
        Order order = Pizzeria.takeFromQueue();
        isWorking = true;
        order.setStatus(Order.Status.Cooking);
        System.out.println(this.name + "'s cooking " + order);
        TimeUnit.SECONDS.sleep(10 - experience);
        order.setStatus(Order.Status.PizzaIsDone);
        Pizzeria.putToStorage(order);
        order.setStatus(Order.Status.DeliveredToStorage);
        isWorking = false;
    }

    @Override
    public Void call() throws Exception {
        while (true) {
            if (Thread.interrupted()) {
                return null;
            }
            takeOrder();
        }
    }
}
