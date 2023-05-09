package ru.nsu.khlebnikov.model;

import lombok.Data;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ControlMark {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final Date date;
    private final String name;

    @SneakyThrows
    public ControlMark(String date, String name) {
        this.date = dateFormat.parse(date);
        this.name = name;
    }
}
