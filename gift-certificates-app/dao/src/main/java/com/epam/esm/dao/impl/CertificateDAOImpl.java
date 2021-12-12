package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    public static final String SELECT_FROM_CERTIFICATE = "SELECT * FROM certificate";
    public static final String SELECT_FROM_CERTIFICATE_WHERE_ID = "SELECT * FROM certificate WHERE id = ?";
    public static final String DELETE_FROM_CERTIFICATE_WHERE_ID = "DELETE FROM certificate WHERE id = ?";
    public static final String CREATE_CERTIFICATE = "INSERT INTO certificate(name,description,duration,price,create_date,last_update_date) VALUES(?,?,?,?,?,?)";
    public static final String UPDATE_CERTIFICATE = "UPDATE certificate SET ";
    public static final String UPDATE_WHERE = " WHERE id =?";

    public static final String NAME = "name = ?, ";
    public static final String DESCRIPTION = "description = ?, ";
    public static final String DURATION = "duration = ?, ";
    public static final String PRICE = "price = ?, ";
    public static final String LAST_UPDATE_DATE = "last_update_date = ? ";
    public static final String EMPTY_STRING = "";


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
        List<Object> fieldList = new ArrayList<>();
        String sqlRequest = createUpdateSQLRequest(fieldList, certificate);
        fieldList.add(id);
        if (!sqlRequest.isEmpty()) {
            jdbcTemplate.update(sqlRequest, fieldList.toArray());
        }
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_FROM_CERTIFICATE_WHERE_ID, id);
    }

    public String createUpdateSQLRequest(List<Object> fieldList, Certificate certificate) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UPDATE_CERTIFICATE);

        boolean isNeedToUpdate = false;
        if (certificate.getName() != null) {
            stringBuilder.append(NAME);
            fieldList.add(certificate.getName());
            isNeedToUpdate = true;
        }
        if (certificate.getDescription() != null) {
            stringBuilder.append(DESCRIPTION);
            fieldList.add(certificate.getDescription());
            isNeedToUpdate = true;
        }
        if (certificate.getDuration() != 0) {
            stringBuilder.append(DURATION);
            fieldList.add(certificate.getDuration());
            isNeedToUpdate = true;
        }
        if (certificate.getPrice() != 0) {
            stringBuilder.append(PRICE);
            fieldList.add(certificate.getPrice());
            isNeedToUpdate = true;
        }

        stringBuilder.append(LAST_UPDATE_DATE);
        fieldList.add(Timestamp.from(Instant.now()));
        stringBuilder.append(UPDATE_WHERE);

        return isNeedToUpdate ? stringBuilder.toString() : EMPTY_STRING;
    }
}
