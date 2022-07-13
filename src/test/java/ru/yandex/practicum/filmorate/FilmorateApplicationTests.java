package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.MPAService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.*;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

    private final UserService userService;
    private final FilmService filmService;
    private final MPAService mpaService;
    private final GenreService genreService;

    @Test
    public void testUserStorage() throws StorageException, ValidationException {
        User user = userService.create(User.builder().email("user@yandex.ru").login("login")
                .name("Ivan")
                .birthday(LocalDate.of(1992, 9, 20))
                .build());
        assertEquals(userService.findUserById(1), user);
        assertEquals(user.getEmail(), "user@yandex.ru");
        assertEquals(userService.findAll().size(), 1);
        user.setEmail("ya123@ya.ru");
        userService.update(user);
        assertEquals("ya123@ya.ru", userService.findUserById(user.getId()).getEmail());
        userService.deleteUserById(user.getId());
    }

    @Test
    public void testFilmStorage() throws StorageException, ValidationException {
        Film film = filmService.create(Film.builder().name("film1").description("Описание")
                .releaseDate(LocalDate.of(2012, 12, 5))
                .duration(2)
                .mpa(mpaService.findMPAById(1))
                .build());
        assertEquals(filmService.findFilmById(1l), film);
        assertEquals(film.getName(), "film1");
        assertEquals(filmService.findAll().size(), 1);
        film.setName("film2");
        filmService.update(film);
        assertEquals("film2", filmService.findFilmById(film.getId()).getName());
    }
    @Test
    public void testMPAStorage() throws StorageException {
        assertEquals(mpaService.findMPAById(1).getName(), "G");
        assertEquals(mpaService.findAll().size(), 5);
    }
    @Test
    public void testGenresStorage() throws StorageException {
        assertEquals(genreService.findGenreById(1).getName(), "Комедия");
        assertEquals(genreService.findAll().size(), 6);
    }
}
