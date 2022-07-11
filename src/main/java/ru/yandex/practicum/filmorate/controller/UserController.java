package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final FriendService friendService;

    @Autowired
    public UserController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }
    @GetMapping
    public Collection<User> findAll() {
        return userService.findAll();
    }
    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        log.info("Получен запрос к эндпоинту: '{} {}', Пользователь: Логин: {} и Email: {}", "POST", "/users",
                user.getLogin(), user.getEmail());
        return userService.create(user);
    }
    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException, StorageException {
        log.info("Получен запрос к эндпоинту: '{} {}', Пользователь: Логин: {} и Email: {}", "PUT", "/users",
                user.getLogin(), user.getEmail());
        return userService.update(user);
    }
    @DeleteMapping
    public void deleteAllUsers(){
        userService.deleteAllUsers();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable long id) throws StorageException {
        log.info("Get user id={}", id);
        return userService.findUserById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id){
        userService.deleteUserById(id);
    }

    //PUT /users/{id}/friends/{friendId}
   @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable long userId, @PathVariable long friendId) throws StorageException {
        friendService.addFriend(userId, friendId);
    }

    // DELETE /users/{id}/friends/{friendId}
    @DeleteMapping("/{userId}/friends/{friendId}")
    public void removeFriend(@PathVariable long userId, @PathVariable long friendId) {
        friendService.deleteFriend(userId, friendId);
    }

    //GET /users/{id}/friends
    @GetMapping("/{userId}/friends")
    public Collection<User> findFriends(@PathVariable long userId) throws StorageException {
        return friendService.findAllFriends(userId);
    }

    //GET /users/{id}/friends/common/{otherId}
    @GetMapping("/{userId}/friends/common/{friendId}")
    public Collection<User> findCommonFriends(@PathVariable long userId, @PathVariable long friendId)
    {
        return friendService.printCommonFriends(userId, friendId);
    }
}
