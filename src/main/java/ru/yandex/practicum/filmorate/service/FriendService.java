package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.util.Collection;

@Service
public class FriendService {

    private final FriendStorage friendStorage;

    @Autowired
    public FriendService(FriendStorage friendStorage) {
        this.friendStorage = friendStorage;
    }

    public void addFriend(long userId, long friendId) throws StorageException {
            friendStorage.addFriend(userId, friendId);
    }

    public boolean deleteFriend(long userId, long friendId){
        return friendStorage.deleteFriend(userId, friendId);
    }

   /* public Collection<User> printCommonFriends(long userId, long friendId) {
        return friendStorage.printCommonFriends(userId, friendId);
    } */

    public Collection<Long> findAllIdFriends(long userId){
        return friendStorage.findAllIdFriends(userId);
    }

}
