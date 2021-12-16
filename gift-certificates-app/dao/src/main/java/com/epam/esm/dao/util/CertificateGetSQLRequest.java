package com.epam.esm.dao.util;

import com.epam.esm.bean.RequestParameters;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CertificateGetSQLRequest {

    private static final String SELECT_FROM_CERTIFICATE = "SELECT * FROM certificate";
    private static final String JOIN = " JOIN certificate_tag JOIN tag ON certificate.id = " +
                                        "certificate_tag.certificate_id AND certificate_tag.tag_id = tag.id";
    private static final String WHERE = " WHERE";
    private static final String CERTIFICATE_NAME = " certificate.name LIKE concat ('%', ?, '%')";
    private static final String CERTIFICATE_DESCRIPTION = " certificate.description LIKE concat ('%', ?, '%')";
    private static final String TAG_NAME = " tag.name = ?";
    private static final String ORDER_BY = " ORDER BY";
    private static final String ORDER_NAME = " certificate.name";
    private static final String ORDER_DATE = " certificate.create_date";
    private static final String ASC = " ASC";
    private static final String DESC = " DESC";
    private static final String AND = " AND";
    private static final String COMMA = ", ";

    public CertificateUpdateParameters create(RequestParameters requestParameters) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT_FROM_CERTIFICATE);
        List<Object> parameters = new ArrayList<>();

        String tagName = requestParameters.getTagName();
        String certificateName = requestParameters.getCertificateName();
        String certificateDescription = requestParameters.getCertificateDescription();
        String orderByDate = requestParameters.getOrderByDate();
        String orderByName = requestParameters.getOrderByName();

        boolean whereExists = false;
        boolean orderExists = false;

        if (tagName != null) {
            stringBuilder.append(JOIN);
        }

        whereExists = appendWhere(stringBuilder, certificateName, CERTIFICATE_NAME, whereExists, parameters);
        whereExists = appendWhere(stringBuilder, certificateDescription, CERTIFICATE_DESCRIPTION,
                                                                                    whereExists, parameters);
        appendWhere(stringBuilder, tagName, TAG_NAME, whereExists, parameters);

        orderExists = appendOrder(stringBuilder, orderByDate, ORDER_DATE, orderExists);
        appendOrder(stringBuilder, orderByName, ORDER_NAME, orderExists);

        return new CertificateUpdateParameters(stringBuilder.toString(), parameters);
    }

    private boolean appendOrder(StringBuilder stringBuilder, String orderBy, String sqlRequest, boolean orderExists) {
        if (orderBy != null) {
            if (!orderExists) {
                stringBuilder.append(ORDER_BY);
                orderExists = true;
            } else {
                stringBuilder.append(COMMA);
            }
            stringBuilder.append(sqlRequest);
            stringBuilder.append(orderBy.equals(DESC.trim()) ? DESC : ASC);
        }
        return orderExists;
    }

    private boolean appendWhere(StringBuilder stringBuilder, String field, String sqlRequest,
                                boolean whileExists, List<Object> parameters) {
        if (field != null) {
            if (!whileExists) {
                stringBuilder.append(WHERE);
                whileExists = true;
            } else {
                stringBuilder.append(AND);
            }
            stringBuilder.append(sqlRequest);
            parameters.add(field);
        }
        return whileExists;
    }
}
