package ru.nsu.khlebnikov;

import java.math.BigInteger;
import java.util.UUID;

/**
 * Class representing customer's order of pizza.
 */
public class Order {
    private final long number;
    private final Customer customer;

    public enum Status {
        Ordered,
        Cooking,
        PizzaIsDone,
        DeliveredToStorage,
        Delivery,
        Done
    }

    private Status status;

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

    @Override
    public String toString() {
        return "Order by " + customer.name()
                + ": [number = " + number + "], [status = " + status + ']';
    }
}
