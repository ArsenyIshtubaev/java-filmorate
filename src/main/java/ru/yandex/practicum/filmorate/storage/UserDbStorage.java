package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exceptions.IsInStorageException;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public class UserDbStorage implements UserStorage{
    @Override
    public Set<User> findAll() {
        return null;
    }

    @Override
    public User create(User user) throws IsInStorageException {
        return null;
    }

    @Override
    public User update(User user) throws StorageException {
        return null;
    }
}
