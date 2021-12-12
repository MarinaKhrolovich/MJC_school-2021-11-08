package com.epam.esm.bean;

public class RequestParameters {

    private String orderByDate;
    private String orderByName;
    private String tagName;
    private String certificateName;
    private String certificateDescription;

    public RequestParameters(String orderByDate, String orderByName, String tagName, String certificateName, String certificateDescription) {
        this.orderByDate = orderByDate;
        this.orderByName = orderByName;
        this.tagName = tagName;
        this.certificateName = certificateName;
        this.certificateDescription = certificateDescription;
    }

    public String getOrderByDate() {
        return orderByDate;
    }

    public String getOrderByName() {
        return orderByName;
    }

    public String getTagName() {
        return tagName;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public String getCertificateDescription() {
        return certificateDescription;
    }
}
