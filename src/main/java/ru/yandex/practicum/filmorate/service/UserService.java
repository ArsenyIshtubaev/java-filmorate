package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;
    private final FriendService friendService;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage, FriendService friendService) {
        this.userStorage = userStorage;
        this.friendService = friendService;
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
        if (findAll().contains(user)) {
            return userStorage.update(user);
        } else {
            throw new StorageException("Данного пользователя нет в БД");
        }
    }

    public void deleteAllUsers() {
        userStorage.deleteAll();
    }

    public User findUserById(long id) throws StorageException {
        return userStorage.findById(id);
    }

    public boolean deleteUserById(long id) {
        return userStorage.delete(id);
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

    public Collection<User> findAllFriends(long userId) {
        return friendService.findAllIdFriends(userId).stream()
                .map(id -> {
                    try {
                        return userStorage.findById(id);
                    } catch (StorageException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    public Collection<User> printCommonFriends(long userId, long friendId) {

        List<User> users1 = new ArrayList<>(findAllFriends(userId));

        List<User> users2 = new ArrayList<>(findAllFriends(friendId));
        users1.retainAll(users2);
        return users1;
    }
}

        //users1.retainAll(users2);

        /*String sqlQuery = "select fr1.FRIEND_ID from (select * from FRIENDSHIP where FRIENDSHIP.USER_ID = ? "+
                "AND FRIENDSHIP.FRIEND_STATUS = true) as fr1 "+
                "join (select * from FRIENDSHIP where FRIENDSHIP.USER_ID = ? " +
                "AND FRIENDSHIP.FRIEND_STATUS = true) as fr2 "+
                "ON fr1.FRIEND_ID = fr2.FRIEND_ID";
        List<User> users = jdbcTemplate.query(sqlQuery, userDbStorage::makeUser, userId, friendId);
        return users1;
} */
