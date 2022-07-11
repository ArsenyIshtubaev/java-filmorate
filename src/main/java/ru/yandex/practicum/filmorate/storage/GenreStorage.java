package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreStorage {

    Collection<Genre> findAll();

    Genre create(Genre genre);

    Genre update(Genre genre) throws StorageException;

    Genre findById(Integer id) throws StorageException;

    boolean delete(Integer id);

    void deleteAll();
}
