package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class FilmorateApplicationTests {

    @Autowired
    private FilmController filmController;

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
    }

    @Test
    public void shouldReturnFilmWithPost() {
        Film film = filmController.create(Film.builder().id(1l).name("film1").description("Описание")
                .releaseDate(LocalDate.of(2012, 12, 5))
                .duration(Duration.ofHours(2l))
                .build());
        assertEquals(film.getId(), 1l);
    }

    @Test
    public void shouldReturnFilmWithPut() {
        Film film = filmController.create(Film.builder().id(1l).name("film1").description("Описание")
                .releaseDate(LocalDate.of(2012, 12, 5))
                .duration(Duration.ofHours(2l))
                .build());
        film.setDescription("Описание2");
        Film film2 = filmController.update(film);
        assertEquals(film, film2);
        assertEquals(filmController.findAll().size(), 1);
    }

    @Test
    public void shouldReturnExceptionWithIncorreсtName() {
        Film film = filmController.create(Film.builder().id(1l).name(" ").description("Описание")
                .releaseDate(LocalDate.of(2012, 12, 5))
                .duration(Duration.ofHours(2l))
                .build());
        assertNull(film);
    }

    @Test
    public void shouldReturnExceptionWithLenghtDescription201() {
        StringBuilder str = new StringBuilder("");
        while (str.length() != 201) {
            str.append("r");
        }
        Film film = filmController.create(Film.builder().id(1l).name("film1").description(String.valueOf(str))
                .releaseDate(LocalDate.of(2012, 12, 5))
                .duration(Duration.ofHours(2l))
                .build());
        assertNull(film);
    }

    @Test
    public void shouldReturnFilmWithDate28_12_1895() {
        Film film = filmController.create(Film.builder().id(1l).name("film1").description("Описание")
                .releaseDate(LocalDate.of(1895, 12, 28))
                .duration(Duration.ofHours(2l))
                .build());
        assertEquals(film.getId(), 1l);
    }

    @Test
    public void shouldReturnNullWithDateBefore28_12_1895() {
        Film film = filmController.create(Film.builder().id(1l).name("film1").description("Описание")
                .releaseDate(LocalDate.of(1895, 11, 28))
                .duration(Duration.ofHours(2l))
                .build());
        assertNull(film);
    }

    @Test
    public void shouldReturnNullWithDurationZeroOrNegative() {
        Film film = filmController.create(Film.builder().id(1l).name("film1").description("Описание")
                .releaseDate(LocalDate.of(2012, 12, 28))
                .duration(Duration.ofHours(0l))
                .build());
        assertNull(film);
        Film film1 = filmController.create(Film.builder().id(2l).name("film2").description("Описание")
                .releaseDate(LocalDate.of(2012, 11, 28))
                .duration(Duration.ofHours(-100l))
                .build());
        assertNull(film1);
    }


}
