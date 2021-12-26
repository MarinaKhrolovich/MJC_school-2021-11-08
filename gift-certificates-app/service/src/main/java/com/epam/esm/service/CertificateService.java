package com.epam.esm.service;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.OrderDTO;
import com.epam.esm.bean.SearchDTO;

import java.util.List;

public interface CertificateService {

    void add(Certificate certificate);

    Certificate get(int id);

    List<Certificate> get(OrderDTO orderDTO, SearchDTO searchDTO);

    Certificate update(int id, Certificate certificate);

    void delete(int id);

}
