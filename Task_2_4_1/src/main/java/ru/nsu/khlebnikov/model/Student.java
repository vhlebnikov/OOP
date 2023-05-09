package ru.nsu.khlebnikov.model;

import lombok.Data;
import lombok.SneakyThrows;

import java.net.URL;

@Data
public class Student {
    private final String nickname;
    private final String name;
    private final String surname;
    private final String patronymic;
    private final URL url;

    @SneakyThrows
    public Student(String nickname, String name, String surname, String patronymic, String url) {
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.url = new URL(url);
    }
}
