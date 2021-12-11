package com.epam.esm.dao;

import com.epam.esm.bean.Certificate;

import java.util.List;

public interface CertificateDAO {

    void add(Certificate certificate);

    Certificate get(int id);

    List<Certificate> get(String orderByDate, String orderByName, String tagName, String certificateName, String certificateDescription);

    void update(int id, Certificate certificate);

    void delete(int id);

}