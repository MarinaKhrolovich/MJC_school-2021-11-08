package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.OrderDTO;
import com.epam.esm.bean.SearchDTO;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.CertificateTagDAO;
import com.epam.esm.dao.mapper.CertificateMapper;
import com.epam.esm.dao.util.CertificateGetSQLRequest;
import com.epam.esm.dao.util.CertificateUpdateParameters;
import com.epam.esm.dao.util.CertificateUpdateSQLRequest;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    private static final String SELECT_FROM_CERTIFICATE_WHERE_ID = "SELECT * FROM certificate WHERE id = ?";
    private static final String DELETE_FROM_CERTIFICATE_WHERE_ID = "DELETE FROM certificate WHERE id = ?";
    private static final String CREATE_CERTIFICATE = "INSERT INTO certificate(name,description,duration,price," +
            "create_date,last_update_date) VALUES(?,?,?,?,?,?)";

    private final JdbcTemplate jdbcTemplate;
    private final CertificateGetSQLRequest getSQLRequest;
    private final CertificateUpdateSQLRequest updateSQLRequest;
    private final CertificateTagDAO certificateTagDAO;

    @Autowired
    public CertificateDAOImpl(JdbcTemplate jdbcTemplate, CertificateGetSQLRequest getSQLRequest,
                              CertificateUpdateSQLRequest updateSQLRequest, CertificateTagDAO certificateTagDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.getSQLRequest = getSQLRequest;
        this.updateSQLRequest = updateSQLRequest;
        this.certificateTagDAO = certificateTagDAO;
    }

    @Override
    public void add(Certificate certificate) {
        certificate.setCreateDate(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        certificate.setLastUpdateDate(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(CREATE_CERTIFICATE, new String[]{"id"});
                    ps.setString(1, certificate.getName());
                    ps.setString(2, certificate.getDescription());
                    ps.setInt(3, certificate.getDuration());
                    ps.setDouble(4, certificate.getPrice());
                    ps.setTimestamp(5, Timestamp.from(certificate.getCreateDate()));
                    ps.setTimestamp(6, Timestamp.from(certificate.getLastUpdateDate()));
                    return ps;
                },
                keyHolder);

        if (keyHolder.getKey() != null) {
            certificate.setId(keyHolder.getKey().intValue());
        }
    }

    @Override
    public Certificate get(int id) {
        return jdbcTemplate.query(SELECT_FROM_CERTIFICATE_WHERE_ID, new CertificateMapper(), id)
                .stream().findAny().orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<Certificate> get(OrderDTO orderDTO, SearchDTO searchDTO) {
        CertificateUpdateParameters getParameters = getSQLRequest.create(orderDTO, searchDTO);
        return jdbcTemplate.query(getParameters.getSqlRequest(),
                new CertificateMapper(), getParameters.getParameters().toArray());
    }

    @Override
    public void update(int id, Certificate certificate) {
        CertificateUpdateParameters updateParameters = updateSQLRequest.create(id, certificate);
        if (!updateParameters.getParameters().isEmpty()) {
            jdbcTemplate.update(updateParameters.getSqlRequest(), updateParameters.getParameters().toArray());
        }
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_FROM_CERTIFICATE_WHERE_ID, id);
    }


}
