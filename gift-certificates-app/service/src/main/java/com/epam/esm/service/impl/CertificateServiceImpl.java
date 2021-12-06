package com.epam.esm.service.impl;

import com.epam.esm.model.CertificateDAO;
import com.epam.esm.service.CertificateService;
import com.epam.esm.bean.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {


    private final CertificateDAO certificateDAO;

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    @Override
    public void add(Certificate certificate) {

    }

    @Override
    public Certificate get(int id) {
        return certificateDAO.get(id);
    }

    @Override
    public List<Certificate> get() {
        return certificateDAO.get();
    }

    @Override
    public void update(int id, Certificate certificate) {

    }

    @Override
    public void delete(int id) {

    }
}
