package ru.nsu.khlebnikov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GradeBookTest {

    @Test
    public void myGradeBookTest() throws IOException {
        GradeBook gradeBook = new GradeBook("Хлебников Вадим Дмитриевич");
        gradeBook.addSubjects("myGradeBook.txt");
        gradeBook.createGradeBookFile("myGradeBook_output.txt", 2);
        Assertions.assertEquals("4.4", gradeBook.getAverageMark());
        Assertions.assertEquals("4.4", gradeBook.getAverageRedDiplomaMark());
        Assertions.assertEquals("нет", gradeBook.redDiploma());
        Assertions.assertEquals("нет", gradeBook.increasedScholarship("1"));
        Assertions.assertEquals("нет", gradeBook.increasedScholarship("2"));
    }

    @Test
    public void incompleteData() throws IOException {
        GradeBook gradeBook = new GradeBook("Фамилия Имя Отчество");
        gradeBook.addSubjects("incompleteGradeBook.txt");
        gradeBook.createGradeBookFile("incompleteGradeBook_output.txt", 2);
        Assertions.assertEquals("0.0", gradeBook.getAverageMark());
        Assertions.assertEquals("0.0", gradeBook.getAverageRedDiplomaMark());
        Assertions.assertEquals("неверно введены данные", gradeBook.redDiploma());
        Assertions.assertEquals("недостаточно информации", gradeBook.increasedScholarship("1"));
        Assertions.assertEquals("неверно введены данные", gradeBook.increasedScholarship("2"));
    }

    @Test
    public void smartStudentGradeBook() throws IOException {
        GradeBook gradeBook = new GradeBook("Андреев Борис Вячеславович");
        gradeBook.addSubjects("smartStudentGradeBook.txt");
        gradeBook.createGradeBookFile("smartStudentGradeBook_output.txt", 2);
        Assertions.assertEquals("5.0", gradeBook.getAverageMark());
        Assertions.assertEquals("5.0", gradeBook.getAverageRedDiplomaMark());
        Assertions.assertEquals("да", gradeBook.redDiploma());
        Assertions.assertEquals("NSU+", gradeBook.increasedScholarship("1"));
        Assertions.assertEquals("NSU++", gradeBook.increasedScholarship("2"));
    }
}