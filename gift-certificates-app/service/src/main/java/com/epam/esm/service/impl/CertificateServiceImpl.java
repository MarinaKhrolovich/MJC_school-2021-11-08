package com.epam.esm.service.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dto.*;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.PageMapper;
import com.epam.esm.mapper.SortSearchMapper;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDAO certificateDAO;
    private final CertificateMapper certificateMapper;
    private final SortSearchMapper sortSearchMapper;
    private final PageMapper pageMapper;

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO, CertificateMapper certificateMapper,
                                  SortSearchMapper sortSearchMapper, PageMapper pageMapper) {
        this.certificateDAO = certificateDAO;
        this.certificateMapper = certificateMapper;
        this.sortSearchMapper = sortSearchMapper;
        this.pageMapper = pageMapper;
    }

    @Override
    @Transactional
    public CertificateDTO add(CertificateDTO certificateDTO) {
        Certificate addedCertificate = certificateDAO.add(certificateMapper.convertToEntity(certificateDTO));
        return certificateMapper.convertToDTO(addedCertificate);
    }

    @Override
    public CertificateDTO get(int id) {
        return certificateMapper.convertToDTO(certificateDAO.get(id));
    }

    @Override
    public List<CertificateDTO> get(PageDTO pageDTO, SortDTO sort, SearchDTO search) {
        return certificateDAO.get(pageMapper.convertToEntity(pageDTO),
                        sortSearchMapper.convertToEntity(sort), sortSearchMapper.convertToEntity(search))
                .stream().map(certificateMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CertificateUpdateDTO update(int id, CertificateUpdateDTO certificate) {
        Certificate updatedCertificate = certificateDAO.update(id, certificateMapper.convertToEntity(certificate));
        return certificateMapper.convertToUpdateDTO(updatedCertificate);
    }

    @Override
    public void delete(int id) {
        certificateDAO.delete(id);
    }

}
