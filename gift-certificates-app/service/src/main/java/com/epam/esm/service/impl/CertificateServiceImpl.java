package com.epam.esm.service.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.OrderDTO;
import com.epam.esm.bean.SearchDTO;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {


    private final CertificateDAO certificateDAO;

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    @Override
    @Transactional
    public void add(Certificate certificate) {
        certificateDAO.add(certificate);
    }

    @Override
    public Certificate get(int id) {
        return certificateDAO.get(id);
    }

    @Override
    public List<Certificate> get(OrderDTO orderDTO, SearchDTO searchDTO) {
        return certificateDAO.get(orderDTO, searchDTO);
    }

    @Override
    @Transactional
    public Certificate update(int id, Certificate certificate) {
        return certificateDAO.update(id, certificate);
    }

    @Override
    public void delete(int id) {
        certificateDAO.delete(id);
    }

}
