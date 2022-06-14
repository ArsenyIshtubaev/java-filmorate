package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IsInStorageException;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) throws IsInStorageException, ValidationException {
        validate(film);
        return filmStorage.create(film);
    }

    public Film update(Film film) throws StorageException, ValidationException {
        validate(film);
        return filmStorage.update(film);
    }

    public void deleteAllFilms(){
        filmStorage.findAll().clear();
    }

    public void addLike(Long filmId, Long userId) throws StorageException {
        findFilmById(filmId).getLikes().add(userId);
        filmStorage.update(findFilmById(filmId));
    }

    public void removeLike(Long filmId, Long userId) throws StorageException {
        if (findFilmById(filmId).getLikes().contains(userId)) {
            findFilmById(filmId).getLikes().remove(userId);
            filmStorage.update(findFilmById(filmId));
        } else {
            throw new StorageException("Пользователь с Id = " + userId + " не ставил лайк этому фильму");
        }
    }

    public List<Film> printTopFilms(int count) {

        return filmStorage.findAll().stream()
                .sorted((p0, p1) -> compare(p0, p1))
                .limit(count)
                .collect(Collectors.toList());
    }

    public void validate(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.info("Параметры фильма не прошли валидацию.");
            throw new ValidationException("Параметры фильма не прошли валидацию.");
        }
    }

    public Film findFilmById(Long id) throws StorageException {
        return filmStorage.findAll().stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new StorageException("Фильма с таким id нет"));
    }

    private int compare(Film p0, Film p1) {
        return (p1.getLikes().size()) - (p0.getLikes().size());
    }

}
