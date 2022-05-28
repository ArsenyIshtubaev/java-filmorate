package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private final Long id;
    private String email;
    private String login;
    private String name;
    private final LocalDate birthday;

}
