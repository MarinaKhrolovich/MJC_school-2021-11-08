package com.epam.esm.service.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.OrderDTO;
import com.epam.esm.bean.SearchDTO;
import com.epam.esm.bean.Tag;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.CertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.validator.CertificateCheck;
import com.epam.esm.validator.TagCheck;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

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
    CertificateCheck certificateCheck;
    @Mock
    TagCheck tagCheck;
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

    private static Certificate certificateExpected;
    private static List<Certificate> certificateList;
    private static Tag newTag;

    @BeforeAll
    static void beforeAll() {
        List<Tag> tagList = new ArrayList<>();
        newTag = new Tag();
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
        assertEquals(certificateList, certificateService.get(orderDTO, searchDTO));
        verify(certificateDAO).get(orderDTO, searchDTO);
    }

    @Test
    public void getCertificatesByOrderSearch() {
        when(certificateDAO.get(any(OrderDTO.class), any(SearchDTO.class))).thenReturn(certificateList);
        OrderDTO orderDTO = new OrderDTO("DESC", null);
        SearchDTO searchDTO = new SearchDTO("sport", null, null);
        assertEquals(certificateList, certificateService.get(orderDTO, searchDTO));
        verify(certificateDAO).get(orderDTO, searchDTO);
    }

    @Test
    public void getShouldBeNotNull() {
        when(certificateDAO.get(ID_EXISTS)).thenReturn(certificateExpected);
        assertNotNull(certificateService.get(ID_EXISTS));
        verify(certificateDAO).get(ID_EXISTS);
    }

    @Test
    public void getShouldException() {
        when(certificateDAO.get(ID_NOT_EXISTS)).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> certificateService.get(ID_NOT_EXISTS));
        verify(certificateDAO).get(ID_NOT_EXISTS);
    }

    @Test
    public void add() {
        doNothing().when(certificateCheck).check(certificateExpected,true);
        doNothing().when(certificateDAO).add(certificateExpected);
        certificateService.add(certificateExpected);
        verify(certificateCheck).check(certificateExpected,true);
        verify(certificateDAO).add(certificateExpected);
        verifyNoMoreInteractions(certificateCheck,certificateDAO);
    }

    @Test
    public void update() {
        when(certificateDAO.get(ID_EXISTS)).thenReturn(certificateExpected);
        doNothing().when(certificateCheck).check(certificateExpected,false);
        doNothing().when(certificateDAO).update(ID_EXISTS, certificateExpected);

        certificateService.update(ID_EXISTS, certificateExpected);
/*        verify(certificateDAO).get(ID_EXISTS);
        verify(certificateCheck).check(certificateExpected,true);
        verify(certificateDAO).update(ID_EXISTS,certificateExpected);*/
    }

}
