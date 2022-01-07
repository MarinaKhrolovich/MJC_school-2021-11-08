package com.epam.esm.service.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.OrderDTO;
import com.epam.esm.bean.SearchDTO;
import com.epam.esm.bean.Tag;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.CertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Mock
    CertificateDAO certificateDAO;
    @Mock
    CertificateTagDAO certificateTagDAO;
    @Mock
    TagDAO tagDAO;

    public static final String NEW_TAG = "new tag";
    public static final String NEW_CERTIFICATE = "new certificate";
    public static final String SECOND_CERTIFICATE = "second certificate";
    public static final double PRICE_OF_CERTIFICATE = 10.0;
    public static final int DURATION_OF_CERTIFICATE = 30;
    public static final int ID_EXISTS = 1;
    public static final int ID_NOT_EXISTS = 100;
    public static final int ID_DELETE = 2;

    private static Certificate certificateExpected;
    private static List<Certificate> certificateList;
    private static List<Tag> tagList;

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

        Certificate secondCertificate = new Certificate();
        secondCertificate.setName(SECOND_CERTIFICATE);
        secondCertificate.setDescription(SECOND_CERTIFICATE);
        secondCertificate.setPrice(PRICE_OF_CERTIFICATE);
        secondCertificate.setDuration(DURATION_OF_CERTIFICATE);
        secondCertificate.setTagList(tagList);

        certificateList = new ArrayList<>();
        certificateList.add(certificateExpected);
        certificateList.add(secondCertificate);
    }

    @Test
    public void getAllCertificates() {
        OrderDTO orderDTO = new OrderDTO(null, null);
        SearchDTO searchDTO = new SearchDTO(null, null, null);
        when(certificateDAO.get(orderDTO, searchDTO)).thenReturn(certificateList);
        when(certificateTagDAO.getAllTagsOfCertificate(anyInt())).thenReturn(tagList);

        assertEquals(certificateList, certificateService.get(orderDTO, searchDTO));

        verify(certificateDAO).get(orderDTO, searchDTO);
        verify(certificateTagDAO, times(2)).getAllTagsOfCertificate(anyInt());
        verifyNoMoreInteractions(certificateDAO, certificateTagDAO);
    }

    @Test
    public void getCertificatesByOrderSearch() {
        when(certificateDAO.get(any(OrderDTO.class), any(SearchDTO.class))).thenReturn(certificateList);
        when(certificateTagDAO.getAllTagsOfCertificate(anyInt())).thenReturn(tagList);
        OrderDTO orderDTO = new OrderDTO("DESC", null);
        SearchDTO searchDTO = new SearchDTO("sport", null, null);

        assertEquals(certificateList, certificateService.get(orderDTO, searchDTO));

        verify(certificateDAO).get(orderDTO, searchDTO);
        verify(certificateTagDAO, times(2)).getAllTagsOfCertificate(anyInt());
        verifyNoMoreInteractions(certificateDAO, certificateTagDAO);
    }

    @Test
    public void getShouldBeNotNull() {
        when(certificateDAO.get(ID_EXISTS)).thenReturn(certificateExpected);
        when(certificateTagDAO.getAllTagsOfCertificate(ID_EXISTS)).thenReturn(tagList);

        assertNotNull(certificateService.get(ID_EXISTS));

        verify(certificateDAO).get(ID_EXISTS);
        verify(certificateTagDAO).getAllTagsOfCertificate(ID_EXISTS);
        verifyNoMoreInteractions(certificateDAO, certificateTagDAO);
    }

    @Test
    public void getShouldException() {
        when(certificateDAO.get(ID_NOT_EXISTS)).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> certificateService.get(ID_NOT_EXISTS));
        verify(certificateDAO).get(ID_NOT_EXISTS);
    }

    @Test
    public void add() {
        doNothing().when(certificateDAO).add(certificateExpected);
        certificateService.add(certificateExpected);
        verify(certificateDAO).add(certificateExpected);
    }

    @Test
    public void update() {
        when(certificateDAO.update(ID_EXISTS, certificateExpected)).thenReturn(certificateExpected);
        Certificate actualCertificate = certificateService.update(ID_EXISTS, certificateExpected);
        assertEquals(certificateExpected,actualCertificate);
        verify(certificateDAO).update(ID_EXISTS, certificateExpected);
    }

    @Test
    public void delete() {
        doNothing().when(certificateDAO).delete(ID_DELETE);
        certificateService.delete(ID_DELETE);
        verify(certificateDAO).delete(ID_DELETE);
    }

}
