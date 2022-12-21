package ru.nsu.khlebnikov;

import java.util.Objects;

/**
 * Additional class for {@link GradeBook} class,
 * which contains semester and mark fields.
 */
public class Subject {
    private final Integer semester;
    private Mark mark;

    public Subject(Integer semester) {
        this.semester = semester;
    }

    public Subject(Integer semester, Mark mark) {
        this.semester = semester;
        this.mark = mark;
    }

    public Integer getSemester() {
        return semester;
    }

    public Mark getMark() {
        return mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subject subject = (Subject) o;
        return semester.equals(subject.semester) && Objects.equals(mark, subject.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semester, mark);
    }
}
