package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IsInStorageException;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.Set;

@Component
public class InMemoryUserStorage implements UserStorage {

    private Set<User> users = new HashSet<>();
    private Long id = 0l;

    @Override
    public Set<User> findAll() {
        return users;
    }

    @Override
    public User create(User user) throws IsInStorageException {
        if (users.contains(user)) {
            throw new IsInStorageException(String.format("Пользователь c email \"%s\" есть в базе", user.getEmail()));
        } else {
            if (user.getId() == null) {
                user.setId(generatedID());
            }
            users.add(user);
            return user;
        }
    }

    @Override
    public User update(User user) throws StorageException {

        for (User user1 : users) {
            if (user1.getId() == user.getId()) {
                users.remove(user1);
                users.add(user);
                return user;
            }
        }
        throw new StorageException(String.format("Пользователь c email \"%s\" не найден", user.getEmail()));
    }

    private Long generatedID() {
        id++;
        return id;
    }
}
