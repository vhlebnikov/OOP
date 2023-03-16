package ru.nsu.khlebnikov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Pizzeria class that where the management of the pizzeria employees are managed.
 */
public class Pizzeria {
    private static volatile BlockingQueue<Order> orderQueue;
    private static volatile BlockingQueue<Order> storage;
    private final List<Baker> bakers;
    private final ExecutorService bakersPool;
    private final ExecutorService deliverymenPool;
    private final List<Deliveryman> deliverymen;

    /**
     * Constructor for pizzeria. Get data from json file and fills fields of this class.
     *
     * @param fileName - json filename
     * @throws IOException - if I/O error occurs
     */
    public Pizzeria(String fileName) throws IOException {
        JsonData jsonData = new JsonData(fileName);
        storage = new LinkedBlockingQueue<>(jsonData.getStorageCapacity());
        orderQueue = new LinkedBlockingQueue<>();
        this.bakers = jsonData.getBakers();
        this.deliverymen = jsonData.getDeliverymen();
        bakersPool = Executors.newFixedThreadPool(bakers.size());
        deliverymenPool = Executors.newFixedThreadPool(deliverymen.size());
    }

    /**
     * Method that starts pizzeria, more precisely, it starts the work of bakers and deliverymen.
     */
    public void start() {
        bakers.forEach(bakersPool::submit);
        deliverymen.forEach(deliverymenPool::submit);
        bakersPool.shutdown();
        deliverymenPool.shutdown();
    }

    /**
     * Method that stops pizzeria, more precisely, it stops the work of bakers and deliverymen.
     * Checks the inactivity of the workers for a certain time,
     * after which it immediately terminates their work.
     *
     * @param seconds - frequency of condition checking.
     */
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

    /**
     * Puts new order to the queue.
     *
     * @param order - order to put
     * @throws InterruptedException - if interrupted while waiting
     */
    protected static void putToQueue(Order order) throws InterruptedException {
        orderQueue.put(order);
    }

    /**
     * Returns order from the queue.
     *
     * @return - order from queue
     * @throws InterruptedException - if interrupted while waiting
     */
    protected static Order takeFromQueue() throws InterruptedException {
        return orderQueue.take();
    }

    /**
     * Puts order to the storage.
     *
     * @param order - order to put
     * @throws InterruptedException - if interrupted while waiting
     */
    protected static void putToStorage(Order order) throws InterruptedException {
        storage.put(order);
    }

    /**
     * Returns list with a certain number of orders.
     *
     * @param number - number of orders to take
     * @return - list of orders
     * @throws InterruptedException - if interrupted while waiting
     */
    protected static List<Order> takeFromStorage(int number) throws InterruptedException {
        List<Order> orders = new ArrayList<>();
        if (storage.size() < number) {
            number = storage.size();
        }
        for (int i = 0; i < number; i++) {
            orders.add(storage.take());
        }
        return orders;
    }
}
