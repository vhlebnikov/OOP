package ru.nsu.khlebnikov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NoteBookTest {
    @Test
    public void test() {
        NoteBook noteBook = new NoteBook();
        noteBook.addNote("123", "123");
        noteBook.addNote("1234", "123");
        noteBook.addNote("1235", "123");
        noteBook.deleteNote("1234");
        NoteBook actual = new NoteBook();
        actual.addNote("123", "123");
        actual.addNote("1235", "123");
        Assertions.assertEquals(noteBook.getNotes(), actual.getNotes());
    }
}
