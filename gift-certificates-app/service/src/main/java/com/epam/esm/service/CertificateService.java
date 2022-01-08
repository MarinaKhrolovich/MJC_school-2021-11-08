package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDTO;
import com.epam.esm.dto.SearchDTO;
import com.epam.esm.dto.SortDTO;

import java.util.List;

/**
 * Service interface responsible for CRUD operation with object Certificate
 * @author Marina Khrolovich
 */
public interface CertificateService {

    /**
     * Add Certificate to the database according to provided object Certificate
     * @param certificate is Object {@link CertificateDTO}
     */
    CertificateDTO add(CertificateDTO certificate);

    /**
     * Get Certificate from the database according to provided id
     * @param id is id of Certificate {@link CertificateDTO} to be getting
     * @return certificate is object {@link CertificateDTO}
     */
    CertificateDTO get(int id);

    /**
     * Get all Certificates from the database
     * @param sort is object {@link SortDTO}  with parameters for sorting
     * @param search is object {@link SearchDTO} with parameters for searching
     * @return list of certificate {@link CertificateDTO}
     */
    List<CertificateDTO> get(SortDTO sort, SearchDTO search);

    /**
     * Update Certificate at the database according to provided data
     * @param id is id of Certificate {@link CertificateDTO} to be updating
     * @param certificate is object {@link CertificateDTO}
     * @return updated certificate {@link CertificateDTO}
     */
    CertificateUpdateDTO update(int id, CertificateUpdateDTO certificate);

    /**
     * Delete Certificate from the database according to provided id
     * @param id is id of Certificate {@link CertificateDTO} to be deleting
     */
    void delete(int id);

}
