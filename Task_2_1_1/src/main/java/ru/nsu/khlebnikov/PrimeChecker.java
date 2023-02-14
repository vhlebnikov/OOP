package ru.nsu.khlebnikov;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PrimeChecker {
    public static volatile boolean flag = false;

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
        executorService.awaitTermination(10, TimeUnit.NANOSECONDS);
        // Сделать, чтобы главная нить ожидала завершения всех дочерних нитей
        if (flag) {
            flag = false;
            return true;
        }
        return flag;
    }

    static Runnable task(int start, int end, List<Integer> array) {
        return () -> {
            if (array.subList(start, end).stream().anyMatch(x -> !BigInteger.valueOf(x).isProbablePrime(1))) {
                flag = true;
            }
        };
    }
}
