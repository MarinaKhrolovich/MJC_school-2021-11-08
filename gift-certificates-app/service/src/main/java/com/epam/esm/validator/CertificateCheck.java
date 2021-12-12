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

    public static void CheckCertificate(Certificate certificate, boolean checkNull) {
        checkNameCertificate(certificate.getName(), checkNull);
        checkDescriptionCertificate(certificate.getDescription(), checkNull);
        checkDurationCertificate(certificate.getDuration(), checkNull);
        checkPriceCertificate(certificate.getPrice(), checkNull);
    }

    public static void checkNameCertificate(String name, boolean checkNull) {
        if (name == null) {
            if (checkNull) {
                throw new ValidatorException(MESSAGE_CERTIFICATE_NAME_FILL);
            }
        } else {
            if (name.length() < 3 || name.length() > 100) {
                throw new ValidatorException(MESSAGE_CERTIFICATE_NAME_LENGTH);
            }
        }
    }

    public static void checkDescriptionCertificate(String description, boolean checkNull) {
        if (description == null) {
            if (checkNull) {
                throw new ValidatorException(MESSAGE_CERTIFICATE_DESCRIPTION_FILL);
            }
        } else {
            if (description.length() < 3 || description.length() > 1000) {
                throw new ValidatorException(MESSAGE_CERTIFICATE_DESCRIPTION_LENGTH);
            }
        }
    }

    public static void checkDurationCertificate(int duration, boolean checkNull) {
        if (duration == 0 && checkNull || duration < 0) {
            throw new ValidatorException(MESSAGE_CERTIFICATE_DURATION);
        }
    }

    public static void checkPriceCertificate(double price, boolean checkNull) {
        if (price == 0 && checkNull || price < 0) {
            throw new ValidatorException(MESSAGE_CERTIFICATE_PRICE);
        }
    }
}
