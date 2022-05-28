package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    private final Long id;
    private final String name;
    private String description;
    private final LocalDate releaseDate;
    private Duration duration;

}
