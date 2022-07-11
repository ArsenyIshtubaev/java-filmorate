package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

@Component
public class LikeDbStorage implements LikeStorage{

    private final JdbcTemplate jdbcTemplate;
    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;


    public LikeDbStorage(JdbcTemplate jdbcTemplate, FilmDbStorage filmDbStorage, UserDbStorage userDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmDbStorage = filmDbStorage;
        this.userDbStorage = userDbStorage;
    }

    @Override
    public void addLike(long filmId, long userId) {
        String sqlQuery = "insert into LIKES (FILM_ID, USER_ID) values (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public boolean deleteLike(long filmId, long userId) throws StorageException {
        String sqlQuery1 = "select * from FILMS where FILM_ID = ?";
        List<Film> films = jdbcTemplate.query(sqlQuery1, filmDbStorage::makeFilm, filmId);
        if (films.size() != 1) {
            throw new StorageException("Фильма с таким id нет в базе данных");
        }
        String sqlQuery2 = "select * from USERS where USER_ID = ?";
        List<User> users = jdbcTemplate.query(sqlQuery2, userDbStorage::makeUser, userId);
        if (users.size() != 1) {
            throw new StorageException("Пользователя с таким id нет в базе данных");
        }
        String sqlQuery = "delete from LIKES where FILM_ID = ? AND USER_ID = ?";
       return jdbcTemplate.update(sqlQuery, filmId, userId) > 0;
    }

    @Override
    public Collection<Film> printTopFilms(int count) {

       String sqlQuery = "select f.FILM_ID, f.FILM_NAME, f.DESCRIPTION, f.RELEASE_DATE, f.DURATION, f.MPA_ID "+
                " from FILMS as f LEFT JOIN LIKES as l ON f.FILM_ID = l.FILM_ID "
                +"GROUP BY f.FILM_ID ORDER BY COUNT(USER_ID) DESC LIMIT ?;";
        List<Film> films = jdbcTemplate.query(sqlQuery, filmDbStorage::makeFilm, count);
        return films;
    }
}
