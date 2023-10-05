package ru.nsu.khlebnikov.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Group {
    private final String name;
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }
}
