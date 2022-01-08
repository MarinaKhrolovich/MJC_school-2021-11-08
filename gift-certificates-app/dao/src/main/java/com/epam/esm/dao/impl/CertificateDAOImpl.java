package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.OrderDTO;
import com.epam.esm.bean.SearchDTO;
import com.epam.esm.bean.Tag;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.CertificateTagDAO;
import com.epam.esm.dao.TagDAO;
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
import java.util.Optional;

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
    private final TagDAO tagDAO;

    @Autowired
    public CertificateDAOImpl(JdbcTemplate jdbcTemplate, CertificateGetSQLRequest getSQLRequest,
                              CertificateUpdateSQLRequest updateSQLRequest, CertificateTagDAO certificateTagDAO,
                              TagDAO tagDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.getSQLRequest = getSQLRequest;
        this.updateSQLRequest = updateSQLRequest;
        this.certificateTagDAO = certificateTagDAO;
        this.tagDAO = tagDAO;
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
        addTagsToCertificate(certificate);
    }

    @Override
    public Certificate get(int id) {
        Certificate certificate = jdbcTemplate.query(SELECT_FROM_CERTIFICATE_WHERE_ID, new CertificateMapper(), id)
                .stream().findAny().orElseThrow(() -> new ResourceNotFoundException(id));
        setTagList(id, certificate);
        return certificate;
    }

    @Override
    public List<Certificate> get(OrderDTO orderDTO, SearchDTO searchDTO) {
        CertificateUpdateParameters getParameters = getSQLRequest.create(orderDTO, searchDTO);
        List<Certificate> certificateList = jdbcTemplate.query(getParameters.getSqlRequest(),
                new CertificateMapper(), getParameters.getParameters().toArray());
        certificateList.forEach(certificate -> setTagList(certificate.getId(), certificate));
        return certificateList;
    }

    @Override
    public Certificate update(int id, Certificate certificate) {
        if (notExists(id)) {
            throw new ResourceNotFoundException(id);
        }
        certificate.setId(id);
        CertificateUpdateParameters updateParameters = updateSQLRequest.create(id, certificate);
        if (!updateParameters.getParameters().isEmpty()) {
            jdbcTemplate.update(updateParameters.getSqlRequest(), updateParameters.getParameters().toArray());
        }
        certificateTagDAO.deleteTagsOfCertificate(id);
        addTagsToCertificate(certificate);
        return get(id);
    }

    @Override
    public void delete(int id) {
        if (notExists(id)) {
            throw new ResourceNotFoundException(id);
        }
        jdbcTemplate.update(DELETE_FROM_CERTIFICATE_WHERE_ID, id);
    }

    private boolean notExists(int id) {
        return jdbcTemplate.query(SELECT_FROM_CERTIFICATE_WHERE_ID, new CertificateMapper(), id)
                .stream().findAny().isEmpty();
    }

    private void addTagsToCertificate(Certificate certificate) {
        List<Tag> tagList = certificate.getTagList();
        if (tagList != null) {
            for (Tag tag : tagList) {
                Optional<Tag> tagFromBase = tagDAO.get(tag.getName());
                if (tagFromBase.isEmpty()) {
                    tagDAO.add(tag);
                } else {
                    tag.setId(tagFromBase.get().getId());
                }
                Optional<Tag> tagOfCertificate = certificateTagDAO.getTagOfCertificate(certificate.getId(), tag.getId());
                if (tagOfCertificate.isEmpty()) {
                    certificateTagDAO.addTagToCertificate(certificate.getId(), tag.getId());
                }
            }
        }
    }

    private void setTagList(int id, Certificate certificate) {
        List<Tag> tagList = certificateTagDAO.getAllTagsOfCertificate(id);
        certificate.setTagList(tagList);
    }
}
