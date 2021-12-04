package com.epam.esm.service;

import com.epam.esm.bean.Tag;

public interface TagService {

    void delete(int id);

    void add(Tag tag);

    Tag get(int id);

    Tag get(String name);
}
