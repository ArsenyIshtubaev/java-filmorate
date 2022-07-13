package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Component("userDbStorage")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Collection<User> findAll() {
        String sql = "select * from USERS";
        return jdbcTemplate.query(sql, this::makeUser);
    }
    public User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getInt("USER_ID"),
                rs.getString("EMAIL"),
                rs.getString("LOGIN"),
                rs.getString("USER_NAME"),
                rs.getDate("BIRTHDAY").toLocalDate());
    }
    @Override
    public User create(User user) {
        String sqlQuery = "insert into USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"USER_ID"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            final LocalDate birthday = user.getBirthday();
            if (birthday == null) {
                stmt.setNull(4, Types.DATE);
            } else {
                stmt.setDate(4, Date.valueOf(birthday));
            }
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User findById(long id) throws StorageException {
        String sqlQuery = "select * from USERS where USER_ID = ?";
        List<User> users = jdbcTemplate.query(sqlQuery, this::makeUser, id);
        if (users.size() != 1) {
            throw new StorageException("Пользователя с таким id нет в базе данных");
        }
        return users.get(0);
    }

    @Override
    public User update(User user) throws StorageException {
            String sqlQuery = "update USERS set " +
                    "EMAIL = ?, LOGIN = ?, USER_NAME = ?, BIRTHDAY = ? " +
                    "where USER_ID = ?";
            jdbcTemplate.update(sqlQuery
                    , user.getEmail()
                    , user.getLogin()
                    , user.getName()
                    , Date.valueOf(user.getBirthday())
                    , user.getId());
            return user;
    }

    @Override
    public boolean delete(long id) {
        String sqlQuery = "delete from USERS where USER_ID = ?";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }
    @Override
    public void deleteAll() {
        String sqlQuery = "drop table USERS CASCADE";
        jdbcTemplate.update(sqlQuery);
    }
}
