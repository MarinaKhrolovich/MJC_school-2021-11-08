package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Tag;
import com.epam.esm.config.ConfigTest;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.CertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ConfigTest.class})
@SqlGroup({
        @Sql("classpath:db_schema.sql"),
        @Sql("classpath:db_data.sql")
})
@Sql(scripts = "classpath:drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CertificateDAOImplTest {

    @Autowired
    private CertificateDAO certificateDAO;
    @Autowired
    private CertificateTagDAO certificateTagDAO;
    @Autowired
    private TagDAO tagDAO;

    private static Certificate certificateExpected;

    public static final int ID_EXISTS = 1;
    public static final int ID_DELETE = 2;
    public static final int ID_NOT_EXISTS = 100;

    public static final String NEW_CERTIFICATE = "new certificate";
    public static final String NEW_TAG = "new tag";

    @BeforeAll
    public static void initTag() {
        List<Tag> tagList = new ArrayList<>();
        Tag newTag = new Tag();
        newTag.setName(NEW_TAG);
        tagList.add(newTag);

        certificateExpected = new Certificate();
        certificateExpected.setName(NEW_CERTIFICATE);
        certificateExpected.setDescription(NEW_CERTIFICATE);
        certificateExpected.setPrice(10.0);
        certificateExpected.setDuration(30);
        certificateExpected.setTagList(tagList);
    }

    @Test
    @Transactional
    public void add() {
        certificateDAO.add(certificateExpected);
        List<Tag> tagList = certificateExpected.getTagList();
        for (Tag tag : tagList) {
            tagDAO.add(tag);
            certificateTagDAO.addTagToCertificate(certificateExpected.getId(), tag.getId());
        }

        Certificate certificateActual = certificateDAO.get(certificateExpected.getId());
        List<Tag> allTagsOfCertificate = certificateTagDAO.getAllTagsOfCertificate(certificateExpected.getId());
        certificateActual.setTagList(allTagsOfCertificate);

        assertEquals(certificateExpected, certificateActual);
    }

    @Test
    @Transactional
    public void delete() {
        certificateTagDAO.deleteTagsOfCertificate(ID_DELETE);
        certificateDAO.delete(ID_DELETE);
        assertThrows(ResourceNotFoundException.class, () -> certificateDAO.get(ID_DELETE));
    }

}
