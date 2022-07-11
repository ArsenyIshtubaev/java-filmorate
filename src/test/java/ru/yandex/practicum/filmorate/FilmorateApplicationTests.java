package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.*;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private final MPAStorage mpaStorage;
    private final GenreStorage genreStorage;

    @Test
    public void testUserStorage() throws StorageException {
        User user = userStorage.create(User.builder().email("user@yandex.ru").login("login")
                .name("Ivan")
                .birthday(LocalDate.of(1992, 9, 20))
                .build());
        assertEquals(userStorage.findById(1), user);
        assertEquals(user.getEmail(), "user@yandex.ru");
        assertEquals(userStorage.findAll().size(), 1);
        user.setEmail("ya123@ya.ru");
        userStorage.update(user);
        assertEquals("ya123@ya.ru", userStorage.findById(user.getId()).getEmail());
        userStorage.delete(user.getId());
    }

    @Test
    public void testFilmStorage() throws StorageException {
        Film film = filmStorage.create(Film.builder().name("film1").description("Описание")
                .releaseDate(LocalDate.of(2012, 12, 5))
                .duration(2)
                .mpa(mpaStorage.findById(1))
                .build());
        assertEquals(filmStorage.findById(1l), film);
        assertEquals(film.getName(), "film1");
        assertEquals(filmStorage.findAll().size(), 1);
        film.setName("film2");
        filmStorage.update(film);
        assertEquals("film2", filmStorage.findById(film.getId()).getName());
    }
    @Test
    public void testMPAStorage() throws StorageException {
        assertEquals(mpaStorage.findById(1).getName(), "G");
        assertEquals(mpaStorage.findAll().size(), 5);
    }
    @Test
    public void testGenresStorage() throws StorageException {
        assertEquals(genreStorage.findById(1).getName(), "Комедия");
        assertEquals(genreStorage.findAll().size(), 6);
    }
}
