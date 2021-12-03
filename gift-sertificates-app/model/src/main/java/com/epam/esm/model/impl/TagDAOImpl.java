package com.epam.esm.model.impl;

import com.epam.esm.model.TagDAO;
import com.epam.esm.model.bean.Tag;
import org.springframework.stereotype.Repository;

@Repository
public class TagDAOImpl implements TagDAO {
    @Override
    public void delete(int id) {

    }

    @Override
    public void add(Tag tag) {

    }

    @Override
    public Tag get(int id) {
        return null;
    }

    @Override
    public Tag get(String name) {
        return null;
    }
}
