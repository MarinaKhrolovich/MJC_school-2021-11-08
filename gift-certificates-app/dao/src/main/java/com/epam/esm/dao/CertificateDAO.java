package com.epam.esm.dao;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Sort;
import com.epam.esm.bean.Search;

import java.util.List;

public interface CertificateDAO {

    Certificate add(Certificate certificate);

    Certificate get(int id);

    List<Certificate> get(Sort sort, Search search);

    Certificate update(int id, Certificate certificate);

    void delete(int id);

}
