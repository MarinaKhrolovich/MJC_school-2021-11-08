package com.epam.esm.dao.util;

import com.epam.esm.bean.Certificate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class CertificateUpdateSQLRequest {

    public static final String UPDATE_CERTIFICATE = "UPDATE certificate SET ";
    public static final String UPDATE_WHERE = " WHERE id =?";
    public static final String NAME = "name = ?, ";
    public static final String DESCRIPTION = "description = ?, ";
    public static final String DURATION = "duration = ?, ";
    public static final String PRICE = "price = ?, ";
    public static final String LAST_UPDATE_DATE = "last_update_date = ? ";

    public CertificateUpdateParameters create(int id, Certificate certificate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UPDATE_CERTIFICATE);
        List<Object> parameters = new ArrayList<>();

        if (certificate.getName() != null) {
            stringBuilder.append(NAME);
            parameters.add(certificate.getName());
        }
        if (certificate.getDescription() != null) {
            stringBuilder.append(DESCRIPTION);
            parameters.add(certificate.getDescription());
        }
        if (certificate.getDuration() != 0) {
            stringBuilder.append(DURATION);
            parameters.add(certificate.getDuration());
        }
        if (certificate.getPrice() != 0) {
            stringBuilder.append(PRICE);
            parameters.add(certificate.getPrice());
        }

        stringBuilder.append(LAST_UPDATE_DATE);
        parameters.add(Timestamp.from(Instant.now()));

        stringBuilder.append(UPDATE_WHERE);
        parameters.add(id);

        return new CertificateUpdateParameters(stringBuilder.toString(), parameters);
    }
}
