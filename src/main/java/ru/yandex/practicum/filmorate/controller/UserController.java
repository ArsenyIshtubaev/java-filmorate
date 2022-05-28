package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final Set<User> users = new HashSet<>();

    @GetMapping
    public Set<User> findAll() {
        return users;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        log.info("Получен запрос к эндпоинту: '{} {}', Пользователь: Логин: {} и Email: {}", "POST", "/users",
                user.getLogin(), user.getEmail());
        if (validation(user)) {
            users.add(user);
            return user;
        }
        return null;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        log.info("Получен запрос к эндпоинту: '{} {}', Пользователь: Логин: {} и Email: {}", "PUT", "/users",
                user.getLogin(), user.getEmail());
        if (validation(user)) {
            for (User user1 : users) {
                if (user1.getId() == user.getId()) {
                    users.remove(user1);
                    users.add(user);
                    return user;
                }
            }
            users.add(user);
            return user;
        }
        return null;
    }

    private boolean validation(User user) {
        if (user.getEmail().isBlank() || user.getEmail() == null || !user.getEmail().contains("@") ||
                user.getLogin().isBlank() || user.getLogin().contains(" ") ||
                user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Пользователь не прошел валидацию.");
            try {
                throw new ValidationException("Пользователь не прошел валидацию.");
            } catch (ValidationException e) {
                e.getMessage();
            }
            return false;
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return true;
    }
}
