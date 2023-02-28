package ru.nsu.khlebnikov;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for PrimeChecker methods.
 */
public class PrimeCheckerTest {
    @Test
    public void initialTest() throws InterruptedException {
        List<Integer> firstArray = new ArrayList<>((List.of(6, 8, 7, 11, 13, 9, 4)));
        List<Integer> secondArray =
                new ArrayList<>(List.of(6997901, 6997927, 6997937, 6997967,
                                        6998009, 6998029, 6998039, 6998051, 6998053));
        Assertions.assertTrue(PrimeChecker.sequentialCheck(firstArray));
        Assertions.assertTrue(PrimeChecker.parallelCheck(firstArray));
        Assertions.assertTrue(PrimeChecker.threadCheck(firstArray, 4));
        Assertions.assertFalse(PrimeChecker.sequentialCheck(secondArray));
        Assertions.assertFalse(PrimeChecker.parallelCheck(secondArray));
        Assertions.assertFalse(PrimeChecker.threadCheck(secondArray, 4));
    }

    @Test
    public void testWithThousandPrimeNumbers() throws InterruptedException {
        List<Integer> array = TestingTools.readFromFile("ThousandPrimeNumbers.txt");
        Assertions.assertFalse(PrimeChecker.sequentialCheck(array));
        Assertions.assertFalse(PrimeChecker.parallelCheck(array));
        Assertions.assertFalse(PrimeChecker.threadCheck(array, 6));
        array.add(4);
        Assertions.assertTrue(PrimeChecker.sequentialCheck(array));
        Assertions.assertTrue(PrimeChecker.parallelCheck(array));
        Assertions.assertTrue(PrimeChecker.threadCheck(array, 6));
    }

    @Test
    public void testWithMillionPrimeNumbers() throws InterruptedException {
        List<Integer> array = TestingTools.readFromFile("MillionPrimeNumbers.txt");
        Assertions.assertFalse(PrimeChecker.sequentialCheck(array));
        Assertions.assertFalse(PrimeChecker.parallelCheck(array));
        Assertions.assertFalse(PrimeChecker.threadCheck(array, 12));
        array.add(4);
        Assertions.assertTrue(PrimeChecker.sequentialCheck(array));
        Assertions.assertTrue(PrimeChecker.parallelCheck(array));
        Assertions.assertTrue(PrimeChecker.threadCheck(array, 12));
    }

    @Test
    public void measureSpeedOfThreadMethod() {
        List<Integer> array = TestingTools.readFromFile("ThousandPrimeNumbers.txt");
        TestingTools.measure(() -> {
            try {
                PrimeChecker.threadCheck(array, 4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 100);
        System.out.println(TestingTools.measure(() -> PrimeChecker.parallelCheck(array), 100));
        System.out.println(TestingTools.measure(() -> PrimeChecker.sequentialCheck(array), 100));
        for (int i = 1; i <= 20; i++) {
            int finalI = i;
            System.out.println(i + " = " + TestingTools.measure(() -> {
                try {
                    PrimeChecker.threadCheck(array, finalI);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, 100));
        }
    }

    @Test
    public void measureTimeWithMillionNumbers() {
        List<Integer> array = TestingTools.readFromFile("MillionPrimeNumbers.txt");
        for (int i = 1; i <= 20; i++) {
            int finalI = i;
            System.out.println(i + " = " + TestingTools.measure(() -> {
                try {
                    PrimeChecker.threadCheck(array, finalI);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, 1));
        }
    }
}
