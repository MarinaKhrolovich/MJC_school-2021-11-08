package com.epam.esm.service;

import com.epam.esm.bean.Tag;

import java.util.List;

public interface TagService {

    void add(Tag tag);

    Tag get(int id);

    List<Tag> get();

    void delete(int id);
}
