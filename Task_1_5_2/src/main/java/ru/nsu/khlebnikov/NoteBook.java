package ru.nsu.khlebnikov;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.FileAlreadyExistsException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * My realization of notebook.
 */
public class NoteBook {
    private final File file;
    private final static Gson gson = new Gson();

    /**
     * Constructor that links file with notebook.
     * If file doesn't exist method creates it.
     *
     * @param fileName file name
     * @throws IOException exception thrown if I/O problems occurs
     */
    public NoteBook(String fileName) throws IOException {
        file = new File(fileName);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new FileAlreadyExistsException("File already exists");
            }
        }
    }

    /**
     * Method that get data with notes from json file.
     *
     * @return list of notes
     * @throws IOException exception thrown if I/O problems occurs
     */
    public List<Note> getNotesFromJson() throws IOException {
        List<Note> notes;
        TypeToken<List<Note>> listType = new TypeToken<>() {
        };
        try (Reader reader = new FileReader(file)) {
            notes = gson.fromJson(reader, listType);
        }
        if (notes == null) {
            return new ArrayList<>();
        }
        return notes;
    }

    /**
     * Method that writes data to json file.
     *
     * @param notes list of notes
     * @throws IOException exception thrown if I/O problems occurs
     */
    public void writeDataToJson(List<Note> notes) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(notes, writer);
        }
    }

    /**
     * Method that add note to notebook.
     *
     * @param title note's title
     * @param data note's data
     * @throws IOException exception thrown if I/O problems occurs
     */
    public void add(String title, String data) throws IOException {
        List<Note> notes = getNotesFromJson();
        Note note;
        if (notes.stream().anyMatch(x -> x.getTitle().equals(title))) {
            throw new IllegalArgumentException("Note with this title already exists");
        }
        if (data == null) {
            note = new Note(title);
        } else {
            note = new Note(title, data);
        }
        notes.add(note);
        writeDataToJson(notes);
    }

    /**
     * Method that removes note from notebook by title.
     *
     * @param title note's title
     * @throws IOException exception thrown if I/O problems occurs
     */
    public void remove(String title) throws IOException {
        List<Note> notes = getNotesFromJson();
        List<Note> newNotes = notes.stream().filter(x -> !x.getTitle().equals(title)).toList();
        writeDataToJson(newNotes);
    }

    /**
     * Method that prints all notes from notebook.
     *
     * @throws IOException exception thrown if I/O problems occurs
     */
    public void show() throws IOException {
        List<Note> notes = getNotesFromJson();
        notes.stream().sorted(Comparator.comparing(Note::getDate)).forEach(System.out::println);
    }

    /**
     * Method that prints notes which are between two time bounds and include certain substrings.
     *
     * @param leftBound left bound of time interval
     * @param rightBound right bound of time interval
     * @param substrings list of substrings
     * @throws IOException exception thrown if I/O problems occurs
     * @throws ParseException exception thrown if problems with parsing time to Date type occurs
     */
    public void show(String leftBound, String rightBound, List<String> substrings)
            throws IOException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");
        Date leftBoundDate = dateFormat.parse(leftBound.substring(1, leftBound.length() - 1));
        Date rightBoundDate = dateFormat.parse(rightBound.substring(1, rightBound.length() - 1));
        List<Note> notes = getNotesFromJson();
        List<Note> newNotes = new ArrayList<>(notes.stream()
                .filter(note -> note.getDate().after(leftBoundDate)
                        && note.getDate().before(rightBoundDate)).toList());
        newNotes.removeIf(note -> substrings.stream().noneMatch(note.getTitle()::contains));
        newNotes.stream().sorted(Comparator.comparing(Note::getDate)).forEach(System.out::println);
    }
}
