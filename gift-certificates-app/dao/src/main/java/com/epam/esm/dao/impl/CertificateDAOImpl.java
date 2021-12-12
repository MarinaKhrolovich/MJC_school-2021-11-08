package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.mapper.CertificateMapper;
import com.epam.esm.dao.util.CertificateUpdateSQLRequest;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    public static final String SELECT_FROM_CERTIFICATE = "SELECT * FROM certificate";
    public static final String SELECT_FROM_CERTIFICATE_WHERE_ID = "SELECT * FROM certificate WHERE id = ?";
    public static final String DELETE_FROM_CERTIFICATE_WHERE_ID = "DELETE FROM certificate WHERE id = ?";
    public static final String CREATE_CERTIFICATE = "INSERT INTO certificate(name,description,duration,price,create_date,last_update_date) VALUES(?,?,?,?,?,?)";

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
        CertificateUpdateSQLRequest sqlRequestParameters = new CertificateUpdateSQLRequest();
        sqlRequestParameters.create(id,certificate);
        if (!sqlRequestParameters.getParameters().isEmpty()) {
            jdbcTemplate.update(sqlRequestParameters.getSqlRequest(), sqlRequestParameters.getParameters().toArray());
        }
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_FROM_CERTIFICATE_WHERE_ID, id);
    }


}
