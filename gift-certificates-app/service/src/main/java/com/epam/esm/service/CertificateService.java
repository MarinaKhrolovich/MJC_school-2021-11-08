package com.epam.esm.service;

import com.epam.esm.bean.Certificate;

import java.util.List;

public interface CertificateService {

    void add(Certificate certificate);

    Certificate get(int id);

    List<Certificate> get();

    void update(Certificate certificate);

    void delete(int id);

}
