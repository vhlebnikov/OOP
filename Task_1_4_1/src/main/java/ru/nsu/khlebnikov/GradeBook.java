package ru.nsu.khlebnikov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeBook {
    private final String studentName;
    private final Map<String, List<Subject>> subjects;

    public GradeBook(String studentName) {
        this.studentName = studentName;
        this.subjects = new HashMap<>();
    }

    public String getStudentName() {
        return studentName;
    }

    public void addSubjects(String fileName) throws IOException {
        try (FileReader file = new FileReader("src/test/resources/" + fileName,
                StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(file)) {
            String input = reader.readLine();

            String subjectName = null;
            while (input != null) {
                Subject subject = new Subject();
                int start = 0;
                for (int i = 0; i < input.length(); i++) {
                    if (input.charAt(i) == '"') {
                        start = ++i;
                        while (input.charAt(i) != '"') {
                            i++;
                        }
                        subjectName = input.substring(start, i);
                        i++;
                        start = i + 1;
                    } else if (input.charAt(i) == ' ') {
                        subject.setSemester(input.substring(start, i));
                        start = i + 1;
                    }
                }
                if (start < input.length()) {
                    subject.setMark(input.substring(start));
                }
                if (!subjects.containsKey(subjectName)) {
                    List<Subject> l = new ArrayList<>();
                    l.add(subject);
                    this.subjects.put(subjectName, l);
                } else {
                    boolean equals = false;
                    for (Subject subject1 : subjects.get(subjectName)) {
                        if (subject1.equals(subject)) {
                            equals = true;
                            break;
                        }
                    }
                    if (!equals) {
                        subjects.get(subjectName).add(subject);
                    }
                }
                input = reader.readLine();
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void printSubjects() {
        for (Map.Entry<String, List<Subject>> entry : subjects.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                System.out.println(entry.getKey() + " " + entry.getValue().get(i).getSemester() +
                        " " + entry.getValue().get(i).getMark());
            }
        }
    }

    public String getAverageMark() {
        int countOfSubjects = 0;
        double averageMark = 0;
        for (Map.Entry<String, List<Subject>> entry : subjects.entrySet()) {
            for (Subject subject : entry.getValue()) {
                if (subject.getMark() != null) {
                    countOfSubjects++;
                    switch (subject.getMark()) {
                        case ("Отлично") -> averageMark += 5;
                        case ("Зачёт"), ("Хорошо") -> averageMark += 4;
                        case ("Удовлетворительно") -> averageMark += 3;
                        case ("Незачёт"), ("Неудовлетворительно") -> averageMark += 2;
                    }
                }
            }
        }
        if (countOfSubjects == 0) {
            throw new IllegalArgumentException("У этого студента нет оценок");
        }
        averageMark /= countOfSubjects;
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(averageMark);
    }
}
