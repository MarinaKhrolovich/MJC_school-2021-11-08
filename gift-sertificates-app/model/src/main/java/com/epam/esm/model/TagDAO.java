package com.epam.esm.model;

import com.epam.esm.bean.Tag;

import java.util.List;

public interface TagDAO {


    void add(Tag tag);

    Tag get(int id);

    List<Tag> get();

    void delete(int id);
}
