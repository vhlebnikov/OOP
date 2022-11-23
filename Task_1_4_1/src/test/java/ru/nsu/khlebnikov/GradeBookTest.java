package ru.nsu.khlebnikov;

import java.io.IOException;

public class GradeBookTest {
    public static void main(String[] args) throws IOException {
        GradeBook s1 = new GradeBook("Vadim Khlebnikov");
        s1.addSubjects("in2.txt");
        s1.printSubjects();
        System.out.println(s1.getAverageMark());
    }
}
