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

    private static final String CREATE_CERTIFICATE_TAG = "INSERT INTO certificate_tag(certificate_id,tag_id) " +
                                                         "VALUES(?,?)";
    private static final String SELECT_TAGS_OF_CERTIFICATE = "SELECT tag.id, tag.name FROM certificate_tag JOIN tag " +
                                         "ON certificate_tag.tag_id = tag.id WHERE certificate_tag.certificate_id = ?";
    private static final String SELECT_CERTIFICATE_TAG = SELECT_TAGS_OF_CERTIFICATE + " AND " +
                                                         "certificate_tag.tag_id = ?";
    private static final String DELETE_FROM_CERTIFICATE_TAG_WHERE_TAG_ID = "DELETE FROM certificate_tag " +
                                                                           "WHERE tag_id = ?";
    private static final String DELETE_FROM_CERTIFICATE_TAG_WHERE_CERTIFICATE_ID = "DELETE FROM certificate_tag " +
                                                                                   "WHERE certificate_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateTagDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addTagToCertificate(int certificateId, int tagId) {
        jdbcTemplate.update(CREATE_CERTIFICATE_TAG, certificateId, tagId);
    }

    @Override
    public List<Tag> getAllTagsOfCertificate(int certificateId) {
        return jdbcTemplate.query(SELECT_TAGS_OF_CERTIFICATE, new BeanPropertyRowMapper<>(Tag.class), certificateId);
    }

    @Override
    public Tag getTagOfCertificate(int certificateId, int tagId) {
        return jdbcTemplate.query(SELECT_CERTIFICATE_TAG, new BeanPropertyRowMapper<>(Tag.class), certificateId, tagId)
                .stream().findAny().orElse(null);
    }

    @Override
    public void deleteTagsOfCertificate(int certificateId) {
        jdbcTemplate.update(DELETE_FROM_CERTIFICATE_TAG_WHERE_CERTIFICATE_ID, certificateId);
    }

}
