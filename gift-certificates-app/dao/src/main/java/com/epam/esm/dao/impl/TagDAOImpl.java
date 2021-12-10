package com.epam.esm.dao.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TagDAOImpl implements TagDAO {

    public static final String SELECT_FROM_TAG = "SELECT * FROM tag";
    public static final String SELECT_FROM_TAG_WHERE_ID = "SELECT * FROM tag WHERE id = ?";
    public static final String SELECT_FROM_TAG_WHERE_NAME = "SELECT * FROM tag WHERE name = ?";
    public static final String CREATE_TAG = "INSERT INTO tag(name) VALUES(?)";
    public static final String DELETE_FROM_TAG_WHERE_ID = "DELETE FROM tag WHERE id = ?";

    public static final String DELETE_FROM_CERTIFICATE_TAG_WHERE_ID = "DELETE FROM certificate_tag WHERE tag_id = ?";


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void add(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(CREATE_TAG, new String[] {"id"});
                    ps.setString(1, tag.getName());
                    return ps;
                },
                keyHolder);

        tag.setId(keyHolder.getKey().intValue());

    }

    @Override
    public Tag get(int id) {
        return jdbcTemplate.query(SELECT_FROM_TAG_WHERE_ID, new BeanPropertyRowMapper<>(Tag.class),id)
                .stream().findAny().orElseThrow(() -> new ResourceNotFoundException());
    }

    @Override
    public Tag get(String name) {
        return jdbcTemplate.query(SELECT_FROM_TAG_WHERE_NAME, new BeanPropertyRowMapper<>(Tag.class),name)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Tag> get() {

        return jdbcTemplate.query(SELECT_FROM_TAG, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_FROM_TAG_WHERE_ID, id);
    }

    @Override
    public void deleteFromCertificates(int id) {
        jdbcTemplate.update(DELETE_FROM_CERTIFICATE_TAG_WHERE_ID, id);
    }

}
