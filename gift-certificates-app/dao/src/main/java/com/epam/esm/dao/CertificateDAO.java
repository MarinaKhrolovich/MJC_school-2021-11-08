package com.epam.esm.dao;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.OrderDTO;
import com.epam.esm.bean.SearchDTO;

import java.util.List;

public interface CertificateDAO {

    void add(Certificate certificate);

    Certificate get(int id);

    List<Certificate> get(OrderDTO orderDTO, SearchDTO searchDTO);

    void update(int id, Certificate certificate);

    void delete(int id);

}
