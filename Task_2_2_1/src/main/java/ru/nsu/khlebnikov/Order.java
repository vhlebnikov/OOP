package ru.nsu.khlebnikov;

import java.math.BigInteger;
import java.util.UUID;

/**
 * Class representing customer's order of pizza.
 */
public class Order {
    private final long number;
    private final Customer customer;

    /**
     * Status of order.
     */
    public enum Status {
        Ordered,
        Cooking,
        PizzaIsDone,
        PizzaIsDoneInHurry,
        DeliveredToStorage,
        Delivery,
        Done
    }

    private Status status;

    /**
     * Makes new order with certain customer and generates ID for this order.
     *
     * @param customer - customer that makes order
     */
    public Order(Customer customer) {
        String stringNumber = String.format("%010d",
                new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
        this.number = Long.parseLong(stringNumber.substring(stringNumber.length() - 10));
        this.customer = customer;
        this.status = Status.Ordered;
        System.out.println(this);
    }

    public void setStatus(Status status) {
        this.status = status;
        System.out.println(this);
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Order by " + customer.name()
                + ": [number = " + number + "], [status = " + status + ']';
    }
}
