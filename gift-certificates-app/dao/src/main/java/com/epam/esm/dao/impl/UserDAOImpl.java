package com.epam.esm.dao.impl;

import com.epam.esm.bean.User;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final String SELECT_FROM_USER = "SELECT * FROM users";
    private static final String SELECT_FROM_USER_WHERE_ID = "SELECT * FROM users WHERE id = ?";
    private static final String CREATE_USER = "INSERT INTO users(login, name, surname) VALUES(?,?,?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User add(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps =
                                connection.prepareStatement(CREATE_USER, new String[]{"id"});
                        ps.setString(1, user.getLogin());
                        ps.setString(2, user.getName());
                        ps.setString(3, user.getSurname());
                        return ps;
                    },
                    keyHolder);
        } catch (DuplicateKeyException exception) {
            throw new ResourceAlreadyExistsException(user.getLogin());
        }
        if (keyHolder.getKey() != null) {
            user.setId(keyHolder.getKey().intValue());
        }
        return user;
    }

    @Override
    public User get(int id) {
        return jdbcTemplate.query(SELECT_FROM_USER_WHERE_ID, new BeanPropertyRowMapper<>(User.class), id).stream()
                .findAny().orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<User> get() {
        return jdbcTemplate.query(SELECT_FROM_USER, new BeanPropertyRowMapper<>(User.class));
    }

}
