package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.IsInStorageException;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public interface UserStorage {

    Collection<User> findAll();

    User create(User user);

    User update(User user) throws StorageException;

    User findById(long id) throws StorageException;

    boolean delete(long id);

    void deleteAll();

}
