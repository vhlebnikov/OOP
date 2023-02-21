package ru.nsu.khlebnikov;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PrimeChecker {
    private static volatile boolean flag;

    public static boolean sequentialCheck(List<Integer> array) {
        return array.stream().anyMatch(x -> !BigInteger.valueOf(x).isProbablePrime(1));
    }

    public static boolean parallelCheck(List<Integer> array) {
        return array.parallelStream().anyMatch(x -> !BigInteger.valueOf(x).isProbablePrime(1));
    }

    public static boolean threadCheck(List<Integer> array, int numberOfThreads) throws InterruptedException { // Вот эту тему вообще переделать
        if (array.size() < numberOfThreads) {
            numberOfThreads = array.size();
        }
        if (numberOfThreads <= 0) {
            throw new InterruptedException("Number of threads must be positive");
        }
        flag = false;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        int div = array.size() / numberOfThreads;
        int mod = array.size() % numberOfThreads;
        for (int i = 0; i < array.size() - div; ) {
            int end = i + div;
            if (numberOfThreads - end / div < mod) {
                end++;
            }
            executorService.submit(task(i, end, array));
            i = end;
        }
//        while (!executorService.isTerminated()) {
//            executorService.awaitTermination(100, TimeUnit.NANOSECONDS);
//        }
        System.out.println(executorService.awaitTermination(1000, TimeUnit.NANOSECONDS));
        if (flag) {
            flag = false;
            return true;
        }
        return false;
    }

    private static Runnable task(int start, int end, List<Integer> array) {
        return () -> {
            System.out.println("Processing");
            if (array.subList(start, end).stream().anyMatch(x -> !BigInteger.valueOf(x).isProbablePrime(1))) {
                flag = true;
            }
            System.out.println("Out");
        };
    }
}
