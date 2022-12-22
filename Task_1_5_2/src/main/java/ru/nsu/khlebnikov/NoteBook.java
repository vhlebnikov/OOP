package ru.nsu.khlebnikov;

import java.util.ArrayList;
import java.util.List;

public class NoteBook {
    private List<Note> notes;

    public NoteBook() {
        this.notes = new ArrayList<>();
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void addNote(String title, String text) {
        Note note = new Note(title, text);
        notes.add(note);
    }

    public void deleteNote(String title) {
        for (Note note : notes) {
            if (note.getTitle().equals(title)) {
                notes.remove(note);
                break;
            }
        }
    }
}
