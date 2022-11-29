package ru.nsu.khlebnikov;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class for creating big file with 10 GB size.
 */
public class FileCreator {

    /**
     * Method that creates big file for test for subStringFinder method.
     *
     * @param fileName - name of file that will be created
     * @throws IOException - if an I/O error occurs
     */
    public static void createFile(String fileName) throws IOException {
        File file = new File("src/test/resources/", fileName);
        if (file.exists()) {
            throw new FileAlreadyExistsException("File already exists");
        }
        try (PrintWriter pw = new PrintWriter(file)) {
            for (int j = 0; j < 12; j++) {
                pw.print("0");
            }
            pw.print("aba");
            for (int j = 16; j < 1000; j++) {
                pw.print("0");
            }
            pw.println();
            for (int i = 0; i < 10000000; i++) {
                for (int j = 0; j < 1000; j++) {
                    pw.print("0");
                }
                pw.println();
            }
            for (int j = 0; j < 12; j++) {
                pw.print("0");
            }
            pw.print("aba");
            for (int j = 16; j < 1000; j++) {
                pw.print("0");
            }
            pw.println();
        }
    }

    /**
     * Method that deletes file by its name.
     *
     * @param fileName - name of file that will be deleted
     * @throws IOException - if an I/O error occurs
     */
    public static void deleteFile(String fileName) throws IOException {
        Path path = Paths.get("src/test/resources/" + fileName);
        if (Files.notExists(path)) {
            throw new FileNotFoundException("File doesn't exist");
        }
        Files.delete(path);
    }
}
