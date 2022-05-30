package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController extends Controller<Film> {

    private final Set<Film> films = new HashSet<>();

    @GetMapping
    public Set<Film> findAll() {
        return films;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Получен запрос к эндпоинту: '{} {}', Название фильма: '{}'", "POST", "/films", film.getName());
        validation(film);
        films.add(film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Получен запрос к эндпоинту: '{} {}', Название фильма: '{}'", "PUT", "/films", film.getName());
        validation(film);
            for (Film film1 : films) {
                if (film1.getId() == film.getId()) {
                    films.remove(film1);
                    films.add(film);
                    return film;
                }
            }
        throw new ValidationException("Параметры фильма не прошли валидацию.");
    }

    private void validation (Film film) throws ValidationException {
        if (film.getId() == null) {
            film.setId(films.size() + 1l);
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))){
            //film.getDuration().isNegative() || film.getDuration().isZero()) {
            log.info("Параметры фильма не прошли валидацию.");
            throw new ValidationException("Параметры фильма не прошли валидацию.");
        }
        /*if (film.getName().isBlank() || film.getName() == null || film.getDescription().length() > 200 ||
                film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)) ||
                film.getDuration()<=0){
                //film.getDuration().isNegative() || film.getDuration().isZero()) {
            log.info("Параметры фильма не прошли валидацию.");
            throw new ValidationException("Параметры фильма не прошли валидацию.");
        } */
    }

}

