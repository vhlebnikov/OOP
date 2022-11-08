package ru.nsu.khlebnikov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.util.ArrayList;

public class SubStringFinderTest {
    @Test
    public void exceptionTest() {
        Throwable exception = assertThrows(NullPointerException.class, () -> SubStringFinder.findSubString("input.txt", "ba"));
        assertEquals(exception.getMessage(), "File is empty");
        exception = assertThrows(IOException.class, () -> SubStringFinder.findSubString("wrong_input.txt", "ba"));
        assertEquals(exception.getMessage(), "Can't open the file");
    }

    @Test
    public void test() throws IOException {
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(2);
        expected.add(4);
        expected.add(6);
        ArrayList<Integer> actual = SubStringFinder.findSubString("input1.txt", "aba");
        assertEquals(actual,expected);
    }
}
