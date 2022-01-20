package com.epam.esm.dao;

import com.epam.esm.bean.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDAO {

    Tag add(Tag tag);

    Tag get(int id);

    Optional<Tag> get(String name);

    List<Tag> get();

    void delete(int id);

    Tag getMostPopular();

}
