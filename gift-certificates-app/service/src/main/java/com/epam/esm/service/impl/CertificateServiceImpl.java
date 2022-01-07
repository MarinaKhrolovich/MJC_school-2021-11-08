package com.epam.esm.service.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.OrderDTO;
import com.epam.esm.bean.SearchDTO;
import com.epam.esm.bean.Tag;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.CertificateTagDAO;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {


    private final CertificateDAO certificateDAO;
    private final CertificateTagDAO certificateTagDAO;

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO, CertificateTagDAO certificateTagDAO) {
        this.certificateDAO = certificateDAO;
        this.certificateTagDAO = certificateTagDAO;
    }

    @Override
    @Transactional
    public void add(Certificate certificate) {
        certificateDAO.add(certificate);
    }

    @Override
    public Certificate get(int id) {
        Certificate certificate = certificateDAO.get(id);
        setTagList(id, certificate);
        return certificate;
    }

    @Override
    public List<Certificate> get(OrderDTO orderDTO, SearchDTO searchDTO) {
        List<Certificate> certificateList = certificateDAO.get(orderDTO, searchDTO);
        certificateList.forEach(certificate -> setTagList(certificate.getId(), certificate));
        return certificateList;
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

    private void setTagList(int id, Certificate certificate) {
        List<Tag> tagList = certificateTagDAO.getAllTagsOfCertificate(id);
        certificate.setTagList(tagList);
    }
}
