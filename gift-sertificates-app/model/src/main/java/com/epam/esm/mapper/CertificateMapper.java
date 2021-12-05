package com.epam.esm.mapper;


import com.epam.esm.bean.Certificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class CertificateMapper implements RowMapper<Certificate> {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String DURATION = "duration";
    public static final String CREATE_DATE = "create_date";
    public static final String LAST_UPDATE_DATE = "last_update_date";

    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Certificate certificate = new Certificate();
        certificate.setId(rs.getInt(ID));
        certificate.setName(rs.getString(NAME));
        certificate.setDescription(rs.getString(DESCRIPTION));
        certificate.setPrice(rs.getDouble(PRICE));
        certificate.setDuration(rs.getInt(DURATION));
        certificate.setCreate_date(rs.getTimestamp(CREATE_DATE).toInstant());
        certificate.setLast_update_date(rs.getTimestamp(LAST_UPDATE_DATE).toInstant());
        return null;
    }
}
