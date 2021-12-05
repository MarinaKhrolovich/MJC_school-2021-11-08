package com.epam.esm.model.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.model.CertificateDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    public static final String SELECT_FROM_CERTIFICATE = "SELECT * FROM certificate";
    public static final String SELECT_FROM_CERTIFICATE_WHERE_ID = "SELECT * FROM certificate WHERE id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void add(Certificate certificate) {

    }

    @Override
    public Certificate get(int id) {
        return jdbcTemplate.queryForObject(SELECT_FROM_CERTIFICATE_WHERE_ID, new CertificateMapper(), id);
    }

    @Override
    public List<Certificate> get() {
        return jdbcTemplate.query(SELECT_FROM_CERTIFICATE, new CertificateMapper());
    }

    @Override
    public void update(Certificate certificate) {

    }

    @Override
    public void delete(int id) {

    }
}
