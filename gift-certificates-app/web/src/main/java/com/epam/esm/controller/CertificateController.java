package com.epam.esm.controller;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.OrderDTO;
import com.epam.esm.bean.SearchDTO;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
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

    @GetMapping()
    public List<Certificate> getCertificates(OrderDTO orderDTO, SearchDTO searchDTO) {
        return certificateService.get(orderDTO, searchDTO);
    }

    @PutMapping("/{id}")
    public Certificate updateCertificate(@PathVariable int id, @RequestBody Certificate certificate) {
        return certificateService.update(id, certificate);
    }

    @DeleteMapping("/{id}")
    public void deleteCertificate(@PathVariable int id) {
        certificateService.delete(id);
    }

}
