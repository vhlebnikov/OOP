package ru.nsu.khlebnikov.model;

import lombok.Data;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Lesson {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final Date date;

    @SneakyThrows
    public Lesson(String date) {
        this.date = dateFormat.parse(date);
    }
}
