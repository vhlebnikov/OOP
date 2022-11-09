package ru.nsu.khlebnikov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubStringFinder {

    public static List<Integer> findSubString(String fileName, String subString) throws IOException, NullPointerException {
        try {
            File file = new File("src/test/resources/"+ fileName);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr); // interface Closable, method Close, try with resources
            String inputString = reader.readLine(); // read

            int lengthOfSubString = subString.length();
            int lengthOfInputString = inputString.length();
            List<Integer> arrayOfIndexes = new ArrayList<>();

            for (int i = 0; i < lengthOfInputString; i++) {
                if (inputString.charAt(i) == subString.charAt(0) && i + lengthOfSubString <= lengthOfInputString) {
                    if (subString.equals(inputString.substring(i, i + lengthOfSubString))) {
                        arrayOfIndexes.add(i);
                    }
                }
            }
            return arrayOfIndexes;
        } catch (IOException e) {
            throw new IOException("Can't open the file");
        } catch (NullPointerException e) {
            throw new NullPointerException("File is empty");
        }
    }
}