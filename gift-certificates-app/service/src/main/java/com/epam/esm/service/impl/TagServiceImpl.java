package com.epam.esm.service.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.model.TagDAO;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    @Transactional
    public void add(Tag tag) {
        tagDAO.add(tag);
    }

    @Override
    @Transactional
    public Tag get(int id) {
        return tagDAO.get(id);
    }

    @Override
    @Transactional
    public List<Tag> get() {
        return tagDAO.get();
    }

    @Override
    @Transactional
    public void delete(int id) {
        tagDAO.delete(id);

    }
}
