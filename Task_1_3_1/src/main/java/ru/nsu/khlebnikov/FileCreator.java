package ru.nsu.khlebnikov;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
     * @param pathName - custom path of file
     * @throws IOException - if an I/O error occurs
     */
    public static void createFile(String fileName, String pathName) throws IOException {
        File file = new File(pathName, fileName);
        if (!file.createNewFile()) {
            throw new FileAlreadyExistsException("File already exists");
        }
        try (FileWriter fw = new FileWriter(file)) {
            for (int j = 0; j < 12; j++) {
                fw.write("0");
            }
            fw.write("aba");
            for (int j = 16; j < 1000; j++) {
                fw.write("0");
            }
            fw.write("\n");
            for (int i = 0; i < 10000000; i++) {
                for (int j = 0; j < 1000; j++) {
                    fw.write("0");
                }
            fw.write("\n");
        }
            for (int j = 0; j < 12; j++) {
                fw.write("0");
            }
            fw.write("aba");
            for (int j = 16; j < 1000; j++) {
                fw.write("0");
            }
            fw.write("\n");
        }
    }

    /**
     * Method that deletes file.
     *
     * @param fileName - name of file that will be deleted
     * @param pathName - custom path of file
     * @throws IOException - if an I/O error occurs
     */
    public static void deleteFile(String fileName, String pathName) throws IOException {
        Path path = Paths.get(pathName, fileName);
        if (Files.notExists(path)) {
            throw new FileNotFoundException("File doesn't exist");
        }
        Files.delete(path);
    }
}
