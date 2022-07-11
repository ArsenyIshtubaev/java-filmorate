package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;

public interface LikeStorage {
    void addLike(long filmId, long userId);
    boolean deleteLike(long filmId, long userId) throws StorageException;

    Collection<Film> printTopFilms (int count);
}
