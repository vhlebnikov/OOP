package ru.nsu.khelbnikov;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
    private String title;
    private String text;
    private String date;

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");
        Date date = new Date();
        this.date = simpleDateFormat.format(date);
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }


}
