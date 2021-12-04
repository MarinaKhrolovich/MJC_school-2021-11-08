package com.epam.esm.model;

import com.epam.esm.bean.Certificate;

public interface CertificateDAO {

    void delete(int id);

    void add(Certificate certificate);

    void update(Certificate certificate);

    void get(int id);

    Certificate get(String name);
}
