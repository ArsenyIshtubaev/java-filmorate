package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IsInStorageException;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) throws ValidationException {
        validate(film);
        return filmStorage.create(film);
    }

    public Film update(Film film) throws ValidationException, StorageException {
        validate(film);
        return filmStorage.update(film);
    }

     public void deleteAllFilms(){
        filmStorage.deleteAll();
    }
    public Film findFilmById(long id) throws StorageException {
        return filmStorage.findById(id);
    }
    public boolean deleteFilmById(long id) {
        return filmStorage.delete(id);
    }

    public void validate(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.info("Параметры фильма не прошли валидацию.");
            throw new ValidationException("Параметры фильма не прошли валидацию.");
        }
    }

}
