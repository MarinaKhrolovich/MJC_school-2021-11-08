package com.epam.esm.service.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.OrderDTO;
import com.epam.esm.bean.SearchDTO;
import com.epam.esm.bean.Tag;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.CertificateMapperImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Mock
    CertificateDAO certificateDAO;
    @Mock
    private CertificateMapperImpl certificateMapper;

    public static final String NEW_TAG = "new tag";
    public static final String NEW_CERTIFICATE = "new certificate";
    public static final String SECOND_CERTIFICATE = "second certificate";
    public static final double PRICE_OF_CERTIFICATE = 10.0;
    public static final int DURATION_OF_CERTIFICATE = 30;
    public static final int ID_EXISTS = 1;
    public static final int ID_NOT_EXISTS = 100;
    public static final int ID_DELETE = 2;

    private static Certificate certificateExpected;
    private static Certificate secondCertificate;
    private static List<Certificate> certificateList;
    private static List<Tag> tagList;

    private static CertificateDTO certificateExpectedDTO;
    private static CertificateUpdateDTO certificateExpectedUpdateDTO;
    private static CertificateDTO secondCertificateDTO;
    private static List<CertificateDTO> certificateListDTO;
    private static List<TagDTO> tagListDTO;

    @BeforeAll
    static void beforeAll() {
        tagList = new ArrayList<>();
        Tag newTag = new Tag();
        newTag.setName(NEW_TAG);
        tagList.add(newTag);

        certificateExpected = new Certificate();
        certificateExpected.setName(NEW_CERTIFICATE);
        certificateExpected.setDescription(NEW_CERTIFICATE);
        certificateExpected.setPrice(PRICE_OF_CERTIFICATE);
        certificateExpected.setDuration(DURATION_OF_CERTIFICATE);
        certificateExpected.setTagList(tagList);

        secondCertificate = new Certificate();
        secondCertificate.setName(SECOND_CERTIFICATE);
        secondCertificate.setDescription(SECOND_CERTIFICATE);
        secondCertificate.setPrice(PRICE_OF_CERTIFICATE);
        secondCertificate.setDuration(DURATION_OF_CERTIFICATE);
        secondCertificate.setTagList(tagList);

        certificateList = new ArrayList<>();
        certificateList.add(certificateExpected);
        certificateList.add(secondCertificate);

        tagListDTO = new ArrayList<>();
        TagDTO newTagDTO = new TagDTO();
        newTagDTO.setName(NEW_TAG);
        tagListDTO.add(newTagDTO);

        certificateExpectedDTO = new CertificateDTO();
        certificateExpectedDTO.setName(NEW_CERTIFICATE);
        certificateExpectedDTO.setDescription(NEW_CERTIFICATE);
        certificateExpectedDTO.setPrice(PRICE_OF_CERTIFICATE);
        certificateExpectedDTO.setDuration(DURATION_OF_CERTIFICATE);
        certificateExpectedDTO.setTagList(tagListDTO);

        certificateExpectedUpdateDTO = new CertificateUpdateDTO();
        certificateExpectedUpdateDTO.setName(NEW_CERTIFICATE);
        certificateExpectedUpdateDTO.setDescription(NEW_CERTIFICATE);
        certificateExpectedUpdateDTO.setPrice(PRICE_OF_CERTIFICATE);
        certificateExpectedUpdateDTO.setDuration(DURATION_OF_CERTIFICATE);
        certificateExpectedUpdateDTO.setTagList(tagListDTO);

        secondCertificateDTO = new CertificateDTO();
        secondCertificateDTO.setName(NEW_CERTIFICATE);
        secondCertificateDTO.setDescription(NEW_CERTIFICATE);
        secondCertificateDTO.setPrice(PRICE_OF_CERTIFICATE);
        secondCertificateDTO.setDuration(DURATION_OF_CERTIFICATE);
        secondCertificateDTO.setTagList(tagListDTO);

        certificateListDTO = new ArrayList<>();
        certificateListDTO.add(certificateExpectedDTO);
        certificateListDTO.add(secondCertificateDTO);
    }

    @Test
    public void getAllCertificates() {
        OrderDTO orderDTO = new OrderDTO(null, null);
        SearchDTO searchDTO = new SearchDTO(null, null, null);
        when(certificateDAO.get(orderDTO, searchDTO)).thenReturn(certificateList);
        when(certificateMapper.convertToDTO(certificateExpected)).thenReturn(certificateExpectedDTO);
        when(certificateMapper.convertToDTO(secondCertificate)).thenReturn(secondCertificateDTO);

        assertEquals(certificateListDTO, certificateService.get(orderDTO, searchDTO));

        verify(certificateDAO).get(orderDTO, searchDTO);
        verify(certificateMapper,times(2)).convertToDTO(any(Certificate.class));
        verifyNoMoreInteractions(certificateDAO,certificateMapper);
    }

    @Test
    public void getCertificatesByOrderSearch() {
        OrderDTO orderDTO = new OrderDTO("DESC", null);
        SearchDTO searchDTO = new SearchDTO("sport", null, null);
        when(certificateDAO.get(any(OrderDTO.class), any(SearchDTO.class))).thenReturn(certificateList);
        when(certificateMapper.convertToDTO(certificateExpected)).thenReturn(certificateExpectedDTO);
        when(certificateMapper.convertToDTO(secondCertificate)).thenReturn(secondCertificateDTO);

        assertEquals(certificateListDTO, certificateService.get(orderDTO, searchDTO));

        verify(certificateDAO).get(orderDTO, searchDTO);
        verify(certificateMapper,times(2)).convertToDTO(any(Certificate.class));
        verifyNoMoreInteractions(certificateDAO,certificateMapper);
    }

    @Test
    public void getShouldBeNotNull() {
        when(certificateDAO.get(ID_EXISTS)).thenReturn(certificateExpected);
        when(certificateMapper.convertToDTO(certificateExpected)).thenReturn(certificateExpectedDTO);

        assertNotNull(certificateService.get(ID_EXISTS));

        verify(certificateDAO).get(ID_EXISTS);
        verify(certificateMapper).convertToDTO(certificateExpected);
        verifyNoMoreInteractions(certificateDAO, certificateMapper);
    }

    @Test
    public void getShouldException() {
        when(certificateDAO.get(ID_NOT_EXISTS)).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> certificateService.get(ID_NOT_EXISTS));
        verify(certificateDAO).get(ID_NOT_EXISTS);
    }

    @Test
    public void add() {
        when(certificateDAO.add(certificateExpected)).thenReturn(certificateExpected);
        when(certificateMapper.сonvertToEntity(certificateExpectedDTO)).thenReturn(certificateExpected);
        when(certificateMapper.convertToDTO(certificateExpected)).thenReturn(certificateExpectedDTO);

        CertificateDTO actualCertificateDTO = certificateService.add(certificateExpectedDTO);

        assertEquals(certificateExpectedDTO, actualCertificateDTO);
        verify(certificateDAO).add(certificateExpected);
        verify(certificateMapper).сonvertToEntity(certificateExpectedDTO);
        verify(certificateMapper).convertToDTO(certificateExpected);
        verifyNoMoreInteractions(certificateDAO, certificateMapper);
    }

    @Test
    public void update() {
        when(certificateDAO.update(ID_EXISTS, certificateExpected)).thenReturn(certificateExpected);
        when(certificateMapper.сonvertToEntity(certificateExpectedUpdateDTO)).thenReturn(certificateExpected);
        when(certificateMapper.convertToUpdateDTO(certificateExpected)).thenReturn(certificateExpectedUpdateDTO);

        CertificateUpdateDTO actualCertificateDTO = certificateService.update(ID_EXISTS, certificateExpectedUpdateDTO);

        assertEquals(certificateExpectedUpdateDTO, actualCertificateDTO);
        verify(certificateDAO).update(ID_EXISTS, certificateExpected);
        verify(certificateMapper).сonvertToEntity(certificateExpectedUpdateDTO);
        verify(certificateMapper).convertToUpdateDTO(certificateExpected);
        verifyNoMoreInteractions(certificateDAO, certificateMapper);
    }

    @Test
    public void delete() {
        doNothing().when(certificateDAO).delete(ID_DELETE);
        certificateService.delete(ID_DELETE);
        verify(certificateDAO).delete(ID_DELETE);
    }

}
