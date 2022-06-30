package ru.yandex.practicum.filmorate.enums;

public enum MPA {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private final String mpa;

    MPA(String mpa){
        this.mpa=mpa;
    }

}
