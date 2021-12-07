package com.epam.esm.model.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.model.CertificateDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    public static final String SELECT_FROM_CERTIFICATE = "SELECT * FROM certificate";
    public static final String SELECT_FROM_CERTIFICATE_WHERE_ID = "SELECT * FROM certificate WHERE id = ?";
    public static final String DELETE_FROM_CERTIFICATE_WHERE_ID = "DELETE FROM certificate WHERE id = ?";
    public static final String CREATE_CERTIFICATE = "INSERT INTO certificate(name,description,duration,price,create_date,last_update_date) VALUES(?,?,?,?,?)";
    public static final String UPDATE_CERTIFICATE = "UPDATE certificate set name =?, description = ?,duration =?, price =?, last_update_date = ? WHERE id =?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void add(Certificate certificate) {
        jdbcTemplate.update(CREATE_CERTIFICATE,
                certificate.getName(), certificate.getDescription(), certificate.getDuration(), certificate.getPrice(),
                Timestamp.from(Instant.now()),Timestamp.from(Instant.now()));
    }

    @Override
    public Certificate get(int id) {
        return jdbcTemplate.queryForObject(SELECT_FROM_CERTIFICATE_WHERE_ID, new CertificateMapper(), id);

    }

    @Override
    public List<Certificate> get(String orderByDate, String orderByName, String tagName, String certificateName, String certificateDescription) {
        return jdbcTemplate.query(SELECT_FROM_CERTIFICATE, new CertificateMapper());
    }

    @Override
    public void update(int id, Certificate certificate) {
        jdbcTemplate.update(UPDATE_CERTIFICATE,
                certificate.getName(), certificate.getDescription(), certificate.getDuration(), certificate.getPrice(),
                Timestamp.from(Instant.now()),id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_FROM_CERTIFICATE_WHERE_ID, id);
    }


}
