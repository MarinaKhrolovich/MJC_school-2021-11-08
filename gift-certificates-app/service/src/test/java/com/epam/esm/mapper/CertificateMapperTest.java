package com.epam.esm.mapper;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Tag;
import com.epam.esm.config.ServiceConfig;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDTO;
import com.epam.esm.dto.TagDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfig.class})
class CertificateMapperTest {

    public static final double PRICE_OF_EXPECTED_CERTIFICATE = 10.00;
    public static final int DURATION_OF_EXPECTED_CERTIFICATE = 30;
    public static final String NEW_CERTIFICATE = "new certificate";
    public static final String NEW_TAG = "new tag";

    private final CertificateMapper certificateMapper;

    @Autowired
    CertificateMapperTest(CertificateMapper certificateMapper) {
        this.certificateMapper = certificateMapper;
    }

    private static Certificate certificate;
    private static CertificateDTO certificateDTO;
    private static CertificateUpdateDTO certificateUpdateDTO;
    private static List<TagDTO> tagListDTO;
    private static List<Tag> tagList;

    @BeforeAll
    public static void initCertificate() {
        tagList = new ArrayList<>();
        Tag newTag = new Tag();
        newTag.setName(NEW_TAG);
        tagList.add(newTag);

        tagListDTO = new ArrayList<>();
        TagDTO newTagDTO = new TagDTO();
        newTagDTO.setName(NEW_TAG);
        tagListDTO.add(newTagDTO);

        certificate = new Certificate();
        certificate.setName(NEW_CERTIFICATE);
        certificate.setDescription(NEW_CERTIFICATE);
        certificate.setPrice(BigDecimal.valueOf(PRICE_OF_EXPECTED_CERTIFICATE));
        certificate.setDuration(DURATION_OF_EXPECTED_CERTIFICATE);
        certificate.setTagList(tagList);

        certificateDTO = new CertificateDTO();
        certificateDTO.setName(NEW_CERTIFICATE);
        certificateDTO.setDescription(NEW_CERTIFICATE);
        certificateDTO.setPrice(BigDecimal.valueOf(PRICE_OF_EXPECTED_CERTIFICATE));
        certificateDTO.setDuration(DURATION_OF_EXPECTED_CERTIFICATE);
        certificateDTO.setTagList(tagListDTO);

        certificateUpdateDTO = new CertificateUpdateDTO();
        certificateUpdateDTO.setName(NEW_CERTIFICATE);
        certificateUpdateDTO.setDescription(NEW_CERTIFICATE);
        certificateUpdateDTO.setPrice(BigDecimal.valueOf(PRICE_OF_EXPECTED_CERTIFICATE));
        certificateUpdateDTO.setDuration(DURATION_OF_EXPECTED_CERTIFICATE);
        certificateUpdateDTO.setTagList(tagListDTO);
    }

    @Test
    void сonvertToEntity() {
        Certificate certificate = certificateMapper.сonvertToEntity(certificateDTO);

        assertEquals(certificateDTO.getName(), certificate.getName());
        assertEquals(certificateDTO.getDescription(), certificate.getDescription());
        assertEquals(certificateDTO.getPrice(), certificate.getPrice());
        assertEquals(certificateDTO.getDuration(), certificate.getDuration());
        assertEquals(tagList, certificate.getTagList());
    }

    @Test
    void сonvertUpdateDTOToEntity() {
        Certificate certificate = certificateMapper.сonvertToEntity(certificateUpdateDTO);

        assertEquals(certificateUpdateDTO.getName(), certificate.getName());
        assertEquals(certificateUpdateDTO.getDescription(), certificate.getDescription());
        assertEquals(certificateUpdateDTO.getPrice(), certificate.getPrice());
        assertEquals(certificateUpdateDTO.getDuration(), certificate.getDuration());
        assertEquals(tagList, certificate.getTagList());
    }

    @Test
    void convertToDTO() {
        CertificateDTO certificateDTO = certificateMapper.convertToDTO(certificate);

        assertEquals(certificate.getName(), certificateDTO.getName());
        assertEquals(certificate.getDescription(), certificateDTO.getDescription());
        assertEquals(certificate.getPrice(), certificateDTO.getPrice());
        assertEquals(certificate.getDuration(), certificateDTO.getDuration());
        assertEquals(tagListDTO, certificateDTO.getTagList());
    }

    @Test
    void convertToUpdateDTO() {
        CertificateUpdateDTO certificateUpdateDTO = certificateMapper.convertToUpdateDTO(certificate);

        assertEquals(certificate.getName(), certificateUpdateDTO.getName());
        assertEquals(certificate.getDescription(), certificateUpdateDTO.getDescription());
        assertEquals(certificate.getPrice(), certificateUpdateDTO.getPrice());
        assertEquals(certificate.getDuration(), certificateUpdateDTO.getDuration());
        assertEquals(tagListDTO, certificateUpdateDTO.getTagList());
    }

}