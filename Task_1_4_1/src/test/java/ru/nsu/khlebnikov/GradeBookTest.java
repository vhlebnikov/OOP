package ru.nsu.khlebnikov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for my grade book realization.
 */
public class GradeBookTest {

    @Test
    public void myGradeBookTest() throws IOException {
        GradeBook gradeBook = new GradeBook("Хлебников Вадим Дмитриевич");
        gradeBook.addSubjects("myGradeBook.txt");
        gradeBook.createGradeBookFile("myGradeBook_output.txt", "src/test/resources", 2);
        Assertions.assertEquals(4.4, gradeBook.getAverageMark());
        Assertions.assertEquals(4.4, gradeBook.getAverageDiplomaMark());
        Assertions.assertFalse(gradeBook.redDiploma());
        Assertions.assertFalse(gradeBook.increasedScholarship(1));
        Assertions.assertFalse(gradeBook.increasedScholarship(2));
    }

    @Test
    public void exceptionTest() throws IOException {
        GradeBook gradeBook = new GradeBook("Фамилия Имя Отчество");
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> gradeBook.addSubjects("badInputGradeBook.txt"));
        assertEquals(exception.getMessage(), "Illegal string with data");
        Throwable exception1 = assertThrows(EnumConstantNotPresentException.class,
                () -> gradeBook.addSubjects("unknownMark.txt"));
        assertEquals(exception1.getMessage(), "ru.nsu.khlebnikov.Mark.Четыре");
        GradeBook gradeBook1 = new GradeBook("Фамилия Имя Отчество");
        gradeBook1.addSubjects("insufficientOfMarks.txt");
        Throwable exception2 = assertThrows(IllegalArgumentException.class,
                gradeBook1::getAverageMark);
        assertEquals(exception2.getMessage(), "This student has no grades");
    }

    @Test
    public void smartStudentGradeBook() throws IOException {
        GradeBook gradeBook = new GradeBook("Андреев Борис Вячеславович");
        gradeBook.addSubjects("smartStudentGradeBook.txt");
        gradeBook.createGradeBookFile("smartStudentGradeBook_output.txt", "src/test/resources", 2);
        Assertions.assertEquals(5.0, gradeBook.getAverageMark());
        Assertions.assertEquals(5.0, gradeBook.getAverageDiplomaMark());
        Assertions.assertTrue(gradeBook.redDiploma());
        Assertions.assertTrue(gradeBook.increasedScholarship(1));
        Assertions.assertTrue(gradeBook.increasedScholarship(2));
    }
}