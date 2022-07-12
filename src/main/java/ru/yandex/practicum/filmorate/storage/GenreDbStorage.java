package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Genre> findAll() {
        String sql = "select * from GENRE";
        return jdbcTemplate.query(sql, this::makeGenre);
    }

    private Genre makeGenre(ResultSet resultSet, int i) throws SQLException {
        return new Genre(resultSet.getInt("GENRE_ID"),
                resultSet.getString("GENRE"));
    }

    @Override
    public Genre create(Genre genre) {
        String sqlQuery = "insert into GENRE (GENRE) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"GENRE_ID"});
            stmt.setString(1, genre.getName());
            return stmt;
        }, keyHolder);
        genre.setId(keyHolder.getKey().intValue());
        return genre;
    }

    @Override
    public Genre update(Genre genre) throws StorageException {
        String sqlQuery = "update GENRE set " +
                "GENRE = ? where GENRE_ID = ?";
        jdbcTemplate.update(sqlQuery
                , genre.getName()
                , genre.getId());
        return genre;
    }

    @Override
    public Genre findById(Integer id) throws StorageException {
        String sqlQuery = "select * from GENRE where GENRE_ID = ?";
        List<Genre> genres =  jdbcTemplate.query(sqlQuery, this::makeGenre, id);
        if (genres.size() != 1) {
            throw new StorageException("Жанра с таким id нет в базе данных");
        }
        return genres.get(0);
    }

    @Override
    public boolean delete(Integer id) {
        String sqlQuery = "delete from GENRE where GENRE_ID = ?";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    @Override
    public void deleteAll() {
        String sqlQuery = "drop table GENRE CASCADE";
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public List<Genre> findGenreByFilmId(long filmId) {
        String sqlQuery = "select g.GENRE_ID, g.GENRE from GENRE as g "+
                "join FILM_GENRE as fg on g.GENRE_ID = fg.GENRE_ID where fg.FILM_ID = ?";
        return jdbcTemplate.query(sqlQuery, this::makeGenre, filmId);
    }
}
