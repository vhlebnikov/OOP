package ru.nsu.khlebnikov;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Tools for testing methods in one class, convenient, isn't it?
 */
public class TestingTools {

    /**
     * Method that reads numbers (Integers) from file, add them to array and returns it.
     *
     * @param name file name
     * @return list with numbers from file
     */
    public static List<Integer> readFromFile(String name) {
        List<Integer> array = new ArrayList<>();
        try (Scanner reader = new Scanner(Objects.requireNonNull(
                TestingTools.class.getClassLoader().getResourceAsStream(name)
        ))) {
            while (reader.hasNext()) {
                array.add(reader.nextInt());
            }
        }
        return array;
    }

    /**
     * Method that measure time of certain method execution.
     *
     * @param method     method that you want to test
     * @param iterations number of iterations
     * @return average method execution time (in milliseconds)
     */
    public static double measure(Runnable method, int iterations) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            method.run();
        }
        long end = System.currentTimeMillis();
        return (double) (end - start) / iterations;
    }
}
