package ru.yandex.practicum.filmorate.enums;

public enum Genre {

    Comedy("Комедия"),
    Drama("Драмма"),
    Cartoon("Мультфильм"),
    Thriller("Триллер"),
    Documentary("Документальный"),
    Action("Боевик");

    private final String genre;

    Genre(String genre) {
        this.genre = genre;
    }
}
