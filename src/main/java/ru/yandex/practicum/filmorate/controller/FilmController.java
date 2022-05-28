package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Request;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final List<Film> films = new ArrayList<>();

    @GetMapping
    public List<Film> findAll() {
        return films;
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: '{} {}', Название фильма: '{}'", "POST", "/films", film.getName());
        if (validation(film)) {
            films.add(film);
            return film;
        }
        return null;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: '{} {}', Название фильма: '{}'", "PUT", "/films", film.getName());
        if (validation(film)) {
            for (Film film1 : films) {
                if (film1.getId() == film.getId()) {
                    films.remove(film1);
                    films.add(film);
                    return film;
                }
            }
            films.add(film);
            return film;
        }
        return null;
    }

    private boolean validation (Film film) {
        if (film.getName().isBlank() || film.getName() == null || film.getDescription().length() > 200 ||
                film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)) ||
                film.getDuration().isNegative() || film.getDuration().isZero()) {
            log.info("Параметры фильма не прошли валидацию.");

            try {
                throw new ValidationException("Параметры фильма не прошли валидацию.");
            } catch (ValidationException e) {
                e.getMessage();
            }
            return false;
        }
        return true;
    }

}

