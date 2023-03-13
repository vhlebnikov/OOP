package ru.nsu.khlebnikov;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Baker implements Callable<Void> {
    private final String name;
    private final int experience;

    public Baker(String name, int experience) {
        this.name = name;
        this.experience = experience;
    }

    private void takeOrder() throws InterruptedException {
        Order order = Pizzeria.takeFromQueue();
        order.setStatus(Order.Status.Cooking);
//        System.out.println(this.name + "'s cooking order of " + order);
        TimeUnit.SECONDS.sleep(10 - experience);
        order.setStatus(Order.Status.PizzaIsDone);
        Pizzeria.putToDeque(order);
        order.setStatus(Order.Status.DeliveredToStorage);
    }

    @Override
    public Void call() throws Exception {
        while (true) {
            takeOrder();
        }
    }
}
