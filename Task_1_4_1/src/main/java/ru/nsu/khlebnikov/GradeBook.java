package ru.nsu.khlebnikov;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
     */
    public void addSubjects(String fileName) {
        try (Scanner scanner = new Scanner(Objects.requireNonNull(
                GradeBook.class.getClassLoader().getResourceAsStream(fileName)),
                StandardCharsets.UTF_8)) {
            List<String> input;
            Subject subject;
            while (scanner.hasNext()) {
                input = List.of(scanner.nextLine().split(" \"|\" |\""));
                if (input.size() == 2) {
                    subject = new Subject(Integer.parseInt(input.get(0)));
                } else if (input.size() == 3) {
                    subject = new Subject(Integer.parseInt(input.get(0)), Mark.getEnumMark(input.get(2)));
                } else {
                    throw new IllegalArgumentException("Illegal string with data");
                }
                if (!subjects.containsKey(input.get(1))) {
                    List<Subject> list = new ArrayList<>();
                    list.add(subject);
                    subjects.put(input.get(1), list);
                } else {
                    Subject finalSubject = subject;
                    if (subjects.get(input.get(1)).stream()
                            .noneMatch(s -> s.equals(finalSubject))) {
                        subjects.get(input.get(1)).add(subject);
                    }
                }
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Wrong input string");
        }
    }

    /**
     * Method that calculates student's average mark.
     *
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
    public Double getAverageMark() {
        Supplier<Stream<Subject>> stream = () -> subjects.values().stream().flatMap(Collection::stream)
                .filter(x -> x.getMark() != null);
        double countOfMarks = stream.get().count();
        double sumOfMarks = stream.get().mapToDouble(x -> Mark.getDoubleMark(x.getMark())).sum();
        if (countOfMarks == 0) {
            throw new IllegalArgumentException("This student has no grades");
        }
        sumOfMarks = sumOfMarks / countOfMarks * 10;
        sumOfMarks = Math.round(sumOfMarks);
        sumOfMarks /= 10;
        return sumOfMarks;
    }

    /**
     * Method that calculates student's diploma average mark.
     *
     * <p>My grade translation table:
     * <ul>
     *      <li>"Зачёт", "Отлично" == 5</li>
     *      <li>"Хорошо" == 4</li>
     *      <li>"Удовлетворительно" == 3</li>
     *      <li>"Незачёт", "Неудовлетворительно" == 2</li>
     * </ul>
     *
     * @return student's diploma average mark
     */
    public Double getAverageDiplomaMark() {
        Supplier<Stream<Subject>> stream = () -> subjects.entrySet().stream()
                .flatMap(x -> x.getValue().stream()
                        .max(Comparator.comparing(Subject::getSemester)).stream())
                .filter(x -> x.getMark() != null);
        double countOfMarks = stream.get().count();
        double sumOfMarks = stream.get().mapToDouble(x -> Mark.getDoubleMark(x.getMark())).sum();
        if (countOfMarks == 0) {
            throw new IllegalArgumentException("This student has no grades");
        }
        sumOfMarks = sumOfMarks / countOfMarks * 10;
        sumOfMarks = Math.round(sumOfMarks);
        sumOfMarks /= 10;
        return sumOfMarks;
    }

    /**
     * A method that finds out if a student can get a red diploma.
     *
     * @return possibility of getting a red diploma
     */
    public boolean redDiploma() {
        Supplier<Stream<Subject>> stream = () -> subjects.entrySet().stream()
                .flatMap(x -> x.getValue().stream()
                        .max(Comparator.comparing(Subject::getSemester)).stream())
                .filter(x -> x.getMark() != null);
        double countOfMarks = stream.get()
                .count();
        double countOfGreatMarks =  stream.get()
                .filter(x -> x.getMark().equals(Mark.GREAT)
                || x.getMark().equals(Mark.PASS)).count();
        if (countOfMarks == 0) {
            throw new IllegalArgumentException("This student has no grades");
        }
        boolean isBadMark = stream.get()
                .anyMatch(x -> x.getMark().equals(Mark.INSUFFICIENT)
                || x.getMark().equals(Mark.UNSATISFACTORY)
                || x.getMark().equals(Mark.SATISFACTORY));
        boolean isQualificationWorkMarkGreat = subjects.entrySet().stream()
                .filter(x -> x.getKey().equals("Квалификационная работа"))
                .flatMap(x -> x.getValue().stream()
                .max(Comparator.comparing(Subject::getSemester)).stream())
                .anyMatch(x -> x.getMark().equals(Mark.GREAT));
        return (countOfGreatMarks / countOfMarks) >= 0.75
                && !isBadMark && isQualificationWorkMarkGreat;
    }

    /**
     * Method that finds out if a student can get increased scholarship
     *      for certain semester.
     *
     * @param semester semester number
     * @return possibility to get increased scholarship
     */
    public boolean increasedScholarship(Integer semester) {
        Supplier<Stream<Subject>> stream = () -> subjects.values().stream().flatMap(Collection::stream)
                .filter(x -> x.getSemester().equals(semester)).filter(x -> x.getMark() != null);
        if (stream.get().findAny().isEmpty()) {
            throw new IllegalArgumentException("This student has no grades in current semester");
        }
        long countOfGoodMarks = stream.get()
               .filter(x -> x.getMark().equals(Mark.GOOD)).count();
        long countOfGreatMarks = stream.get()
                .filter(x -> x.getMark().equals(Mark.GREAT)
                || x.getMark().equals(Mark.PASS)).count();
        long countOfMarks = stream.get()
                .filter(x -> x.getSemester().equals(semester)).count();
        if (countOfMarks == 0) {
            throw new IllegalArgumentException("This student has no grades in current semester");
        }
        return countOfGoodMarks <= 1 && countOfMarks <= countOfGoodMarks + countOfGreatMarks;
    }

    /**
     * Method that creates file with statistic about student.
     *
     * <p>It contains:
     * <ul>
     *     <li>Student name</li>
     *     <li>Student's average mark</li>
     *     <li>Student's diploma average mark</li>
     *     <li>Possibility to get a red diploma</li>
     *     <li>Possibilities to get increased scholarship up to a certain semester</li
     * </ul>
     *
     * @param fileName name of output file
     * @param pathName path where file will be created
     * @param numOfSemesters the number of the semester before which you need to get statistics
     * @throws IOException if I/O error occurs
     */
    public void createGradeBookFile(String fileName, String pathName, Integer numOfSemesters) throws IOException {
        File file = new File(pathName, fileName);
        file.createNewFile();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(toString());
            for (int i = 1; i <= numOfSemesters; i++) {
                fw.write("Increased scholarship for " + i + " semester: " +
                        increasedScholarship(i) + "\n");
            }
        }
    }

    @Override
    public String toString() {
        return "Student: " + getStudentName() + "\n" +
                "Average mark: " + getAverageMark() + "\n" +
                "Average diploma mark: " + getAverageDiplomaMark() + "\n" +
                "Is a red diploma possible: " + redDiploma() + "\n";
    }
}