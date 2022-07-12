package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exceptions.StorageException;

public interface LikeStorage {
    void addLike(long filmId, long userId);
    boolean deleteLike(long filmId, long userId) throws StorageException;

    Integer countLikeByFilm (long filmId);

}
