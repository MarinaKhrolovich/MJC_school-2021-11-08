package com.epam.esm.dao.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.CertificateTagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertificateTagDAOImpl implements CertificateTagDAO {

    public static final String CREATE_CERTIFICATE_TAG = "INSERT INTO certificate_tag(certificate_id,tag_id) VALUES(?,?)";
    public static final String SELECT_TAGS_OF_CERTIFICATE = "SELECT tag.id, tag.name FROM certificate_tag JOIN tag ON certificate_tag.tag_id = tag.id WHERE certificate_tag.certificate_id = ?";
    public static final String SELECT_CERTIFICATE_TAG = SELECT_TAGS_OF_CERTIFICATE + " AND certificate_tag.certificate_id = ?";
    public static final String DELETE_FROM_CERTIFICATE_TAG_WHERE_TAG_ID = "DELETE FROM certificate_tag WHERE tag_id = ?";
    public static final String DELETE_FROM_CERTIFICATE_TAG_WHERE_CERTIFICATE_ID = "DELETE FROM certificate_tag WHERE certificate_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateTagDAOImpl() {
        jdbcTemplate = null;
    }

    @Override
    public void addTagToCertificate(int certificate_id, int tag_id) {
        jdbcTemplate.update(CREATE_CERTIFICATE_TAG, certificate_id, tag_id);
    }

    @Override
    public List<Tag> getAllTagsOfCertificate(int certificate_id) {
        return jdbcTemplate.query(SELECT_TAGS_OF_CERTIFICATE, new BeanPropertyRowMapper<>(Tag.class), certificate_id);
    }

    @Override
    public Tag getTagOfCertificate(int certificate_id, int tag_id) {
        return jdbcTemplate.query(SELECT_CERTIFICATE_TAG, new BeanPropertyRowMapper<>(Tag.class), certificate_id, tag_id)
                .stream().findAny().orElse(null);
    }

    @Override
    public void deleteTagsOfCertificate(int certificate_id) {
        jdbcTemplate.update(DELETE_FROM_CERTIFICATE_TAG_WHERE_CERTIFICATE_ID, certificate_id);
    }

    @Override
    public void deleteTagFromCertificates(int tag_id) {
        jdbcTemplate.update(DELETE_FROM_CERTIFICATE_TAG_WHERE_TAG_ID, tag_id);
    }
}