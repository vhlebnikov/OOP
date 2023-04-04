package ru.nsu.khlebnikov;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Baker class representing himself and his task.
 */
public class Baker implements Callable<Void> {
    private final String name;
    private final int experience;
    private volatile boolean isInterrupted = false;

    public Baker(String name, int experience) {
        this.name = name;
        this.experience = experience;
    }

    public void setInterrupted(boolean interrupted) {
        isInterrupted = interrupted;
    }

    public boolean isInterrupted() {
        return isInterrupted;
    }

    /**
     * Task of baker: takes order from the queue then cooks pizza and transfers it to the storage.
     * If queue is empty waits until order becomes available.
     * If storage is full waits until it becomes free.
     *
     * @throws InterruptedException - if interrupted while waiting
     */
    private void takeOrder() throws InterruptedException {
        if (isInterrupted && Pizzeria.getOrderQueueSize() == 0) { // нужно сделать чтобы он сразу выходил отсюда если его прервали
            Thread.currentThread().interrupt();
            System.err.println("Interrupted baker " + this.name);
        }
        Order order = Pizzeria.takeFromQueue(); // тут выйдет - будет здорово
        order.setStatus(Order.Status.Cooking);
        System.out.println(this.name + "'s cooking " + order);
        TimeUnit.SECONDS.sleep(10 - experience); // тут не круто
        order.setStatus(Order.Status.PizzaIsDone);
        Pizzeria.putToStorage(order); // тут тоже надо дождаться всё же
        order.setStatus(Order.Status.DeliveredToStorage);
        if (isInterrupted) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted baker " + this.name);
        }
    }

    @Override
    public Void call() throws InterruptedException {
        while (true) {
            if (Thread.interrupted()) {
                return null;
            }
            takeOrder();
        }
    }
}
