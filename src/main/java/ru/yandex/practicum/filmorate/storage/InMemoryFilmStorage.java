package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IsInStorageException;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final List<Film> films = new ArrayList<>();
    private Long id = 0l;

    @Override
    public List<Film> findAll() {
        return films;
    }

    @Override
    public Film create(Film film) throws IsInStorageException {

        if (films.contains(film)) {
            throw new IsInStorageException(String.format("Фильм \"%s\" есть в базе", film.getName()));
        } else {
            if (film.getId() == null) {
                film.setId(generatedID());
            }
            films.add(film);
            return film;
        }
    }

    @Override
    public Film update(Film film) throws StorageException {
        for (Film film1 : films) {
            if (film1.getId() == film.getId()) {
                films.remove(film1);
                films.add(film);
                return film;
            }
        }
        throw new StorageException(String.format("Фильм \"%s\" не найден", film.getName()));
    }

    private Long generatedID() {
        id++;
        return id;
    }

}
