package ru.nsu.khlebnikov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/**
 * Test for pizzeria.
 */
public class PizzeriaTest {
    @Test
    public void pizzeriaTest() throws IOException, InterruptedException {
        Pizzeria pizzeria = new Pizzeria("info.json");
        pizzeria.start();
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            customers.add(new Customer("Guy number " + i));
        }
        ExecutorService customersPool = Executors.newCachedThreadPool();
        customers.forEach(customersPool::submit);
        TimeUnit.SECONDS.sleep(2);
        customersPool.submit(new Customer("Guy number 5"));
        customersPool.shutdown();
        pizzeria.stop(1);
    }
}
