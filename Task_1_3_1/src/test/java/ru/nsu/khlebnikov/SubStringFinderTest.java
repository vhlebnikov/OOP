package ru.nsu.khlebnikov;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for my substring finder implementation.
 */
public class SubStringFinderTest {

    @Test
    public void testWithRussianLetters() throws IOException {
        List<List<Integer>> expected = List.of(Arrays.asList(0, 7));
        List<List<Integer>> actual = SubStringFinder.findSubString("input_rus.txt", "пирог");
        assertEquals(actual, expected);
    }

    @Test
    public void emptyAnswerTest() throws IOException {
        assertTrue(SubStringFinder.findSubString("input_rus.txt", "слово").isEmpty());
    }

    @Test
    public void anotherTest() throws IOException {
        List<List<Integer>> expected =
                List.of(Arrays.asList(0, 0), Arrays.asList(0, 2), Arrays.asList(0, 4),
                        Arrays.asList(0, 6), Arrays.asList(1, 0), Arrays.asList(1, 2),
                        Arrays.asList(2, 5));
        List<List<Integer>> actual = SubStringFinder.findSubString("input1.txt", "dad");
        assertEquals(actual, expected);
    }

    @Test
    public void warAndPeaceTest() throws IOException {
        List<List<Integer>> actual = SubStringFinder.findSubString("WarAndPeace.txt", "bragging");
        List<List<Integer>> expected = List.of(Arrays.asList(1564, 19), Arrays.asList(7481, 10));
        assertEquals(actual, expected);
    }

//    @Test
//    public void bigFileTest() throws IOException {
//        FileCreator.createFile("bigFile.txt");
//        List<List<Integer>> actual = SubStringFinder.findSubString("bigFile.txt", "aba");
//        List<List<Integer>> expected = List.of(Arrays.asList(0, 12), Arrays.asList(10000001, 12));
//        assertEquals(actual, expected);
//    }

    @Test
    public void exceptionTest() {
        Throwable exception = assertThrows(NullPointerException.class, () -> SubStringFinder.findSubString("empty_input.txt", "ba"));
        assertEquals(exception.getMessage(), "File is empty");
        exception = assertThrows(IOException.class, () -> SubStringFinder.findSubString("wrong_input.txt", "ba"));
        assertEquals(exception.getMessage(), "Can't open the file");
    }
}
