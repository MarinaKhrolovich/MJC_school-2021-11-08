package com.epam.esm.model.impl;

import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.TagDAO;
import com.epam.esm.bean.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDAOImpl implements TagDAO {

    public static final String SELECT_FROM_TAG = "SELECT * FROM tag";
    public static final String SELECT_FROM_TAG_WHERE_ID = "SELECT * FROM tag WHERE id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void add(Tag tag) {

    }

    @Override
    public Tag get(int id) {
        return jdbcTemplate.queryForObject(SELECT_FROM_TAG_WHERE_ID, new TagMapper(), id);
    }

    @Override
    public List<Tag> get() {

        return jdbcTemplate.query(SELECT_FROM_TAG, new TagMapper());
    }

    @Override
    public void delete(int id) {

    }
}
