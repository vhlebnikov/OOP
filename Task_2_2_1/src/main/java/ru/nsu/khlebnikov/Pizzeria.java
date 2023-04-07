package ru.nsu.khlebnikov;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
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
    private static final Object storageLock = new Object();

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

    /**
     * Method that starts pizzeria, more precisely, it starts the work of bakers and deliverymen.
     */
    public void start() {
        System.out.println("Pizzeria has opened");
        bakers.forEach(bakersPool::submit);
        deliverymen.forEach(deliverymenPool::submit);
        bakersPool.shutdown();
        deliverymenPool.shutdown();
    }

    /**
     * Method that stops pizzeria, more precisely, it stops the work of bakers and deliverymen.
     * Waits for a more pleasant closing of the pizzeria.
     *
     * @throws InterruptedException - if was interrupted, but nobody wants to interrupt this
     */
    public void stop() throws InterruptedException {
        System.err.println("Pizzeria is closing");
        bakersPool.shutdownNow();
        deliverymenPool.shutdownNow();
        TimeUnit.SECONDS.sleep(5);
        System.err.println("Pizzeria has closed");
        System.err.println("Storage: " + storage);
        System.err.println("Queue of orders: " + orderQueue);
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
        if (storageCapacity > storage.size()) {
            storage.put(order);
        } else {
            synchronized (storageLock) {
                storageLock.wait();
                storage.put(order);
            }
        }
    }

    /**
     * Immediately adds order to the storage.
     * Blocking queue can't be full this time, because there is extra space for each baker to put order in a hurry.
     *
     * @param order - order to put
     */
    protected static void addToStorage(Order order) {
        storage.add(order);
    }

    /**
     * Adds orders to the bag of the deliveryman.
     *
     * @param bagCapacity - bag capacity of the deliveryman
     * @param orders - orders queue of the deliveryman
     */
    protected static void takeFromStorage(int bagCapacity, Queue<Order> orders) throws InterruptedException {
        int numberOfOrders = (int) Math.floor(Math.random() * bagCapacity + 1);

        if (storage.size() < numberOfOrders && storage.size() != 0) {
            numberOfOrders = storage.size();
        }

        for (int i = 0; i < numberOfOrders; i++) {
            orders.add(storage.take());
            synchronized (storageLock) {
                storageLock.notify();
            }
        }
    }

    /**
     * Adds a maximum of "bagCapacity" orders to the baggage carrier from the storage.
     *
     * @param bagCapacity - bag capacity of the deliveryman
     * @param orders - orders queue of the deliveryman
     */
    protected static void drainStorage(int bagCapacity, Queue<Order> orders) {
        storage.drainTo(orders, bagCapacity);
    }
}
