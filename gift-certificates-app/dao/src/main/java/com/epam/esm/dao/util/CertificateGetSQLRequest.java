package com.epam.esm.dao.util;

import com.epam.esm.bean.RequestParameters;

import java.util.ArrayList;
import java.util.List;

public class CertificateGetSQLRequest {

    public static final String SELECT_FROM_CERTIFICATE = "SELECT * FROM certificate";
    public static final String JOIN = " JOIN certificate_tag JOIN tag ON certificate.id = certificate_tag.certificate_id AND certificate_tag.tag_id = tag.id";
    public static final String WHERE = " WHERE";
    public static final String CERTIFICATE_NAME = " certificate.name LIKE concat ('%', ?, '%')";
    public static final String CERTIFICATE_DESCRIPTION = " certificate.description LIKE concat ('%', ?, '%')";
    public static final String TAG_NAME = " tag.name = ?";
    public static final String ORDER_BY = " ORDER BY";
    public static final String ORDER_NAME = " certificate.name";
    public static final String ORDER_DATE = " certificate.create_date";
    public static final String ASC = " ASC";
    public static final String DESC = " DESC";
    public static final String AND = " AND";
    public static final String COMMA = ", ";

    private String sqlRequest;
    private List<Object> parameters;

    public CertificateGetSQLRequest() {
    }

    public String getSqlRequest() {
        return sqlRequest;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void create(RequestParameters requestParameters) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT_FROM_CERTIFICATE);
        parameters = new ArrayList<>();

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

        whereExists = appendWhere(stringBuilder, certificateName, CERTIFICATE_NAME, whereExists);
        whereExists = appendWhere(stringBuilder, certificateDescription, CERTIFICATE_DESCRIPTION, whereExists);
        appendWhere(stringBuilder, tagName, TAG_NAME, whereExists);

        orderExists = appendOrder(stringBuilder, orderByDate, ORDER_DATE, orderExists);
        appendOrder(stringBuilder, orderByName, ORDER_NAME, orderExists);

        sqlRequest = stringBuilder.toString();
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
            stringBuilder.append(orderBy.equals(DESC.trim())?DESC:ASC);
        }
        return orderExists;
    }

    private boolean appendWhere(StringBuilder stringBuilder, String field, String sqlRequest, boolean whileExists) {
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

