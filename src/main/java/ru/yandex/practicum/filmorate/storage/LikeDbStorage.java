package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.StorageException;

@Component
public class LikeDbStorage implements LikeStorage{

    private final JdbcTemplate jdbcTemplate;

    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(long filmId, long userId) {
        String sqlQuery = "insert into LIKES (FILM_ID, USER_ID) values (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public boolean deleteLike(long filmId, long userId) throws StorageException {
            String sqlQuery = "delete from LIKES where FILM_ID = ? AND USER_ID = ?";
            return jdbcTemplate.update(sqlQuery, filmId, userId) > 0;
    }

    @Override
    public Integer countLikeByFilm(long filmId) {
        String sqlQuery = "select COUNT(USER_ID) from LIKES WHERE FILM_ID = ?";
        Integer countLike = jdbcTemplate.queryForObject(sqlQuery,Integer.class, filmId);
        return countLike;
    }

   /* public Collection<Film> printTopFilms(int count) {

       String sqlQuery = "select f.FILM_ID, f.FILM_NAME, f.DESCRIPTION, f.RELEASE_DATE, f.DURATION, f.MPA_ID "+
                " from FILMS as f LEFT JOIN LIKES as l ON f.FILM_ID = l.FILM_ID "
                +"GROUP BY f.FILM_ID ORDER BY COUNT(USER_ID) DESC LIMIT ?;";
        List<Film> films = jdbcTemplate.query(sqlQuery, filmDbStorage::makeFilm, count);
        return films;
    } */
}
