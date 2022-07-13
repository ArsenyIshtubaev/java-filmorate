package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final LikeService likeService;
    private final GenreService genreService;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage, LikeService likeService, GenreService genreService) {
        this.filmStorage = filmStorage;
        this.likeService = likeService;
        this.genreService = genreService;
    }

    public Collection<Film> findAll() throws StorageException {
        return filmStorage.findAll();
    }

    public Film create(Film film) throws ValidationException {
        validate(film);
        return filmStorage.create(film);
    }

    public Film update(Film film) throws ValidationException, StorageException {
        validate(film);
        if (findAll().contains(film)) {
            return filmStorage.update(film);
        } else {
            throw new StorageException("Данного пользователя нет в БД");
        }
    }

    public void deleteAllFilms() {
        filmStorage.deleteAll();
    }

    public Film findFilmById(long id) throws StorageException {
        Film film =filmStorage.findById(id);
        film.setGenres( genreService.findGenreByFilmId(film.getId()));
        return film;
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

    public Collection<Film> printTopFilms(int count) throws StorageException {
        return filmStorage.findAll().stream().sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }
    private int compare(Film p0, Film p1) {
        return (likeService.countLikeByFilm(p1.getId()) - likeService.countLikeByFilm(p0.getId()));
    }

}
