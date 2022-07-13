package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;

public interface MPAStorage {

    Collection<MPA> findAll();
    MPA create(MPA mpa);
    MPA update(MPA mpa) throws StorageException;
    MPA findById(Integer id) throws StorageException;
    boolean delete(Integer id);
    void deleteAll();
}
