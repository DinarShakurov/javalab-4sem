package ru.shakurov.servlet_spring.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.shakurov.servlet_spring.models.User;
import ru.shakurov.servlet_spring.repositories.UserRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryJdbcTemplateImpl implements UserRepository {

    //language=PostgreSQL
    private static final String SQL_SELECT_BY_ID = "select * from users where id = ?";
    //language=PostgreSQL
    private static final String SQL_DELETE_BY_ID = "delete from users where id = ?";
    //language=PostgreSQL
    private static final String SQL_SELET_ALL = "select * from users";
    //language=PostgreSQL
    private static final String SQL_INSERT = "insert into users (name, email, password, link, state) values (?,?,?,?,?)";
    //language=PostgreSQL
    private static final String SQL_SELECT_BY_LINK = "select * from users where link = ?";
    //language=PostgreSQL
    private static final String SQL_UPDATE_STATE_BY_LINK = "update users set state = ? where link = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = (rs, rowNum) ->
            User.builder()
                    .id(rs.getInt("id"))
                    .email(rs.getString("email"))
                    .name(rs.getString("name"))
                    .password(rs.getString("password"))
                    .build();

    @Override
    public Optional<User> find(Integer id) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, userRowMapper, id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELET_ALL, userRowMapper);
    }

    @Override
    public void save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getPassword());
            ps.setString(4, entity.getLink());
            ps.setString(5, entity.getState());
            return ps;
        }, keyHolder);
        entity.setId((Integer) keyHolder.getKey());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public Optional<User> findByLink(String link) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_BY_LINK, userRowMapper, link);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateState(String state, String userLink) {
        jdbcTemplate.update(SQL_UPDATE_STATE_BY_LINK, state, userLink);
    }
}
