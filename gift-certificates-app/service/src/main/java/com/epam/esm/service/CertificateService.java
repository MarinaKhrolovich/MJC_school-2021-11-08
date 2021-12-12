package com.epam.esm.service;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.RequestParameters;

import java.util.List;

public interface CertificateService {

    void add(Certificate certificate);

    Certificate get(int id);

    List<Certificate> get(RequestParameters requestParameters);

    void update(int id, Certificate certificate);

    void delete(int id);

}
