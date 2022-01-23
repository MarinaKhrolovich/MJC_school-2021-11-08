package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Order;
import com.epam.esm.bean.Page;
import com.epam.esm.config.ConfigDAO;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ConfigDAO.class})
@TestPropertySource(
        locations = "classpath:properties/application-test.properties")
@Sql(scripts = "classpath:db_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class OrderDAOImplTest {

    public static final int ID_EXISTS = 1;
    public static final int ID_NOT_EXISTS = 100;
    public static final int EXPECTED_SIZE = 2;

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CertificateDAO certificateDAO;

    @Test
    public void addExistedCertificate() {
        List<Certificate> certificates = new ArrayList<>();
        certificates.add(certificateDAO.get(ID_EXISTS));
        Order orderExpected = new Order();
        orderExpected.setUser(userDAO.get(ID_EXISTS));
        orderExpected.setCertificates(certificates);

        orderDAO.add(orderExpected);
        Order orderActual = orderDAO.get(orderExpected.getId());
        assertEquals(orderExpected, orderActual);
    }

    @Test
    public void getShouldBeNotNull() {
        assertNotNull(orderDAO.get(ID_EXISTS));
    }

    @Test
    public void getShouldException() {
        assertThrows(ResourceNotFoundException.class, () -> orderDAO.get(ID_NOT_EXISTS));
    }

    @Test
    public void get() {
        assertEquals(EXPECTED_SIZE, orderDAO.get(new Page(10,0)).size());
    }

}