package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class GenreService {
    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Collection<Genre> findAll() throws StorageException {
        return genreStorage.findAll();
    }

    public Genre create(Genre genre) {
        return genreStorage.create(genre);
    }

    public Genre update(Genre genre) throws StorageException {
        if (findAll().contains(genre)) {
            return genreStorage.update(genre);
        } else {
            throw new StorageException("Данного жанра нет в БД");
        }
    }

    public void deleteAllGenre() {
        genreStorage.deleteAll();
    }

    public Genre findGenreById(Integer id) throws StorageException {
        return genreStorage.findById(id);
    }

    public boolean deleteGenreById(Integer id) {
        return genreStorage.delete(id);
    }

    public List<Genre> findGenreByFilmId(long filmId){
        return genreStorage.findGenreByFilmId(filmId);
    }
}
