package ru.nsu.khlebnikov;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PrimeCheckerTest {
//    @Test
//    public void test() {
//        long duration = 0;
//        long start, end;
//        boolean b;
//        List<Integer> array = new ArrayList<>(List.of(6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053, 6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053, 6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053));
//        for (int i = 0; i < 100000; i++) {
//            start = System.nanoTime();
//            b = PrimeChecker.sequentialCheck(array);
//            end = System.nanoTime();
//            duration += (end - start);
//        }
//        System.out.println("Seq check = " + (duration / 100000));
//        duration = 0;
//        for (int i = 0; i < 100000; i++) {
//            start = System.nanoTime();
//            b = PrimeChecker.parallelCheck(array);
//            end = System.nanoTime();
//            duration += (end - start);
//        }
//        System.out.println("Parallel check = " + (duration / 100000));
//    }
    @Test
    public void test2() throws InterruptedException {
        List<Integer> array = new ArrayList<>(List.of(6997901, 4, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053));
        boolean b = PrimeChecker.threadCheck(array, 4);
        System.out.println(PrimeChecker.flag);
    }
}
