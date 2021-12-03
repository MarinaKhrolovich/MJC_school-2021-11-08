package com.epam.esm.service;

import com.epam.esm.model.bean.Certificate;

public interface CertificateService {

    void delete(int id);

    void add(Certificate certificate);

    void update(Certificate certificate);

    Certificate get(int id);

    Certificate get(String name);
}
