package ru.nsu.khlebnikov;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Callable interface implementation for searching non-prime numbers in sub-array.
 */
public class ThreadTask implements Callable<Boolean> {
    private final List<Integer> subArray;

    public ThreadTask(List<Integer> array, int start, int end) {
        this.subArray = array.subList(start, end);
    }

    /**
     * Overridden call method, that determines if there non-prime number in sub-array.
     *
     * @return the fact of existing a non-prime number
     */
    @Override
    public Boolean call() {
        return subArray.stream().anyMatch(x -> !BigInteger.valueOf(x).isProbablePrime(10));
    }
}
