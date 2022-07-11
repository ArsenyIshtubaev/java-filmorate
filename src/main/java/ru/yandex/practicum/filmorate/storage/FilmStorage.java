package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exceptions.IsInStorageException;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {

    Collection<Film> findAll();

    Film create(Film film);

    Film update(Film film) throws StorageException;

    Film findById(long id) throws StorageException;

    boolean delete(long id);

    void deleteAll();

}
