package com.epam.esm.dao.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TagDAOImpl implements TagDAO {

    public static final String SELECT_FROM_TAG = "SELECT * FROM tag";
    public static final String SELECT_FROM_TAG_WHERE_ID = "SELECT * FROM tag WHERE id = ?";
    public static final String SELECT_FROM_TAG_WHERE_NAME = "SELECT * FROM tag WHERE name = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Transactional
    public void add(Tag tag) {

    }

    @Override
    @Transactional
    public Tag get(int id) {
        return jdbcTemplate.queryForObject(SELECT_FROM_TAG_WHERE_ID, new BeanPropertyRowMapper<>(Tag.class), id);
    }

    @Override
    @Transactional
    public Tag get(String name) {
        return jdbcTemplate.queryForObject(SELECT_FROM_TAG_WHERE_NAME, new BeanPropertyRowMapper<>(Tag.class), name);
    }

    @Override
    @Transactional
    public List<Tag> get() {

        return jdbcTemplate.query(SELECT_FROM_TAG, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    @Transactional
    public void delete(int id) {

    }
}
