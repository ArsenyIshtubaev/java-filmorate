package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendStorage {

    void addFriend(long userId, long friendId) throws StorageException;
    boolean deleteFriend(long userId, long friendId);
   // Collection<User> printCommonFriends(long userId, long friendId);
    Collection<Long> findAllIdFriends(long userId);
}
