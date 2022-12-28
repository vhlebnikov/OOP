package ru.nsu.khlebnikov;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Note structure.
 */
public class Note {
    private final String title;
    private final String text;
    private final Date date;

    /**
     * Constructor needed for notes without data.
     *
     * @param title note's title
     */
    public Note(String title) {
        this.title = title;
        this.text = "";
        this.date = new Date();
    }

    /**
     * Constructor for full filled notes.
     *
     * @param title note's title
     * @param text note's text
     */
    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        this.date = new Date();
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Method that converts date type to string.
     *
     * @return string representation of date
     */
    public String printDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");
        return dateFormat.format(date);
    }

    @Override
    public String toString() {
        return "Note{"
                + "title='" + title + '\''
                + ", text='" + text + '\''
                + ", date=" + printDate()
                + '}';
    }
}
