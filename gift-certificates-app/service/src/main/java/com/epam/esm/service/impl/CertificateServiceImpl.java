package com.epam.esm.service.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Tag;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {


    private final CertificateDAO certificateDAO;
    private final TagDAO tagDAO;

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO, TagDAO tagDAO) {
        this.certificateDAO = certificateDAO;
        this.tagDAO = tagDAO;
    }

    @Override
    @Transactional
    public void add(Certificate certificate) {
        certificate.setCreate_date(Instant.now());
        certificate.setLast_update_date(Instant.now());
        List<Tag> tagList = certificate.getTagList();
        certificateDAO.add(certificate);
        if (tagList != null) {
            tagList.forEach(tag -> {
                tagDAO.add(tag);//TODO check tag
                certificateDAO.addTagToCertificate(certificate.getId(), tag.getId());
            });
        }

    }

    @Override
    @Transactional
    public Certificate get(int id) {
        Certificate certificate = certificateDAO.get(id);
        addTagsToCertificate(id, certificate);
        return certificate;
    }

    @Override
    @Transactional
    public List<Certificate> get(String orderByDate, String orderByName, String tagName, String certificateName, String certificateDescription) {
        List<Certificate> certificateList = certificateDAO.get(orderByDate, orderByName, tagName, certificateName, certificateDescription);
        certificateList.forEach(certificate -> addTagsToCertificate(certificate.getId(), certificate));
        return certificateList;
    }

    @Override
    @Transactional
    public void update(int id, Certificate certificate) {
        certificateDAO.update(id, certificate);
    }

    @Override
    @Transactional
    public void delete(int id) {
        certificateDAO.get(id);
        certificateDAO.deleteTagsOfCertificate(id);
        certificateDAO.delete(id);
    }

    private void addTagsToCertificate(int id, Certificate certificate) {
        List<Tag> tagList = certificateDAO.getTagsOfCertificate(id);
        certificate.setTagList(tagList);
    }
}
