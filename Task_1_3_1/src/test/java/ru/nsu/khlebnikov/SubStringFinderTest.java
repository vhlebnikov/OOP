package ru.nsu.khlebnikov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests for my substring finder implementation.
 */
public class SubStringFinderTest {

    @Test
    public void testWithRussianLetters() throws IOException {
        List<Pair<Integer>> expected = List.of(Pair.create(0, 7));
        List<Pair<Integer>> actual =
                SubStringFinder.findSubStringFromResources("input_rus.txt", "пирог");
        assertEquals(actual, expected);
    }

    @Test
    public void emptyAnswerTest() throws IOException {
        assertTrue(SubStringFinder.findSubStringFromResources("input_rus.txt", "слово").isEmpty());
    }

    @Test
    public void anotherTest() throws IOException {
        List<Pair<Integer>> expected =
                List.of(Pair.create(0, 0), Pair.create(0, 2), Pair.create(0, 4),
                        Pair.create(0, 6), Pair.create(1, 0), Pair.create(1, 2),
                        Pair.create(2, 5));
        List<Pair<Integer>> actual =
                SubStringFinder.findSubStringFromResources("input1.txt", "dad");
        assertEquals(actual, expected);
    }

    @Test
    public void warAndPeaceTest() throws IOException {
        List<Pair<Integer>> actual =
                SubStringFinder.findSubStringFromResources("WarAndPeace.txt", "bragging");
        List<Pair<Integer>> expected = List.of(Pair.create(1564, 19), Pair.create(7481, 10));
        assertEquals(actual, expected);
    }

    @Test
    public void bigFileTest() throws IOException {
        FileCreator.createFile("bigFile.txt", "src/test/resources");
        List<Pair<Integer>> actual =
                SubStringFinder.findSubStringFromPath("bigFile.txt", "src/test/resources", "aba");
        List<Pair<Integer>> expected = List.of(Pair.create(0, 12), Pair.create(10000001, 12));
        FileCreator.deleteFile("bigFile.txt", "src/test/resources");
        assertEquals(actual, expected);
    }

    @Test
    public void exceptionTest() {
        Throwable exception = assertThrows(NullPointerException.class,
                () -> SubStringFinder.findSubStringFromResources("wrong_input.txt", "ba"));
        assertEquals(exception.getMessage(), "File doesn't exist");
        Throwable exception1 = assertThrows(FileNotFoundException.class,
                () -> SubStringFinder.findSubStringFromPath("wrong_input.txt", "path", "ba"));
        assertEquals(exception1.getMessage(), "File doesn't exist");
    }
}
