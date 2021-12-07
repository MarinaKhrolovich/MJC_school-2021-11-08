package com.epam.esm.service.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.model.CertificateDAO;
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
    @Transactional
    public Certificate get(int id) {
        return certificateDAO.get(id);
    }

    @Override
    @Transactional
    public List<Certificate> get() {
        return certificateDAO.get();
    }

    @Override
    @Transactional
    public void update(int id, Certificate certificate) {
        certificateDAO.update(id, certificate);
    }

    @Override
    @Transactional
    public void delete(int id) {
        certificateDAO.delete(id);
    }
}
