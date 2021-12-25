package com.epam.esm.validator;

import com.epam.esm.bean.Certificate;
import com.epam.esm.exception.ValidatorException;
import org.springframework.stereotype.Component;

@Component
public class CertificateCheck {

    public static final String MESSAGE_CERTIFICATE_NAME_FILL = "message.certificate.name.fill";
    public static final String MESSAGE_CERTIFICATE_NAME_LENGTH = "message.certificate.name.length";
    public static final String MESSAGE_CERTIFICATE_DESCRIPTION_FILL = "message.certificate.description.fill";
    public static final String MESSAGE_CERTIFICATE_DESCRIPTION_LENGTH = "message.certificate.description.length";
    public static final String MESSAGE_CERTIFICATE_DURATION = "message.certificate.duration";
    public static final String MESSAGE_CERTIFICATE_PRICE = "message.certificate.price";
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_DESCRIPTION_LENGTH = 1000;
    public static final int MIN_STRING_LENGTH = 3;

    public void check(Certificate certificate, boolean checkNull) {
        checkNameCertificate(certificate.getName(), checkNull);
        checkDescriptionCertificate(certificate.getDescription(), checkNull);
        checkDurationCertificate(certificate.getDuration(), checkNull);
        checkPriceCertificate(certificate.getPrice(), checkNull);
    }

    public void checkNameCertificate(String name, boolean checkNull) {
        if (name == null) {
            if (checkNull) {
                throw new ValidatorException(MESSAGE_CERTIFICATE_NAME_FILL);
            }
        } else {
            if (name.trim().isEmpty() || name.length() < MIN_STRING_LENGTH || name.length() > MAX_NAME_LENGTH) {
                throw new ValidatorException(MESSAGE_CERTIFICATE_NAME_LENGTH);
            }
        }
    }

    public void checkDescriptionCertificate(String description, boolean checkNull) {
        if (description == null) {
            if (checkNull) {
                throw new ValidatorException(MESSAGE_CERTIFICATE_DESCRIPTION_FILL);
            }
        } else {
            if (description.trim().isEmpty() || description.length() < MIN_STRING_LENGTH || description.length() > MAX_DESCRIPTION_LENGTH) {
                throw new ValidatorException(MESSAGE_CERTIFICATE_DESCRIPTION_LENGTH);
            }
        }
    }

    public void checkDurationCertificate(int duration, boolean checkNull) {
        if (duration == 0 && checkNull || duration < 0) {
            throw new ValidatorException(MESSAGE_CERTIFICATE_DURATION);
        }
    }

    public void checkPriceCertificate(double price, boolean checkNull) {
        if (price == 0 && checkNull || price < 0) {
            throw new ValidatorException(MESSAGE_CERTIFICATE_PRICE);
        }
    }
}
