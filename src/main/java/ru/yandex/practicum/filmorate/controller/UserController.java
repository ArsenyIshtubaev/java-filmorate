package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IsInStorageException;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Set<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException, IsInStorageException {
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

    //GET .../users/{id}
    @GetMapping("/{id}")
    public User findUser(@PathVariable Long id) throws StorageException {
        return userService.findUserById(id);
    }

    //PUT /users/{id}/friends/{friendId}
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) throws StorageException {
        userService.addFriend(id, friendId);
    }

    // DELETE /users/{id}/friends/{friendId}
    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable Long id, @PathVariable Long friendId) throws StorageException {
        userService.removeFriend(id, friendId);
    }

    //GET /users/{id}/friends
    @GetMapping("/{id}/friends")
    public List<User> findFriends(@PathVariable Long id) throws StorageException {
        return userService.findAllFriends(id);
    }

    //GET /users/{id}/friends/common/{otherId}
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable Long id, @PathVariable Long otherId) throws StorageException {
        return userService.printCommonFriends(id, otherId);
    }
}
