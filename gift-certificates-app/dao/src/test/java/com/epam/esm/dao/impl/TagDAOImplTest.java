package com.epam.esm.dao.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.config.ConfigTest;
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

    @Autowired
    private TagDAO tagDAO;
    private static Tag tagExpected;

    @BeforeAll
    public static void initTag() {
        tagExpected = new Tag();
        tagExpected.setName("new tag");
    }

    @Test
    public void add() {
        tagDAO.add(tagExpected);
        Tag tagActual = tagDAO.get(tagExpected.getId());
        assertEquals(tagExpected, tagActual);
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
    public void delete() {
        tagDAO.delete(ID_DELETE);
        assertThrows(ResourceNotFoundException.class, () -> tagDAO.get(ID_DELETE));
    }

}
