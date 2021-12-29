package com.epam.esm.dao.util;

import java.util.List;

public class CertificateUpdateParameters {

    private final String sqlRequest;

    private final List<Object> parameters;

    public CertificateUpdateParameters(String sqlRequest, List<Object> parameters) {
        this.sqlRequest = sqlRequest;
        this.parameters = parameters;
    }

    public String getSqlRequest() {
        return sqlRequest;
    }

    public List<Object> getParameters() {
        return parameters;
    }

}
