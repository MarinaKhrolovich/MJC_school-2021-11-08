package com.epam.esm.bean;

import java.util.Optional;

public class SearchDTO {

    private final String tagName;
    private final String certificateName;
    private final String certificateDescription;

    public SearchDTO(String tagName, String certificateName, String certificateDescription) {
        this.tagName = tagName;
        this.certificateName = certificateName;
        this.certificateDescription = certificateDescription;
    }

    public Optional<String> getTagName() {
        return Optional.ofNullable(tagName);
    }

    public Optional<String> getCertificateName() {
        return Optional.ofNullable(certificateName);
    }

    public Optional<String> getCertificateDescription() {
        return Optional.ofNullable(certificateDescription);
    }

}
