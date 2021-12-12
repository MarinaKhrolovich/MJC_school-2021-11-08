package com.epam.esm.validator;

import com.epam.esm.bean.Certificate;
import com.epam.esm.exception.ValidatorException;

public final class CertificateCheck {

    public static final String MESSAGE_CERTIFICATE_NAME_FILL = "message.certificate.name.fill";
    public static final String MESSAGE_CERTIFICATE_NAME_LENGTH = "message.certificate.name.length";
    public static final String MESSAGE_CERTIFICATE_DESCRIPTION_FILL = "message.certificate.description.fill";
    public static final String MESSAGE_CERTIFICATE_DESCRIPTION_LENGTH = "message.certificate.description.length";
    public static final String MESSAGE_CERTIFICATE_DURATION = "message.certificate.duration";
    public static final String MESSAGE_CERTIFICATE_PRICE = "message.certificate.price";

    public static void checkCertificate(Certificate certificate) {
        String name = certificate.getName();
        String description = certificate.getDescription();
        int duration = certificate.getDuration();
        double price = certificate.getPrice();

        if (name == null) {
            throw new ValidatorException(MESSAGE_CERTIFICATE_NAME_FILL);
        }

        if (name.length() < 3 || name.length() > 100) {
            throw new ValidatorException(MESSAGE_CERTIFICATE_NAME_LENGTH);
        }

        if (description == null) {
            throw new ValidatorException(MESSAGE_CERTIFICATE_DESCRIPTION_FILL);
        }

        if (description.length() < 3 || description.length() > 1000) {
            throw new ValidatorException(MESSAGE_CERTIFICATE_DESCRIPTION_LENGTH);
        }

        if (duration < 1) {
            throw new ValidatorException(MESSAGE_CERTIFICATE_DURATION);
        }

        if (price <= 0) {
            throw new ValidatorException(MESSAGE_CERTIFICATE_PRICE);
        }
    }
}
