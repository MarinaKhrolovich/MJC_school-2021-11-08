package com.epam.esm.service.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Tag;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.CertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.CertificateService;
import com.epam.esm.validator.CertificateCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {


    private final CertificateDAO certificateDAO;
    private final TagDAO tagDAO;
    private final CertificateTagDAO certificateTagDAO;

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO, TagDAO tagDAO, CertificateTagDAO certificateTagDAO) {
        this.certificateDAO = certificateDAO;
        this.tagDAO = tagDAO;
        this.certificateTagDAO = certificateTagDAO;
    }

    @Override
    @Transactional
    public void add(Certificate certificate) {
        CertificateCheck.checkCertificate(certificate);

        certificate.setCreate_date(Instant.now());
        certificate.setLast_update_date(Instant.now());

        certificateDAO.add(certificate);
        addTagsToCertificate(certificate);

    }

    @Override
    @Transactional
    public Certificate get(int id) {
        Certificate certificate = certificateDAO.get(id);
        setTagList(id, certificate);
        return certificate;
    }

    @Override
    @Transactional
    public List<Certificate> get(String orderByDate, String orderByName, String tagName, String certificateName, String certificateDescription) {
        List<Certificate> certificateList = certificateDAO.get(orderByDate, orderByName, tagName, certificateName, certificateDescription);
        certificateList.forEach(certificate -> setTagList(certificate.getId(), certificate));
        return certificateList;
    }

    @Override
    @Transactional
    public void update(int id, Certificate certificate) {
        certificateDAO.get(id);
        certificate.setId(id);
        certificateDAO.update(id, certificate);
        addTagsToCertificate(certificate);
    }

    @Override
    @Transactional
    public void delete(int id) {
        certificateDAO.get(id);
        certificateTagDAO.deleteTagsOfCertificate(id);
        certificateDAO.delete(id);
    }

    private void addTagsToCertificate(Certificate certificate) {
        List<Tag> tagList = certificate.getTagList();
        if (tagList != null) {
            for (Tag tag : tagList) {
                Tag tagFromBase = tagDAO.get(tag.getName());
                if (tagFromBase == null) {
                    tagDAO.add(tag);
                } else {
                    tag.setId(tagFromBase.getId());
                }
                Tag tagOfCertificate = certificateTagDAO.getTagOfCertificate(certificate.getId(), tag.getId());
                if (tagOfCertificate == null) {
                    certificateTagDAO.addTagToCertificate(certificate.getId(), tag.getId());
                }
            }
        }
    }

    private void setTagList(int id, Certificate certificate) {
        List<Tag> tagList = certificateTagDAO.getAllTagsOfCertificate(id);
        certificate.setTagList(tagList);
    }
}
