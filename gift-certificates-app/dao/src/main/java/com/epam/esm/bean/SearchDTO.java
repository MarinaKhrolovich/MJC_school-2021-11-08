package com.epam.esm.bean;

public class SearchDTO {

    private final String tagName;
    private final String certificateName;
    private final String certificateDescription;

    public SearchDTO(String tagName, String certificateName, String certificateDescription) {
        this.tagName = tagName;
        this.certificateName = certificateName;
        this.certificateDescription = certificateDescription;
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
