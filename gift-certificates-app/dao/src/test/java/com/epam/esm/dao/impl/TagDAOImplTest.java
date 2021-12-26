package com.epam.esm.dao.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.config.ConfigTest;
import com.epam.esm.dao.CertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ResourceAlreadyExistsException;
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

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ConfigTest.class})
@SqlGroup({
        @Sql("classpath:db_schema.sql"),
        @Sql("classpath:db_data.sql")
})
@Sql(scripts = "classpath:drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TagDAOImplTest {

    public static final int ID_EXISTS = 1;
    public static final int ID_DELETE = 2;
    public static final int ID_NOT_EXISTS = 100;
    public static final String TAG_EXISTS = "sport";
    public static final String TAG_NOT_EXISTS = "not exists tag";
    public static final String NEW_TAG = "new tag";
    public static final int EXPECTED_SIZE = 2;

    @Autowired
    private TagDAO tagDAO;
    @Autowired
    private CertificateTagDAO certificateTagDAO;
    private static Tag tagExpected;
    private static Tag tagExists;


    @BeforeAll
    public static void initTag() {
        tagExpected = new Tag();
        tagExpected.setName(NEW_TAG);

        tagExists = new Tag();
        tagExists.setName(TAG_EXISTS);
    }

    @Test
    public void add() {
        tagDAO.add(tagExpected);
        Tag tagActual = tagDAO.get(tagExpected.getId());
        assertEquals(tagExpected, tagActual);
    }

    @Test
    public void addExists() {
        assertThrows(ResourceAlreadyExistsException.class, () -> tagDAO.add(tagExists));
    }

    @Test
    public void getShouldBeNotNull() {
        assertNotNull(tagDAO.get(ID_EXISTS));
    }

    @Test
    public void getShouldException() {
        assertThrows(ResourceNotFoundException.class, () -> tagDAO.get(ID_NOT_EXISTS));
    }

    @Test
    public void getByNameShouldBeNotNull() {
        assertNotNull(tagDAO.get(TAG_EXISTS));
    }

    @Test
    public void getByNameShouldBeNull() {
        assertNull(tagDAO.get(TAG_NOT_EXISTS));
    }

    @Test
    public void get() {
        assertEquals(EXPECTED_SIZE, tagDAO.get().size());
    }

    @Test
    @Transactional
    public void delete() {
        certificateTagDAO.deleteTagFromCertificates(ID_DELETE);
        tagDAO.delete(ID_DELETE);
        assertThrows(ResourceNotFoundException.class, () -> tagDAO.get(ID_DELETE));
    }

}
