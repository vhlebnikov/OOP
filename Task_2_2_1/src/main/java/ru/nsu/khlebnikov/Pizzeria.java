package ru.nsu.khlebnikov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Pizzeria {
    private static volatile BlockingQueue<Order> orderQueue;
    private static volatile BlockingDeque<Order> storage;
    private final List<Baker> bakers;
    private final ExecutorService bakersPool;
    private final ExecutorService deliverymenPool;
    private final List<Deliveryman> deliverymen;

    public Pizzeria(String fileName) throws IOException {
        JsonData jsonData = new JsonData(fileName);
        storage = new LinkedBlockingDeque<>(jsonData.getStorageCapacity());
        orderQueue = new LinkedBlockingQueue<>();
        this.bakers = jsonData.getBakers();
        this.deliverymen = jsonData.getDeliverymen();
        bakersPool = Executors.newFixedThreadPool(bakers.size());
        deliverymenPool = Executors.newFixedThreadPool(deliverymen.size());
    }

    public void start() {
        bakers.forEach(bakersPool::submit);
        deliverymen.forEach(deliverymenPool::submit);
        bakersPool.shutdown();
        deliverymenPool.shutdown();
    }

    public void stop(long seconds) {
        boolean notWork = bakers.stream().noneMatch(Baker::isWorking)
                && deliverymen.stream().noneMatch(Deliveryman::isWorking);
        long start = System.nanoTime();
        while (true) {
            if ((System.nanoTime() - start) >= TimeUnit.SECONDS.toNanos(seconds)) {
                if (bakers.stream().noneMatch(Baker::isWorking)
                        && deliverymen.stream().noneMatch(Deliveryman::isWorking) && notWork) {
                    System.out.println("Shutdown");
                    bakersPool.shutdownNow();
                    deliverymenPool.shutdownNow();
                    return;
                } else {
                    notWork = bakers.stream().noneMatch(Baker::isWorking)
                            && deliverymen.stream().noneMatch(Deliveryman::isWorking);
                    start = System.nanoTime();
                }
            }
        }
    }

    protected static void putToQueue(Order order) {
        try {
            orderQueue.put(order);
//            System.out.println("queue: " + orderQueue);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected static Order takeFromQueue() {
        try {
//            System.out.println("queue: " + orderQueue);
            return orderQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void putToDeque(Order order) {
        try {
            storage.put(order);
//            System.out.println("storage: " + storage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected static List<Order> takeFromDeque(int number) {
        List<Order> orders = new ArrayList<>();
        try {
            if (storage.size() < number) {
                number = storage.size();
            }
            for (int i = 0; i < number; i++) {
                orders.add(storage.take());
//                System.out.println("storage: " + storage);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
}
