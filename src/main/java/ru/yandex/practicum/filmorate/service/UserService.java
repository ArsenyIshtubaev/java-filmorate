package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IsInStorageException;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }
    public Collection<User> findAll() {
        return userStorage.findAll();
    }
    public User create(User user) throws ValidationException {
        validate(user);
        return userStorage.create(user);
    }
    public User update(User user) throws ValidationException, StorageException {
        validate(user);
        return userStorage.update(user);
    }
    public void deleteAllUsers(){
        userStorage.deleteAll();
    }

    public User findUserById(long id) throws StorageException {
        return userStorage.findById(id);
    }
    public boolean deleteUserById(long id){
        return userStorage.delete(id);
    }
    public void validate(User user) throws ValidationException {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getLogin().contains(" ") && !user.getLogin().isBlank())  {
            log.info("Пользователь не прошел валидацию.");
            throw new ValidationException("Пользователь не прошел валидацию.");
        }
    }

}
