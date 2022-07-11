package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.Collection;

@Slf4j
@Service
public class LikeService {
    private final LikeStorage likeStorage;

    @Autowired
    public LikeService(LikeStorage likeStorage) {
        this.likeStorage = likeStorage;
    }

    public void addLike(long filmId, long userId){
        likeStorage.addLike(filmId, userId);
    }
    public boolean deleteLike(long filmId, long userId) throws StorageException {
        return likeStorage.deleteLike(filmId,userId);
    }

    public Collection<Film> printTopFilms(int count){
        return likeStorage.printTopFilms(count);
    }
}
