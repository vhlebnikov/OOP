package ru.nsu.khlebnikov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Realization of student grade book.
 */
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

    /**
     * Method, that fills subjects in grade book.
     *
     * @param fileName name of file that contains grade book information
     * @throws IOException if I/O error occurs
     */
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

    /**
     * Method that calculates student's average mark.
     * <p>My grade translation table:
     * <ul>
     *     <li>"Зачёт", "Отлично" == 5</li>
     *     <li>""Хорошо" == 4</li>
     *     <li>"Удовлетворительно" == 3</li>
     *     <li>"Незачёт", "Неудовлетворительно" == 2</li>
     * </ul>
     *
     * @return student's average mark
     */
    public String getAverageMark() {
        int countOfMarks = 0;
        double averageMark = 0;
        for (Map.Entry<String, List<Subject>> entry : subjects.entrySet()) {
            for (Subject subject : entry.getValue()) {
                if (subject.getMark() != null) {
                    countOfMarks++;
                    switch (subject.getMark()) {
                        case ("Зачёт") :
                            averageMark += 5;
                            break;
                        case ("Отлично") :
                            averageMark += 5;
                            break;
                        case ("Хорошо") :
                            averageMark += 4;
                            break;
                        case ("Удовлетворительно") :
                            averageMark += 3;
                            break;
                        case ("Незачёт") :
                            averageMark += 2;
                            break;
                        case ("Неудовлетворительно") :
                            averageMark += 2;
                            break;
                    }
                }
            }
        }
        if (countOfMarks == 0) {
            throw new IllegalArgumentException("У этого студента нет оценок");
        }
        averageMark /= countOfMarks;
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(averageMark);
    }

    /**
     * Method that calculates student's red diploma average mark.
     * <p>My grade translation table:
     * <ul>
     *      <li>"Зачёт", "Отлично" == 5</li>
     *      <li>"Хорошо" == 4</li>
     *      <li>"Удовлетворительно" == 3</li>
     *      <li>"Незачёт", "Неудовлетворительно" == 2</li>
     * </ul>
     *
     * @return student's red diploma average mark
     */
    public String getAverageRedDiplomaMark() {
        double mark = 0;
        int countOfSubjects = 0;
        for (Map.Entry<String, List<Subject>> entry : subjects.entrySet()) {
            Subject subject = entry.getValue().stream().
                    max(Comparator.comparing(Subject::getSemester)).get();
            if (subject.getMark() != null) {
                countOfSubjects++;
                switch (subject.getMark()) {
                    case ("Зачёт") :
                        mark += 5;
                        break;
                    case ("Отлично") :
                        mark += 5;
                        break;
                    case ("Хорошо") :
                        mark += 4;
                        break;
                    case ("Удовлетворительно") :
                        mark += 3;
                        break;
                    case ("Незачёт") :
                        mark += 2;
                        break;
                    case ("Неудовлетворительно") :
                        mark += 2;
                        break;
                }
            }
        }
        mark /= countOfSubjects;
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(mark);
    }

    /**
     * A method that finds out if a student can get a red diploma.
     *
     * @return string that contains information about the possibility of getting a red diploma
     */
    public String redDiploma() {
        double countOfGreatMarks = 0;
        double countOfMarks = 0;
        boolean qualificationWork = false;
        boolean isQualificationWork = false;
        for (Map.Entry<String, List<Subject>> entry : subjects.entrySet()) {
            Subject subject = entry.getValue().stream().
                    max(Comparator.comparing(Subject::getSemester)).get();
            if (subject.getMark() != null) {
                if (entry.getKey().equals("Квалификационная работа")) {
                    isQualificationWork = true;
                }
                if (subject.getMark().equals("Отлично") || subject.getMark().equals("Зачёт")) {
                    if (entry.getKey().equals("Квалификационная работа")) {
                        qualificationWork = true;
                    }
                    countOfGreatMarks++;
                } else if (subject.getMark().equals("Удовлетворительно") ||
                        subject.getMark().equals("Неудовлетворительно") ||
                        subject.getMark().equals("Незачёт")) {
                    return "нет";
                } else if (!subject.getMark().equals("Хорошо")) {
                    return "неверно введены данные";
                }
                countOfMarks++;
            } else {
                return "недостаточно информации";
            }
        }
        if (((countOfGreatMarks / countOfMarks) >= 0.75d) && qualificationWork) {
            return "да";
        } else if (isQualificationWork){
            return "нет";
        } else {
            return "недостаточно информации";
        }
    }

    /**
     * Method that finds out if a student can get increased scholarship
     *      for certain semester.
     *
     * @param semester semester number
     * @return string that contains type of increased scholarship or inability to get it
     */
    public String increasedScholarship(String semester) {
        boolean markGood = false;
        for (Map.Entry<String, List<Subject>> entry : subjects.entrySet()) {
            List<Subject> actuallyOneSubject =
                    entry.getValue().stream().
                            filter(x -> x.getSemester().equals(semester)).toList();
            if (actuallyOneSubject.size() == 0) {
                continue;
            }
            Subject subject = actuallyOneSubject.get(0);
            if (subject.getMark() != null) {
                if (subject.getMark().equals("Хорошо")) {
                    if (!markGood) {
                        markGood = true;
                    } else {
                        return "нет";
                    }
                } else if (subject.getMark().equals("Удовлетворительно") ||
                        subject.getMark().equals("Неудовлетворительно") ||
                        subject.getMark().equals("Незачёт")) {
                    return "нет";
                } else if (!subject.getMark().equals("Отлично") &&
                        !subject.getMark().equals("Зачёт")) {
                    return "неверно введены данные";
                }
            } else {
                return "недостаточно информации";
            }
        }
        if (markGood) {
            return "NSU+";
        } else {
            return "NSU++";
        }
    }

    /**
     * Method that creates file with statistic about student, which contains:
     * <ul>
     *     <li>Student name</li>
     *     <li>Student's average mark</li>
     *     <li>Student's red diploma average mark</li>
     *     <li>Possibility to get a red diploma</li>
     * </ul>
     *
     * @param fileName name of output file
     * @param numOfSemesters the number of semesters for which you need to get statistics
     * @throws IOException if I/O error occurs
     */
    public void createGradeBookFile(String fileName, Integer numOfSemesters) throws IOException {
        File file = new File("src/test/resources/" + fileName);
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println("Студент: " + getStudentName());
            pw.println("Общий средний балл: " + getAverageMark());
            pw.println("Средний балл диплома: " + getAverageRedDiplomaMark());
            pw.println("Будет ли красный диплом? - " + redDiploma());
            for (int i = 1; i <= numOfSemesters; i++) {
                pw.println("Повышенная стипендия за " + i +
                        " семестр: " + increasedScholarship(Integer.toString(i)));
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}