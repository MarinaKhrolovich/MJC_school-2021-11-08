package com.epam.esm.controller;

import com.epam.esm.model.bean.Certificate;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/certificate")
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping
    public Certificate addCertificate(@RequestBody Certificate certificate) {
        certificateService.add(certificate);
        return certificate;
    }

    @GetMapping("/{id}")
    public Certificate getCertificate(@PathVariable int id) {
        return certificateService.get(id);
    }

    @PutMapping
    public Certificate updateCertificate(@RequestBody Certificate certificate) {
        certificateService.update(certificate);
        return certificate;
    }

    @DeleteMapping("/{id}")
    public void deleteCertificate(@PathVariable int id) {
        certificateService.delete(id);
    }

}
