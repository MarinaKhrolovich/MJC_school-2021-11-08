package com.epam.esm.service.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.CertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;
    private final CertificateTagDAO certificateTagDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, CertificateTagDAO certificateTagDAO) {
        this.tagDAO = tagDAO;
        this.certificateTagDAO = certificateTagDAO;
    }

    @Override
    @Transactional
    public void add(Tag tag) {
        Tag tagFromBase = tagDAO.get(tag.getName());
        if (tagFromBase != null) {
            throw new ResourceAlreadyExistsException(tag.getName());
        }
        tagDAO.add(tag);
    }

    @Override
    @Transactional
    public Tag get(int id) {
        Tag tag = tagDAO.get(id);
        return tag;
    }

    @Override
    @Transactional
    public List<Tag> get() {
        return tagDAO.get();
    }

    @Override
    @Transactional
    public void delete(int id) {
        tagDAO.get(id);
        certificateTagDAO.deleteTagFromCertificates(id);
        tagDAO.delete(id);
    }
}
