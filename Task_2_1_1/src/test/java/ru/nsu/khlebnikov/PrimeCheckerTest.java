package ru.nsu.khlebnikov;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PrimeCheckerTest {
    public static long measure(Runnable method, int iterations) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            method.run();
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    @Test
    public void test() {
        List<Integer> array = new ArrayList<>(List.of(6997901, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053, 6997901, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053, 6997901, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053,6997901, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053, 6997901, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053, 4));
        System.out.println(measure(() -> PrimeChecker.sequentialCheck(array), 100));
        System.out.println(measure(() -> PrimeChecker.parallelCheck(array), 100));
        System.out.println(measure(() -> {
            try {
                PrimeChecker.threadCheck(array, 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 100));
    }
    @Test
    public void test2() throws InterruptedException {
        List<Integer> array = new ArrayList<>(List.of(6997901, 4, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053, 3));
        boolean b = PrimeChecker.threadCheck(array, 4);
//        System.out.println(b);
    }
}
