package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Search;
import com.epam.esm.bean.Sort;
import com.epam.esm.bean.Tag;
import com.epam.esm.config.ConfigDAO;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ConfigDAO.class})
@TestPropertySource(
        locations = "classpath:properties/application-test.properties")
@Sql(scripts = "classpath:db_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CertificateDAOImplTest {

    public static final int EXPECTED_SIZE_BY_SEARCH = 1;
    public static final double PRICE_OF_EXPECTED_CERTIFICATE = 10.00;
    public static final int DURATION_OF_EXPECTED_CERTIFICATE = 30;
    public static final int DURATION_OF_UPDATE_CERTIFICATE = 60;
    public static final int ID_EXISTS = 1;
    public static final int ID_DELETE = 2;
    public static final int ID_NOT_EXISTS = 100;
    public static final int EXPECTED_LIST_SIZE = 2;
    public static final String NEW_CERTIFICATE = "new certificate";
    public static final String NEW_TAG = "new tag";
    public static final int ID_FIRST_ELEMENT = 1;
    public static final int ID_SECOND_ELEMENT = 2;
    public static final int EXPECTED_SIZE_BY_INVALID_SEARCH = 0;

    @Autowired
    private CertificateDAO certificateDAO;

    private static Certificate certificateExpected;
    private static Certificate certificateUpdate;

    @BeforeAll
    public static void initCertificate() {
        List<Tag> tagList = new ArrayList<>();
        Tag newTag = new Tag();
        newTag.setName(NEW_TAG);
        tagList.add(newTag);

        certificateExpected = new Certificate();
        certificateExpected.setName(NEW_CERTIFICATE);
        certificateExpected.setDescription(NEW_CERTIFICATE);
        certificateExpected.setPrice(BigDecimal.valueOf(PRICE_OF_EXPECTED_CERTIFICATE));
        certificateExpected.setDuration(DURATION_OF_EXPECTED_CERTIFICATE);
        certificateExpected.setTagList(tagList);

        certificateUpdate = new Certificate();
        certificateUpdate.setDescription(NEW_CERTIFICATE);
        certificateUpdate.setDuration(DURATION_OF_UPDATE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void add() {
        certificateDAO.add(certificateExpected);
        Certificate certificateActual = certificateDAO.get(certificateExpected.getId());
        assertEquals(certificateExpected, certificateActual);
    }

    @Test
    public void getAllCertificates() {
        Sort sort = new Sort(null, null);
        Search search = new Search(null, null, null);
        assertEquals(EXPECTED_LIST_SIZE, certificateDAO.get(sort, search).size());
    }

    @Test
    public void getCertificatesBySearch() {
       /* Sort sort = new Sort(null,null);
        Search search = new Search("sport", null, null);
        assertEquals(EXPECTED_SIZE_BY_SEARCH, certificateDAO.get(sort, search).size());

        search = new Search(null, "spo", null);
        assertEquals(EXPECTED_SIZE_BY_SEARCH, certificateDAO.get(sort, search).size());

        search = new Search(null, null, "mas");
        assertEquals(EXPECTED_SIZE_BY_SEARCH, certificateDAO.get(sort, search).size());

        search = new Search("sport", "spo", "mas");
        assertEquals(EXPECTED_SIZE_BY_INVALID_SEARCH, certificateDAO.get(sort, search).size());*/
    }

    @Test
    public void getCertificatesByOrder() {
       /* Search search = new Search(null, null, null);
        Sort sort = new Sort(null, "DESC");
        Certificate firstCertificate = certificateDAO.get(ID_FIRST_ELEMENT);
        Certificate secondCertificate = certificateDAO.get(ID_SECOND_ELEMENT);
        List<Certificate> expectedList = Arrays.asList(firstCertificate, secondCertificate);
        assertEquals(expectedList, certificateDAO.get(sort, search));

        expectedList = Arrays.asList(secondCertificate, firstCertificate);
        sort = new Sort("ASC", null);
        assertEquals(expectedList, certificateDAO.get(sort, search));*/
    }

    @Test
    public void getShouldBeNotNull() {
        assertNotNull(certificateDAO.get(ID_EXISTS));
    }

    @Test
    public void getShouldException() {
        assertThrows(ResourceNotFoundException.class, () -> certificateDAO.get(ID_NOT_EXISTS));
    }

    @Test
    @Transactional
    public void update() {
        Certificate certificateActual = certificateDAO.update(ID_EXISTS, certificateUpdate);
        assertEquals(certificateUpdate.getDescription(), certificateActual.getDescription());
        assertEquals(certificateUpdate.getDuration(), certificateActual.getDuration());
    }

    @Test
    public void delete() {
        certificateDAO.delete(ID_DELETE);
        assertThrows(ResourceNotFoundException.class, () -> certificateDAO.get(ID_DELETE));
    }

}
