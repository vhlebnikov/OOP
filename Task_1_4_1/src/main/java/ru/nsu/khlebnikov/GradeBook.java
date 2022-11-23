package ru.nsu.khlebnikov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GradeBook {
    private String studentName;
    private List<Subject> subjects;

    public GradeBook(String studentName) {
        this.studentName = studentName;
        this.subjects = new ArrayList<>();
    }

    public void addSubjects(String fileName) throws IOException {
        try (FileReader file = new FileReader("src/test/resources/" + fileName,
                StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(file)) {
            String input = reader.readLine();
            while (input != null) {
                Subject subject = new Subject();
                int start = 0;
                for (int i = 0; i < input.length(); i++) {
                    if (input.charAt(i) == '"') {
                        start = ++i;
                        while (input.charAt(i) != '"') {
                            i++;
                        }
                        subject.setSubjectName(input.substring(start, i));
                        i++;
                        start = i + 1;
                    } else if (input.charAt(i) == ' ') {
                        subject.setSemester(Integer.parseInt(input.substring(start, i)));
                        start = i + 1;
                    }
                }
                if (start < input.length()) {
                    subject.setMark(input.substring(start, input.length()));
                } else {
                    subject.setMark("Нет оценки");
                }
                this.subjects.add(subject);
                input = reader.readLine();
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void printSubjects() {
        for (Subject subject : subjects) {
            System.out.println("Semester = " + subject.getSemester());
            System.out.println("Subject Name = " + subject.getSubjectName());
            System.out.println("Mark = " + subject.getMark());
        }
    }

    public double getAverageMark() {
        int countOfSubjects = 0;
        double averageMark = 0;
        for (Subject subject : subjects) {
            countOfSubjects++;
            switch (subject.getMark()) {
                case ("Зачёт") -> averageMark += 4;
                case ("Незачёт") -> averageMark += 2;
                case ("Отлично") -> averageMark += 5;
                case ("Хорошо") -> averageMark += 4;
                case ("Удовлетворительно") -> averageMark += 3;
                case ("Неудовлетворительно") -> averageMark += 2;
            }
        }
        if (countOfSubjects == 0 || averageMark == 0) {
            throw new IllegalArgumentException("У этого студента нет оценок");
        }
        averageMark /= countOfSubjects;
        return averageMark;
    }
}
