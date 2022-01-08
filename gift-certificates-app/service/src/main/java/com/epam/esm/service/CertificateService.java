package com.epam.esm.service;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.OrderDTO;
import com.epam.esm.bean.SearchDTO;
import com.epam.esm.bean.Tag;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDTO;

import java.util.List;

/**
 * Service interface responsible for CRUD operation with object Certificate
 * @author Marina Khrolovich
 */
public interface CertificateService {

    /**
     * Add Certificate to the database according to provided object Certificate
     * @param certificate is Object {@link Certificate}
     */
    void add(CertificateDTO certificate);

    /**
     * Get Certificate from the database according to provided id
     * @param id is id of Certificate {@link Certificate} to be getting
     * @return certificate is object {@link Certificate}
     */
    CertificateDTO get(int id);

    /**
     * Get all Certificates from the database
     * @param orderDTO is object {@link OrderDTO}  with parameters for sorting
     * @param searchDTO is object {@link SearchDTO} with parameters for searching
     * @return list of certificate {@link Certificate}
     */
    List<CertificateDTO> get(OrderDTO orderDTO, SearchDTO searchDTO);

    /**
     * Update Certificate at the database according to provided data
     * @param id is id of Certificate {@link Certificate} to be updating
     * @param certificate is object {@link Certificate}
     * @return updated certificate {@link Certificate}
     */
    CertificateUpdateDTO update(int id, CertificateUpdateDTO certificate);

    /**
     * Delete Certificate from the database according to provided id
     * @param id is id of Certificate {@link Certificate} to be deleting
     */
    void delete(int id);

}
