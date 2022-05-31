package ru.yandex.practicum.filmorate;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmorateApplicationTests {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private FilmController filmController;

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
    }

    @Test
    public void shouldReturnFilmWithPost() throws ValidationException {
        Film film = filmController.create(Film.builder().id(1l).name("film1").description("Описание")
                .releaseDate(LocalDate.of(2012, 12, 5))
                .duration(2)
                .build());
        assertEquals(film.getId(), 1l);
    }

    @Test
    public void shouldReturnFilmWithPut() throws ValidationException {
        Film film = filmController.create(Film.builder().id(1l).name("film1").description("Описание")
                .releaseDate(LocalDate.of(2012, 12, 5))
                .duration(2)
                .build());
        film.setDescription("Описание2");
        Film film2 = filmController.update(film);
        assertEquals(film, film2);
        assertEquals(filmController.findAll().size(), 1);
    }

    @Test
    public void shouldReturnExceptionWithIncorreсtName() throws ValidationException {
        Film film = filmController.create(Film.builder().id(1l).name(" ").description("Описание")
                    .releaseDate(LocalDate.of(2012, 12, 5))
                    .duration(2)
                    .build());
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldReturnExceptionWithLenghtDescription201() throws ValidationException {
        StringBuilder str = new StringBuilder("");
        while (str.length() != 201) {
            str.append("r");
        }
        Film film = filmController.create(Film.builder().id(1l).name(" ").description(String.valueOf(str))
                    .releaseDate(LocalDate.of(2012, 12, 5))
                    .duration(2)
                    .build());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldReturnFilmWithDate28_12_1895() throws ValidationException {
        Film film = filmController.create(Film.builder().id(1l).name("film1").description("Описание")
                .releaseDate(LocalDate.of(1895, 12, 28))
                .duration(2)
                .build());
        assertEquals(film.getId(), 1l);
    }

    @Test
    public void shouldReturnExceptionWithDateBefore28_12_1895() throws ValidationException {
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmController.create(Film.builder().id(1l).name(" ").description("Описание")
                    .releaseDate(LocalDate.of(1895, 11, 5))
                    .duration(2)
                    .build());
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void shouldReturnExceptionWithDurationZero() throws ValidationException {
        Film film = filmController.create(Film.builder().id(1l).name(" ").description("Описание")
                    .releaseDate(LocalDate.of(2012, 12, 5))
                    .duration(0)
                    .build());
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            assertFalse(violations.isEmpty());
    }
    @Test
    public void shouldReturnExceptionWithDurationNegative() throws ValidationException {
        Film film = filmController.create(Film.builder().id(1l).name(" ").description("Описание")
                .releaseDate(LocalDate.of(2012, 12, 5))
                .duration(-2)
                .build());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldReturnUserWithPost() throws ValidationException {
        User user = userController.create(User.builder().id(1l).email("user@yandex.ru").login("login")
                .name("Ivan")
                .birthday(LocalDate.of(1992, 9, 20))
                .build());
        assertEquals(user.getId(), 1l);
    }

    @Test
    public void shouldReturnUserWithPut() throws ValidationException {
        User user = userController.create(User.builder().id(1l).email("user@yandex.ru").login("login")
                .name("Ivan")
                .birthday(LocalDate.of(1992, 9, 20))
                .build());
        user.setLogin("login1");
        User user1 = userController.update(user);
        assertEquals(user, user1);
        assertEquals(userController.findAll().size(), 1);
    }

    @Test
    public void shouldReturnExceptionWithEmptyEmail() throws ValidationException {
        User user = userController.create(User.builder().id(1l).email(" ").login("login")
                    .name("Ivan")
                    .birthday(LocalDate.of(1992, 9, 20))
                    .build());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }
    @Test
    public void shouldReturnExceptionWithIncorreсtEmail() throws ValidationException {
            User user = userController.create(User.builder().id(1l).email("user.yandex.ru").login("login")
                    .name("Ivan")
                    .birthday(LocalDate.of(1992, 9, 20))
                    .build());
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            assertFalse(violations.isEmpty());
    }
    @Test
    public void shouldReturnExceptionWithIncorreсtLogin() throws ValidationException {
            User user = userController.create(User.builder().id(1l).email("user@yandex.ru").login(" ")
                    .name("Ivan")
                    .birthday(LocalDate.of(1992, 9, 20))
                    .build());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }
    @Test
    public void shouldReturnExceptionWithIncorreсtLogin2() throws ValidationException {
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userController.create(User.builder().id(1l).email("user@yandex.ru").login("log in ")
                            .name("Ivan")
                            .birthday(LocalDate.of(1992, 9, 20))
                            .build());
        });
        assertNotNull(thrown.getMessage());
    }
    @Test
    public void shouldReturnLoginWithEmptyName() throws ValidationException {
        User user = userController.create(User.builder().id(1l).email("user@yandex.ru").login("login")
                .name("")
                .birthday(LocalDate.of(1992, 9, 20))
                .build());
        assertEquals(user.getName(), user.getLogin());
    }
    @Test
    public void shouldReturnNullWithIncorreсtBirthday() throws ValidationException {
        User user = userController.create(User.builder().id(1l).email("user@yandex.ru").login("login")
                    .name("Ivan")
                    .birthday(LocalDate.of(2100, 9, 20))
                    .build());
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            assertFalse(violations.isEmpty());
    }

}
