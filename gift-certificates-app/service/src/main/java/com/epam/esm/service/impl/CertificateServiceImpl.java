package com.epam.esm.service.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.OrderDTO;
import com.epam.esm.bean.SearchDTO;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDTO;
import com.epam.esm.mapper.CertificateMapper;
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

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO, CertificateMapper certificateMapper) {
        this.certificateDAO = certificateDAO;
        this.certificateMapper = certificateMapper;
    }

    @Override
    @Transactional
    public void add(CertificateDTO certificateDTO) {
        certificateDAO.add(certificateMapper.сonvertToEntity(certificateDTO));
    }

    @Override
    public CertificateDTO get(int id) {
        return certificateMapper.convertToDTO(certificateDAO.get(id));
    }

    @Override
    public List<CertificateDTO> get(OrderDTO orderDTO, SearchDTO searchDTO) {
        return certificateDAO.get(orderDTO, searchDTO).stream().map(certificateMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CertificateUpdateDTO update(int id, CertificateUpdateDTO certificate) {
        Certificate  updatedCertificate= certificateDAO.update(id, certificateMapper.сonvertToEntity(certificate));
        return certificateMapper.convertToUpdateDTO(updatedCertificate);

    }

    @Override
    public void delete(int id) {
        certificateDAO.delete(id);
    }

}
