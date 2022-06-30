package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Set<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) throws IsInStorageException, ValidationException {
        validate(user);
        return userStorage.create(user);
    }

    public User update(User user) throws StorageException, ValidationException {
        validate(user);
        return userStorage.update(user);
    }

    public void deleteAllUsers(){
        userStorage.findAll().clear();
    }

    public void addFriend(Long userId, Long friendId) throws StorageException {

        if (findUserById(userId).getFriends() == null) {
            findUserById(userId).setFriends(new HashMap<>());
        }
        if (findUserById(friendId).getFriends() == null) {
            findUserById(friendId).setFriends(new HashMap<>());
        }
        findUserById(userId).getFriends().put(friendId, true);
        findUserById(friendId).getFriends().put(userId, true);
        userStorage.update(findUserById(userId));
        userStorage.update(findUserById(friendId));
    }

    public void removeFriend(Long userId, Long friendId) throws StorageException {
        findUserById(userId).getFriends().remove(friendId);
        findUserById(friendId).getFriends().remove(userId);
        userStorage.update(findUserById(userId));
        userStorage.update(findUserById(friendId));
    }

    public List<User> printCommonFriends(Long id1, Long id2) throws StorageException {
        List<User> listCommonFriends = new ArrayList<>();
        Set<Long> commonFriends = new HashSet<>();
        if (findUserById(id1).getFriends() != null) {
            commonFriends.addAll(findUserById(id1).getFriends());
            if (!findUserById(id2).getFriends().isEmpty()) {
                commonFriends.retainAll(findUserById(id2).getFriends());
            }
        } else if (findUserById(id2).getFriends() != null) {
            commonFriends.addAll(findUserById(id2).getFriends());
        } else {
            return listCommonFriends;
        }
        for (Long friendId : commonFriends) {
            listCommonFriends.add(findUserById(friendId));
        }
        return listCommonFriends;
    }

    public void validate(User user) throws ValidationException {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getLogin().contains(" ") && !user.getLogin().isBlank()) {
            log.info("Пользователь не прошел валидацию.");
            throw new ValidationException("Пользователь не прошел валидацию.");
        }
    }

    public User findUserById(Long id) throws StorageException {
        return userStorage.findAll().stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new StorageException("Пользователя с id = " + id + " нет в хранилище"));
    }

    public List<User> findAllFriends(Long id) throws StorageException {
        List<User> friends = new ArrayList<>();
        if (findUserById(id).getFriends() == null) {
            return friends;
        }
        for (Long friendId : findUserById(id).getFriends()) {
            friends.add(findUserById(friendId));
        }
        return friends;
    }
}
