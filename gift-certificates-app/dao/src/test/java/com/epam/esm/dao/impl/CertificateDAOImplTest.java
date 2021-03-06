package com.epam.esm.dao.impl;

import com.epam.esm.bean.*;
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
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ConfigDAO.class})
@TestPropertySource(
        locations = "classpath:properties/application-test.properties")
@Sql(scripts = "classpath:db_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CertificateDAOImplTest {

    public static final double PRICE_OF_EXPECTED_CERTIFICATE = 10.00;
    public static final int DURATION_OF_EXPECTED_CERTIFICATE = 30;
    public static final int DURATION_OF_UPDATE_CERTIFICATE = 60;
    public static final int ID_EXISTS = 1;
    public static final int ID_DELETE = 2;
    public static final int ID_NOT_EXISTS = 100;
    public static final int EXPECTED_LIST_SIZE = 2;
    public static final String NEW_CERTIFICATE = "new certificate";
    public static final String NEW_TAG = "new tag";

    @Autowired
    private CertificateDAO certificateDAO;

    private static Certificate certificateExpected;
    private static Certificate certificateUpdate;

    @BeforeAll
    public static void initCertificate() {
        Set<Tag> tagList = new HashSet<>();
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
        Page page = new Page(10, 0);
        assertEquals(EXPECTED_LIST_SIZE, certificateDAO.get(page, sort, search).size());
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
    @Transactional
    public void delete() {
        certificateDAO.delete(ID_DELETE);
        assertThrows(ResourceNotFoundException.class, () -> certificateDAO.get(ID_DELETE));
    }

}
