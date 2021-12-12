package com.epam.esm.dao.util;

import com.epam.esm.bean.RequestParameters;

import java.util.ArrayList;
import java.util.List;

public class CertificateGetSQLRequest {

    public static final String SELECT_FROM_CERTIFICATE = "SELECT * FROM certificate";


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


        sqlRequest = stringBuilder.toString();
    }
}

