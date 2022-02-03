package com.epam.esm.dao.impl;

import com.epam.esm.bean.Page;
import com.epam.esm.bean.User;
import com.epam.esm.config.ConfigDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ConfigDAO.class})
@TestPropertySource(
        locations = "classpath:properties/application-test.properties")
@Sql(scripts = "classpath:db_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserDAOImplTest {

    public static final int ID_EXISTS = 1;
    public static final int ID_NOT_EXISTS = 100;
    public static final String USER_EXISTS = "admin";
    public static final String NEW_USER = "new user";
    public static final int EXPECTED_SIZE = 2;

    @Autowired
    private UserDAO userDAO;

    @BeforeAll
    public static void initUser() {
        User userExpected = new User();
        userExpected.setUsername(NEW_USER);
        userExpected.setPassword(NEW_USER);

        User userExists = new User();
        userExists.setUsername(USER_EXISTS);
        userExpected.setPassword(NEW_USER);
    }

    @Test
    public void getShouldBeNotNull() {
        assertNotNull(userDAO.get(ID_EXISTS));
    }

    @Test
    public void getShouldException() {
        assertThrows(ResourceNotFoundException.class, () -> userDAO.get(ID_NOT_EXISTS));
    }

    @Test
    public void get() {
        assertEquals(EXPECTED_SIZE, userDAO.get(new Page(10, 0)).size());
    }

}