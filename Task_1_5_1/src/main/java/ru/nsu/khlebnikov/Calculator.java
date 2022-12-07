package ru.nsu.khlebnikov;

import java.io.*;
import java.util.*;

public class Calculator {

    public static Double calculate(InputStream stream) throws IOException {
        stream = System.in; // Mock stream
        Scanner sc = new Scanner(stream);
        String input;
        if ((input = sc.nextLine()) == null) {
            throw new IOException("Пустое выражение");
        }
        int numberIndex = 0;
        List<String> elements = new ArrayList<>(Arrays.asList(input.split(" ")));
        Deque<String> operations = new ArrayDeque<>();

        for (String elem : elements) {
            if (Character.isDigit(elem.charAt(0))) {
                break;
            }
            numberIndex++;
            operations.push(elem);
        }

        for (int i = numberIndex; i < elements.size(); i++) {
            
        }
        return null;
    }

    public boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }
}

class Main {
    public static void main(String[] args) throws IOException {
        Calculator.calculate(System.in);
    }
}
