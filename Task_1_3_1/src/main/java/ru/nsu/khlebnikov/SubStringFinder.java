package ru.nsu.khlebnikov;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Primitive implementation of substring search in a string.
 * Uses BufferReader, because buffer manipulation is faster than disk manipulation.
 * This is due to the fact that writing/reading to disk causes system calls that are generally slow.
 */
public class SubStringFinder {

    /**
     * Method for finding substring.
     *
     * @param fileName  - file with string, where will be search of substring
     * @param subString - substring
     * @return - array of pairs with line number in the text and character number in this line
     *              (the count starts from 0, for readability it would be possible
     *              to rewrite the code by adding 1 to the elements of the answer)
     * @throws IOException          - exception that called if the input file can't be opened
     */
    public static List<Pair<Integer>> findSubString(String fileName, String subString)
            throws IOException {
        try (FileReader file = new FileReader("src/test/resources/" + fileName,
                StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(file)) {
            List<Pair<Integer>> arrayOfIndexes = new ArrayList<>();
            int lengthOfSubString = subString.length();

            int charCode;
            char letter;

            boolean suitableSubString;

            int indexOfCharInString = -1;
            int indexOfString = 0;

            while ((charCode = reader.read()) != -1) {
                if (charCode == 10) {
                    indexOfString++;
                    indexOfCharInString = -1;
                    continue;
                }
                letter = (char) charCode;
                suitableSubString = true;
                indexOfCharInString++;
                if (letter == subString.charAt(0)) {
                    reader.mark(lengthOfSubString);
                    for (int i = 1; i < lengthOfSubString; i++) {
                        if ((charCode = reader.read()) != -1) {
                            letter = (char) charCode;
                        } else {
                            suitableSubString = false;
                            break;
                        }
                        if (letter != subString.charAt(i)) {
                            suitableSubString = false;
                            reader.reset();
                            break;
                        }
                    }
                    if (suitableSubString) {
                        reader.reset();
                        arrayOfIndexes.add(Pair.create(indexOfString, indexOfCharInString));
                    }
                }
            }
            return arrayOfIndexes;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File doesn't exist");
        }
    }
}
