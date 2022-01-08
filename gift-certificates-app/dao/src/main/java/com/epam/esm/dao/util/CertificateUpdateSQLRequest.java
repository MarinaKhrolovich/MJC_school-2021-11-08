package com.epam.esm.dao.util;

import com.epam.esm.bean.Certificate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class CertificateUpdateSQLRequest {

    private static final String UPDATE_CERTIFICATE = "UPDATE certificate SET ";
    private static final String UPDATE_WHERE = " WHERE id =?";
    private static final String NAME = "name = ?, ";
    private static final String DESCRIPTION = "description = ?, ";
    private static final String DURATION = "duration = ?, ";
    private static final String PRICE = "price = ?, ";
    private static final String LAST_UPDATE_DATE = "last_update_date = ? ";

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
        if (certificate.getDuration() != null) {
            stringBuilder.append(DURATION);
            parameters.add(certificate.getDuration());
        }
        if (certificate.getPrice() != null) {
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
