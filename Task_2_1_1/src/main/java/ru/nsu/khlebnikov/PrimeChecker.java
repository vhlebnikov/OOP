package ru.nsu.khlebnikov;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class for searching non-simple numbers in different ways,
 * all methods return the fact that there is a non-simple number in the array.
 */
public class PrimeChecker {

    /**
     * Static method that finds out existence of non-prime number in array using sequential execution.
     *
     * @param array input array
     * @return fact of existence of non-prime number in array
     */
    public static boolean sequentialCheck(List<Integer> array) {
        return array.stream().anyMatch(x -> !BigInteger.valueOf(x).isProbablePrime(10));
    }

    /**
     * Static method that finds out existence of non-prime number in array using parallelStream execution.
     *
     * @param array input array
     * @return fact of existence of non-prime number in array
     */
    public static boolean parallelCheck(List<Integer> array) {
        return array.parallelStream().anyMatch(x -> !BigInteger.valueOf(x).isProbablePrime(10));
    }

    /**
     * Static method that finds out existence of non-prime number in array using certain number of threads.
     *
     * @param array           input array
     * @param numberOfThreads number of thread that you want to use
     * @return fact of existence of non-prime number in array
     */
    public static boolean threadCheck(List<Integer> array, int numberOfThreads)
            throws InterruptedException {
        if (array.size() < numberOfThreads) {
            numberOfThreads = array.size();
        }
        if (numberOfThreads <= 0) {
            throw new IndexOutOfBoundsException("Number of threads must be positive");
        }
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<ThreadTask> tasks = new ArrayList<>();

        int div = array.size() / numberOfThreads;
        int mod = array.size() % numberOfThreads;
        int start = 0, end;

        for (int i = 0; i < numberOfThreads; i++) {
            end = mod-- > 0 ? start + div + 1 : start + div;
            tasks.add(new ThreadTask(array, start, end));
            start = end;
        }

        List<Future<Boolean>> futures = executorService.invokeAll(tasks);
        executorService.shutdown();

        return futures.stream().anyMatch(x -> {
            try {
                return x.get().equals(true);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }
}
