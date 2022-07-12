package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

@Component
public class FriendDbStorage implements FriendStorage{
    private final JdbcTemplate jdbcTemplate;

    public FriendDbStorage(JdbcTemplate jdbcTemplate, UserDbStorage userDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(long userId, long friendId) {
        String sqlQuery = "insert into FRIENDSHIP (USER_ID, FRIEND_ID, FRIEND_STATUS) values (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, userId, friendId, true);
    }

    @Override
    public boolean deleteFriend(long userId, long friendId) {
        String sqlQuery = "delete from FRIENDSHIP where USER_ID = ? AND FRIEND_ID = ?";
        return jdbcTemplate.update(sqlQuery, userId, friendId) > 0;
    }

    /* @Override
    public Collection<User> printCommonFriends(long userId, long friendId) {
        String sql1 = "select u.USER_ID, u.EMAIL, u.LOGIN, u.USER_NAME, u.BIRTHDAY "
                +"from USERS as u INNER JOIN FRIENDSHIP as fr ON u.USER_ID = fr.FRIEND_ID where fr.USER_ID = ? "+
                "AND fr.FRIEND_STATUS = true";
        List<User> users1 = jdbcTemplate.query(sql1, userDbStorage::makeUser, userId);

        String sql2 = "select u.USER_ID, u.EMAIL, u.LOGIN, u.USER_NAME, u.BIRTHDAY "
                +"from USERS as u INNER JOIN FRIENDSHIP as fr ON u.USER_ID = fr.FRIEND_ID where fr.USER_ID = ? "+
                "AND fr.FRIEND_STATUS = true";
        List<User> users2 = jdbcTemplate.query(sql2, userDbStorage::makeUser, friendId);

        users1.retainAll(users2);

        /*String sqlQuery = "select fr1.FRIEND_ID from (select * from FRIENDSHIP where FRIENDSHIP.USER_ID = ? "+
                "AND FRIENDSHIP.FRIEND_STATUS = true) as fr1 "+
                "join (select * from FRIENDSHIP where FRIENDSHIP.USER_ID = ? " +
                "AND FRIENDSHIP.FRIEND_STATUS = true) as fr2 "+
                "ON fr1.FRIEND_ID = fr2.FRIEND_ID";
        List<User> users = jdbcTemplate.query(sqlQuery, userDbStorage::makeUser, userId, friendId);
        return users1;
    } */

    @Override
    public Collection<Long> findAllIdFriends(long userId) {
        String sql = "select FRIEND_ID from FRIENDSHIP where USER_ID = ? AND FRIEND_STATUS = true";
        List<Long> idFriends = jdbcTemplate.queryForList(sql, Long.class, userId);
        return idFriends;
       /* String sql = "select u.USER_ID, u.EMAIL, u.LOGIN, u.USER_NAME, u.BIRTHDAY "
                +"from USERS as u INNER JOIN FRIENDSHIP as fr ON u.USER_ID = fr.FRIEND_ID where fr.USER_ID = ? "+
                "AND fr.FRIEND_STATUS = true";
        List<User> users = jdbcTemplate.query(sql, userDbStorage::makeUser, userId);
        return users; */
    }
}
