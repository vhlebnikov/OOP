package ru.nsu.khlebnikov.model;

import lombok.Data;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Task {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final String id;
    private final String title;
    private final int points;
    private final Date deadline;

    @SneakyThrows
    public Task(String id, String title, int points, String deadline) {
        this.id = id;
        this.title = title;
        this.points = points;
        this.deadline = dateFormat.parse(deadline);
    }
}
