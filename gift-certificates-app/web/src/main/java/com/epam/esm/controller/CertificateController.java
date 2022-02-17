package com.epam.esm.controller;

import com.epam.esm.dto.*;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Validated
@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private final CertificateService certificateService;
    private final CertificateMapper certificateMapper;

    @Autowired
    public CertificateController(CertificateService certificateService, CertificateMapper certificateMapper) {
        this.certificateService = certificateService;
        this.certificateMapper = certificateMapper;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CertificateDTO addCertificate(@Valid @RequestBody CertificateDTO certificate) {
        CertificateDTO addedDTO = certificateService.add(certificate);
        int id = addedDTO.getId();
        addedDTO.add(linkTo(methodOn(CertificateController.class).getCertificate(id)).withSelfRel());
        setTagLink(addedDTO.getTagList());
        return addedDTO;
    }

    @GetMapping("/{id}")
    public CertificateDTO getCertificate(@PathVariable @Min(1) int id) {
        CertificateDTO certificateDTO = certificateService.get(id);
        certificateDTO.add(linkTo(methodOn(CertificateController.class).getCertificate(id)).withSelfRel());
        setTagLink(certificateDTO.getTagList());
        return certificateDTO;
    }

    @GetMapping()
    public List<CertificateDTO> getCertificates(PageDTO pageDTO, SortDTO sortDTO, SearchDTO searchDTO) {
        List<CertificateDTO> certificateDTOS = certificateService.get(pageDTO, sortDTO, searchDTO);
        if (!CollectionUtils.isEmpty(certificateDTOS)) {
            certificateDTOS.forEach(certificateDTO -> {
                int id = certificateDTO.getId();
                certificateDTO.add(linkTo(methodOn(CertificateController.class).getCertificate(id)).withSelfRel());
                setTagLink(certificateDTO.getTagList());
            });
        }
        return certificateDTOS;
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CertificateUpdateDTO patchUpdateCertificate(@PathVariable @Min(1) int id,
                                                       @Valid @RequestBody CertificateUpdateDTO certificate) {
        CertificateUpdateDTO updateDTO = certificateService.update(id, certificate);
        updateDTO.add(linkTo(methodOn(CertificateController.class).getCertificate(id)).withSelfRel());
        setTagLink(updateDTO.getTagList());
        return updateDTO;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CertificateDTO putUpdateCertificate(@PathVariable @Min(1) int id,
                                               @Valid @RequestBody CertificateDTO certificateDTO) {
        CertificateUpdateDTO certificateUpdateDTO =
                certificateService.update(id, certificateMapper.convertToUpdateDTO(certificateDTO));
        CertificateDTO certificate = certificateMapper.convertToUpdateEntity(certificateUpdateDTO);
        certificate.add(linkTo(methodOn(CertificateController.class).getCertificate(id)).withSelfRel());
        setTagLink(certificate.getTagList());
        return certificate;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCertificate(@PathVariable @Min(1) int id) {
        certificateService.delete(id);
    }

    private void setTagLink(Set<TagDTO> tagList) {
        if (!CollectionUtils.isEmpty(tagList)) {
            tagList.forEach(tagDTO -> {
                int tagId = tagDTO.getId();
                tagDTO.add(linkTo(methodOn(TagController.class).getTag(tagId)).withSelfRel());
            });
        }
    }

}
