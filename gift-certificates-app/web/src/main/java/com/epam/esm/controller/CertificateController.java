package com.epam.esm.controller;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.RequestParameters;
import com.epam.esm.service.CertificateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Certificate> getCertificates(@RequestParam(required = false) String orderByDate,
                                             @RequestParam(required = false) String orderByName,
                                             @RequestParam(required = false) String tagName,
                                             @RequestParam(required = false) String certificateName,
                                             @RequestParam(required = false) String certificateDescription
                                             ) {
        return certificateService.get(new RequestParameters(orderByDate, orderByName, tagName, certificateName, certificateDescription));
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
