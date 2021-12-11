package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Tag;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    public static final String SELECT_FROM_CERTIFICATE = "SELECT * FROM certificate";
    public static final String SELECT_FROM_CERTIFICATE_WHERE_ID = "SELECT * FROM certificate WHERE id = ?";
    public static final String DELETE_FROM_CERTIFICATE_WHERE_ID = "DELETE FROM certificate WHERE id = ?";
    public static final String CREATE_CERTIFICATE = "INSERT INTO certificate(name,description,duration,price,create_date,last_update_date) VALUES(?,?,?,?,?,?)";
    public static final String UPDATE_CERTIFICATE = "UPDATE certificate set name =?, description = ?,duration =?, price =?, last_update_date = ? WHERE id =?";


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void add(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(CREATE_CERTIFICATE, new String[]{"id"});
                    ps.setString(1, certificate.getName());
                    ps.setString(2, certificate.getDescription());
                    ps.setInt(3, certificate.getDuration());
                    ps.setDouble(4, certificate.getPrice());
                    ps.setTimestamp(5, Timestamp.from(certificate.getCreate_date()));
                    ps.setTimestamp(6, Timestamp.from(certificate.getLast_update_date()));
                    return ps;
                },
                keyHolder);
        certificate.setId(keyHolder.getKey().intValue());
    }

    @Override
    public Certificate get(int id) {
        return jdbcTemplate.query(SELECT_FROM_CERTIFICATE_WHERE_ID, new CertificateMapper(), id)
                .stream().findAny().orElseThrow(() -> new ResourceNotFoundException(Integer.toString(id)));
    }

    @Override
    public List<Certificate> get(String orderByDate, String orderByName, String tagName, String certificateName, String certificateDescription) {
        return jdbcTemplate.query(SELECT_FROM_CERTIFICATE, new CertificateMapper());
    }

    @Override
    public void update(int id, Certificate certificate) {
        jdbcTemplate.update(UPDATE_CERTIFICATE,
                certificate.getName(), certificate.getDescription(), certificate.getDuration(), certificate.getPrice(),
                Timestamp.from(Instant.now()), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_FROM_CERTIFICATE_WHERE_ID, id);
    }


}
