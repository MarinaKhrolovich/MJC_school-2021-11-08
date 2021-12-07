package com.epam.esm.model;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Tag;

import java.util.List;

public interface CertificateDAO {

    void add(Certificate certificate);

    Certificate get(int id);

    List<Certificate> get(String orderByDate, String orderByName, String tagName, String certificateName, String certificateDescription);

    void update(int id, Certificate certificate);

    void delete(int id);

    List<Tag> getTagsOfCertificate(int id);
}
