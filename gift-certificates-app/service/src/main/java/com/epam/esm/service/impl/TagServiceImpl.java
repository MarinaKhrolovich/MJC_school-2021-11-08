package com.epam.esm.service.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.CertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;
    private final CertificateTagDAO certificateTagDAO;
    private final TagCheck tagCheck;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, CertificateTagDAO certificateTagDAO, TagCheck tagCheck) {
        this.tagDAO = tagDAO;
        this.certificateTagDAO = certificateTagDAO;
        this.tagCheck = tagCheck;
    }

    @Override
    @Transactional
    public void add(Tag tag) {
        tagCheck.check(tag);
        tagDAO.add(tag);
    }

    @Override
    public Tag get(int id) {
        Tag tag = tagDAO.get(id);
        return tag;
    }

    @Override
    public List<Tag> get() {
        return tagDAO.get();
    }

    @Override
    @Transactional
    public void delete(int id) {
        tagDAO.get(id);
        //certificateTagDAO.deleteTagFromCertificates(id);
        tagDAO.delete(id);
    }
}
