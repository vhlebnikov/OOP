package ru.nsu.khlebnikov;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class PrimeChecker {

    public static boolean sequentialCheck(List<Integer> array) {
        return array.stream().anyMatch(x -> !BigInteger.valueOf(x).isProbablePrime(1));
    }

    public static boolean parallelCheck(List<Integer> array) {
        return array.parallelStream().anyMatch(x -> !BigInteger.valueOf(x).isProbablePrime(1));
    }

    private static Callable<Boolean> task(List<Integer> array, int start, int end) {
        return () -> {
            return array.subList(start, end).stream().anyMatch(x -> !BigInteger.valueOf(x).isProbablePrime(1));
        };
    }

    public static boolean threadCheck(List<Integer> array, int numberOfThreads) throws InterruptedException {
        if (array.size() < numberOfThreads) {
            numberOfThreads = array.size();
        }
        if (numberOfThreads <= 0) {
            throw new InterruptedException("Number of threads must be positive");
        }
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Boolean>> result = new ArrayList<>();
        int div = array.size() / numberOfThreads;
        int mod = array.size() % numberOfThreads;
        for (int i = 0; i < array.size() - div; ) {
            int end = i + div;
            if (numberOfThreads - end / div < mod) {
                end++;
            }
            result.add(executorService.submit(task(array, i, end)));
            i = end;
        }
        executorService.shutdown();
        return result.stream().anyMatch(x -> {
            try {
                return x.get().equals(true);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
