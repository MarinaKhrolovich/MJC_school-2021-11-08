package com.epam.esm.controller;

import com.epam.esm.dto.*;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping
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
    public CertificateUpdateDTO patchUpdateCertificate(@PathVariable @Min(1) int id,
                                                       @Valid @RequestBody CertificateUpdateDTO certificate) {
        return updateCertificate(id, certificate);
    }

    @PutMapping("/{id}")
    public CertificateUpdateDTO putUpdateCertificate(@PathVariable @Min(1) int id,
                                                     @Valid @RequestBody CertificateUpdateDTO certificate) {
        return updateCertificate(id, certificate);
    }

    private CertificateUpdateDTO updateCertificate(@PathVariable @Min(1) int id,
                                                   @RequestBody @Valid CertificateUpdateDTO certificate) {
        CertificateUpdateDTO updateDTO = certificateService.update(id, certificate);
        updateDTO.add(linkTo(methodOn(CertificateController.class).getCertificate(id)).withSelfRel());
        Set<TagDTO> tagList = updateDTO.getTagList();
        if (!CollectionUtils.isEmpty(tagList)) {
            tagList.forEach(tagDTO -> {
                int tagId = tagDTO.getId();
                tagDTO.add(linkTo(methodOn(TagController.class).getTag(tagId)).withSelfRel());
            });
        }
        return updateDTO;
    }

    @DeleteMapping("/{id}")
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
