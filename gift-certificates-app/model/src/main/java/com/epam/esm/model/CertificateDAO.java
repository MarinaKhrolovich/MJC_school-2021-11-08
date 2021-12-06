package com.epam.esm.model;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Tag;

import java.util.List;

public interface CertificateDAO {

    void add(Certificate certificate);

    Certificate get(int id);

    List<Certificate> get();

    void update(int id, Certificate certificate);

    void delete(int id);
}
