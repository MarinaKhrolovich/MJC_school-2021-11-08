package com.epam.esm.dao.mapper;

import com.epam.esm.bean.Certificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Certificate certificate = new Certificate();
        certificate.setId(rs.getInt("id"));
        certificate.setName(rs.getString("name"));
        certificate.setDescription(rs.getString("description"));
        certificate.setDuration(rs.getInt("duration"));
        certificate.setPrice(rs.getBigDecimal("price"));
        certificate.setCreateDate(rs.getTimestamp("create_date").toInstant());
        certificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toInstant());
        return certificate;
    }
}
