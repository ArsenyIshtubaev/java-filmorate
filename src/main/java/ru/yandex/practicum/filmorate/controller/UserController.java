package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController extends Controller<User> {

    private final Set<User> users = new HashSet<>();

    @GetMapping
    public Set<User> findAll() {
        return users;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        log.info("Получен запрос к эндпоинту: '{} {}', Пользователь: Логин: {} и Email: {}", "POST", "/users",
                user.getLogin(), user.getEmail());
        validate(user);
        users.add(user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
        log.info("Получен запрос к эндпоинту: '{} {}', Пользователь: Логин: {} и Email: {}", "PUT", "/users",
                user.getLogin(), user.getEmail());
        validate(user);
        for (User user1 : users) {
            if (user1.getId() == user.getId()) {
                users.remove(user1);
                users.add(user);
                return user;
            }
        }
        throw new ValidationException("Пользователь не прошел валидацию.");
    }
    @Override
    public void validate(User user) throws ValidationException {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getId() == null) {
            user.setId(users.size() + 1l);
        }
        if (user.getLogin().contains(" ") && !user.getLogin().isBlank()) {
            log.info("Пользователь не прошел валидацию.");
            throw new ValidationException("Пользователь не прошел валидацию.");
        }
        /*if (user.getEmail().isBlank() || user.getEmail() == null || !user.getEmail().contains("@") ||
                user.getLogin().isBlank() || user.getLogin().contains(" ") ||
                user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Пользователь не прошел валидацию.");
            throw new ValidationException("Пользователь не прошел валидацию.");
        }*/
    }
}
