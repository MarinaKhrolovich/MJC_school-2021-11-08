package com.epam.esm.mapper;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Tag;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDTO;
import com.epam.esm.dto.TagDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class CertificateMapperTest {

    public static final double PRICE_OF_EXPECTED_CERTIFICATE = 10.00;
    public static final int DURATION_OF_EXPECTED_CERTIFICATE = 30;
    public static final String NEW_CERTIFICATE = "new certificate";
    public static final String NEW_TAG = "new tag";

    private final CertificateMapper certificateMapper = Mappers.getMapper(CertificateMapper.class);

    private static Certificate certificate;
    private static CertificateDTO certificateDTO;
    private static CertificateUpdateDTO certificateUpdateDTO;
    private static Set<TagDTO> tagListDTO;
    private static Set<Tag> tagList;

    @BeforeAll
    public static void initCertificate() {
        tagList = new HashSet<>();
        Tag newTag = new Tag();
        newTag.setName(NEW_TAG);
        tagList.add(newTag);

        tagListDTO = new HashSet<>();
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
    void convertToEntity() {
        Certificate certificate = certificateMapper.convertToEntity(certificateDTO);

        assertEquals(certificateDTO.getName(), certificate.getName());
        assertEquals(certificateDTO.getDescription(), certificate.getDescription());
        assertEquals(certificateDTO.getPrice(), certificate.getPrice());
        assertEquals(certificateDTO.getDuration(), certificate.getDuration());
        assertEquals(tagList, certificate.getTagList());
    }

    @Test
    void convertUpdateDTOToEntity() {
        Certificate certificate = certificateMapper.convertToEntity(certificateUpdateDTO);

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