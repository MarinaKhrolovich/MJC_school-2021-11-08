package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDTO;
import com.epam.esm.dto.SearchDTO;
import com.epam.esm.dto.SortDTO;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping
    public CertificateDTO addCertificate(@Valid @RequestBody CertificateDTO certificate) {
        return certificateService.add(certificate);
    }

    @GetMapping("/{id}")
    public CertificateDTO getCertificate(@PathVariable @Min(1) int id) {
        return certificateService.get(id);
    }

    @GetMapping()
    public List<CertificateDTO> getCertificates(SortDTO sortDTO, SearchDTO searchDTO) {
        return certificateService.get(sortDTO, searchDTO);
    }

    @PatchMapping("/{id}")
    public CertificateUpdateDTO updateCertificate(@PathVariable @Min(1) int id, @Valid @RequestBody CertificateUpdateDTO certificate) {
        return certificateService.update(id, certificate);
    }

    @DeleteMapping("/{id}")
    public void deleteCertificate(@PathVariable @Min(1) int id) {
        certificateService.delete(id);
    }

}
