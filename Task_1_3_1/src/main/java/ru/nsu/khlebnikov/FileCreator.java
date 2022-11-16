package ru.nsu.khlebnikov;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class for creating big file with 10 GB size.
 */
public class FileCreator {

    /**
     * Method that creates big file for test for subStringFinder method.
     *
     * @param fileName - name of file that will be created
     * @throws IOException - if an I/O error occurs
     * @throws IllegalArgumentException - exception that called if file is already exists
     */
    public static void createFile(String fileName) throws IOException, IllegalArgumentException {
        File file = new File("src/test/resources/", fileName);
        if (!file.exists()) {
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
            } catch (IOException e) {
                throw new IOException();
            }
        } else {
            throw new IllegalArgumentException("File is already exist");
        }
    }
}
