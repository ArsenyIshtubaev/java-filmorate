package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Component ("filmDbStorage")
public class FilmDbStorage implements FilmStorage{

    private final JdbcTemplate jdbcTemplate;
    private final GenreDbStorage genreDbStorage;

    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreDbStorage genreDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDbStorage = genreDbStorage;
    }

    public Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film(rs.getLong("FILM_ID"),
                rs.getString("FILM_NAME"),
                rs.getString("DESCRIPTION"),
                rs.getDate("RELEASE_DATE").toLocalDate(),
                rs.getInt("DURATION"),
                null,
                null
                );
            String sql = "select * from MPA where MPA_ID = ?";
            film.setMpa(jdbcTemplate.queryForObject(sql, this::makeMPA, rs.getInt("MPA_ID")));
            String sql2 = "select GENRE_ID from FILM_GENRE where FILM_ID = " + film.getId();
            List<Integer> genresIdList = jdbcTemplate.queryForList(sql2, Integer.class);
        List<Genre> genres = new ArrayList<>();
        genres.addAll(genresIdList.stream().map(id -> {
            try {
                return genreDbStorage.findById(id);
            } catch (StorageException e) {
                throw new RuntimeException(e);
            }
        }).distinct().collect(Collectors.toList()));
        film.setGenres(genres);
        return film;
    }
    public MPA makeMPA (ResultSet rs, int rowNum) throws SQLException {
        return new MPA(rs.getInt("MPA_ID"),
                rs.getString("MPA_NAME"));
    }
    public Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("GENRE_ID"),
        rs.getString("GENRE"));
    }
    @Override
    public Collection<Film> findAll() {
        String sql = "select * from FILMS";
       return jdbcTemplate.query(sql, this::makeFilm);
    }

    @Override
    public Film create(Film film) {
        String sqlQuery = "insert into FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID) values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().longValue());
        String sql = "delete from FILM_GENRE where FILM_ID = ?";
        jdbcTemplate.update(sql, film.getId());
        if ((film.getGenres()==null) || (film.getGenres().isEmpty())){
            return film;
        }
        else {
            for (Genre genre : film.getGenres()) {
                String sql2 = "insert into FILM_GENRE (FILM_ID, GENRE_ID) values (?, ?)";
                jdbcTemplate.update(sql2,
                        film.getId(),
                        genre.getId());
            }
        }
        return film;
    }
    @Override
    public Film update(Film film) throws StorageException {
        if (findAll().contains(film)) {
            String sqlQuery = "update FILMS set " +
                    "FILM_NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, MPA_ID = ? " +
                    "where FILM_ID = ?";
            jdbcTemplate.update(sqlQuery
                    , film.getName()
                    , film.getDescription()
                    , Date.valueOf(film.getReleaseDate())
                    , film.getDuration()
                    , film.getMpa().getId()
                    , film.getId());
            String sql = "delete from FILM_GENRE where FILM_ID = ?";
            jdbcTemplate.update(sql, film.getId());
            if ((film.getGenres()==null) || (film.getGenres().isEmpty())){
                return film;
            }
            else {
                List<Genre> genres = film.getGenres().stream().distinct().collect(Collectors.toList());
                for (Genre genre : genres) {
                    String sql2 = "insert into FILM_GENRE (FILM_ID, GENRE_ID) values (?, ?)";
                    jdbcTemplate.update(sql2,
                            film.getId(),
                            genre.getId());
                }
                film.setGenres(genres);
            }
        } else {
            throw new StorageException("Данного пользователя нет в БД");
        }
        return film;
    }

    @Override
    public Film findById(long id) throws StorageException {
        String sqlQuery = "select * from FILMS where FILM_ID = ?";
        List<Film> films = jdbcTemplate.query(sqlQuery, this::makeFilm, id);
        if (films.size() != 1) {
            throw new StorageException("Фильма с таким id нет в базе данных");
        }
        return films.get(0);
    }

    @Override
    public boolean delete(long id) {
        String sqlQuery = "delete from FILMS where FILM_ID = ?";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    @Override
    public void deleteAll() {
        String sqlQuery = "drop table FILMS CASCADE";
        jdbcTemplate.update(sqlQuery);
    }
}
