package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
public class MPADbStorage implements MPAStorage{

    private final JdbcTemplate jdbcTemplate;

    public MPADbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<MPA> findAll() {
        String sql = "select * from MPA";
        return jdbcTemplate.query(sql, this::makeMPA);
    }
    private MPA makeMPA(ResultSet resultSet, int i) throws SQLException {
        return new MPA(resultSet.getInt("MPA_ID"),
                resultSet.getString("MPA_NAME"));
    }

    @Override
    public MPA create(MPA mpa) {
        String sqlQuery = "insert into MPA (MPA_NAME) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"MPA_ID"});
            stmt.setString(1, mpa.getName());
            return stmt;
        }, keyHolder);
        mpa.setId(keyHolder.getKey().intValue());
        return mpa;
    }

    @Override
    public MPA update(MPA mpa) throws StorageException {
        if (findAll().contains(mpa)) {
            String sqlQuery = "update MPA set " +
                    "MPA_NAME = ? where MPA_ID = ?";
            jdbcTemplate.update(sqlQuery
                    , mpa.getName()
                    , mpa.getId());
            return mpa;
        } else {
            throw new StorageException("Данного рейтинга нет в БД");
        }
    }

    @Override
    public MPA findById(Integer id) throws StorageException {
        String sqlQuery = "select * from MPA where MPA_ID = ?";
         List<MPA> mpaList = jdbcTemplate.query(sqlQuery, this::makeMPA, id);
        if (mpaList.size() != 1) {
            throw new StorageException("Рейтинга с таким id нет в базе данных");
        }
         return mpaList.get(0);
    }

    @Override
    public boolean delete(Integer id) {
        String sqlQuery = "delete from MPA where MPA_ID = ?";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    @Override
    public void deleteAll() {
        String sqlQuery = "drop table MPA CASCADE";
        jdbcTemplate.update(sqlQuery);
    }
}
