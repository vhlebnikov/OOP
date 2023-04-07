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
    private static int storageCapacity;
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
        this.bakers = jsonData.getBakers();
        this.deliverymen = jsonData.getDeliverymen();
        bakersPool = Executors.newFixedThreadPool(bakers.size());
        deliverymenPool = Executors.newFixedThreadPool(deliverymen.size());
        storageCapacity = jsonData.getStorageCapacity();
        storage = new LinkedBlockingQueue<>(storageCapacity + bakers.size());
        orderQueue = new LinkedBlockingQueue<>();
    }

    public static int getOrderQueueSize() {
        return orderQueue.size();
    }

    public static int getStorageSize() {
        return storage.size();
    }

    /**
     * Method that starts pizzeria, more precisely, it starts the work of bakers and deliverymen.
     */
    public void start() {
        bakers.forEach(bakersPool::submit);
//        deliverymen.forEach(deliverymenPool::submit);
        bakersPool.shutdown();
//        deliverymenPool.shutdown();
    }

    /**
     * Method that stops pizzeria, more precisely, it stops the work of bakers and deliverymen.
     * Checks the inactivity of the workers for a certain time,
     * after which it immediately terminates their work.
     *
     * @param seconds - frequency of condition checking.
     */
    public void stop(long seconds) throws InterruptedException {
//        boolean notWork = bakers.stream().noneMatch(Baker::isWorking)
//                && deliverymen.stream().noneMatch(Deliveryman::isWorking);
//        long start = System.nanoTime();
//        while (true) {
//            if ((System.nanoTime() - start) >= TimeUnit.SECONDS.toNanos(seconds)) {
//                if (bakers.stream().noneMatch(Baker::isWorking)
//                        && deliverymen.stream().noneMatch(Deliveryman::isWorking) && notWork) {
//                    System.out.println("Shutdown");
//                    bakersPool.shutdownNow();
//                    deliverymenPool.shutdownNow();
//                    return;
//                } else {
//                    notWork = bakers.stream().noneMatch(Baker::isWorking)
//                            && deliverymen.stream().noneMatch(Deliveryman::isWorking);
//                    start = System.nanoTime();
//                }
//            }
//        }
        bakersPool.shutdownNow();
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
     */
    protected static void putToStorage(Order order) {
        try {
            if (storageCapacity != storage.size()) {
                storage.put(order);
            } else {
                Thread.currentThread().wait();
                storage.put(order);
            }
        } catch (InterruptedException e) {
            storage.add(order);
        }
    }

    /**
     * Returns list with a certain number of orders.
     *
     * @param number - number of orders to take
     * @return - list of orders
     */
    protected static List<Order> takeFromStorage(int number) { // изменить мб
        List<Order> orders = new ArrayList<>();
        int i = 0;
        if (storage.size() < number && storage.size() != 0) {
            number = storage.size();
        }
        try {
            for (;i < number; i++) {
                orders.add(storage.take());
                Thread.currentThread().notify();
            }
        } catch (InterruptedException e) {
            for (;i < number && !storage.isEmpty(); i++) {
                orders.add(storage.poll());
                Thread.currentThread().notify();
            }
        }
        return orders;
    }
}
