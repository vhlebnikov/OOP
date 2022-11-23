package ru.nsu.khlebnikov;

import java.util.Objects;

public class Subject {
    private String semester;
    private String mark;

    public Subject() {}
    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return semester.equals(subject.semester) && Objects.equals(mark, subject.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semester, mark);
    }
}
