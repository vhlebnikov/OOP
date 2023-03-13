package ru.nsu.khlebnikov;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

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

    private void takeOrder() throws InterruptedException {
        Order order = Pizzeria.takeFromQueue();
        isWorking = true;
        order.setStatus(Order.Status.Cooking);
//        System.out.println(this.name + "'s cooking order of " + order);
        TimeUnit.SECONDS.sleep(10 - experience);
        order.setStatus(Order.Status.PizzaIsDone);
        Pizzeria.putToDeque(order);
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
