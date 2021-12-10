package com.epam.esm.dao;

import com.epam.esm.bean.Tag;

import java.util.List;

public interface TagDAO {

    void add(Tag tag);

    Tag get(int id);

    Tag get(String name);

    List<Tag> get();

    void delete(int id);

    void deleteFromCertificates(int id);
}
