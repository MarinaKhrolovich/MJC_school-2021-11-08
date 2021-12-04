package com.epam.esm.model;

import com.epam.esm.bean.Tag;

public interface TagDAO {
    void delete(int id);

    void add(Tag tag);

    Tag get(int id);

    Tag get(String name);
}
