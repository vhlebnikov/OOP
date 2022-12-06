package ru.nsu.khlebnikov;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Calculator {

    public static Double calculate(InputStream stream) throws IOException {
        stream = System.in; // Mock stream
        Scanner sc = new Scanner(stream);
        String input;
        List<String> operands = new ArrayList<>();
        if ((input = sc.nextLine()) == null) {
            throw new IOException("Пустая строка ввода");
        }
        operands.addAll(Arrays.asList(input.split(" ")));
        for (int i = 0; i < input.split(" ").length; i++) {
            if (operands.get(0).charAt(0) == )
        }
        return null;
    }
}

class Main {
    public static void main(String[] args) throws IOException {
        Calculator.calculate(System.in);
    }
}
