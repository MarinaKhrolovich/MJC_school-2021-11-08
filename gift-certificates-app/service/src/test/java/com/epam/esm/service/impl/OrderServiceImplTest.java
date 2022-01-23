package com.epam.esm.service.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Order;
import com.epam.esm.bean.User;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.OrderMapperImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    OrderDAO orderDAO;
    @Mock
    private OrderMapperImpl orderMapper;

    public static final double PRICE_OF_ORDER = 10.0;

    public static final String LOGIN = "admin";
    public static final String CERTIFICATE_NAME = "certificate";
    public static final int ID_EXISTS = 1;
    public static final int ID_NOT_EXISTS = 100;

    private static List<Order> orderList;
    private static Order orderExpected;
    private static Order secondOrder;

    private static List<OrderDTO> orderListDTO;
    private static OrderDTO orderExpectedDTO;
    private static OrderDTO secondOrderDTO;


    @BeforeAll
    static void beforeAll() {
        User user = new User();
        user.setId(1);
        user.setLogin(LOGIN);

        Certificate certificate = new Certificate();
        certificate.setId(1);
        certificate.setName(CERTIFICATE_NAME);
        List<Certificate> certificates = new ArrayList<>();
        certificates.add(certificate);

        orderExpected = new Order();
        orderExpected.setUser(user);
        orderExpected.setCertificates(certificates);
        orderExpected.setPrice(BigDecimal.valueOf(PRICE_OF_ORDER));
        orderExpected.setCreateDate(Instant.now().truncatedTo(ChronoUnit.MILLIS));

        secondOrder = new Order();
        orderExpected.setUser(user);
        orderExpected.setCertificates(certificates);
        secondOrder.setPrice(BigDecimal.valueOf(PRICE_OF_ORDER));
        orderExpected.setCreateDate(Instant.now().truncatedTo(ChronoUnit.MILLIS));


        orderList = new ArrayList<>();
        orderList.add(orderExpected);
        orderList.add(secondOrder);

        orderExpectedDTO = new OrderDTO();
        orderExpectedDTO.setPrice(BigDecimal.valueOf(PRICE_OF_ORDER));

        secondOrderDTO = new OrderDTO();
        secondOrderDTO.setPrice(BigDecimal.valueOf(PRICE_OF_ORDER));

        orderListDTO = new ArrayList<>();
        orderListDTO.add(orderExpectedDTO);
        orderListDTO.add(secondOrderDTO);
    }

    @Test
    public void add() {
        when(orderDAO.add(orderExpected)).thenReturn(orderExpected);
        when(orderMapper.convertToEntity(orderExpectedDTO)).thenReturn(orderExpected);
        when(orderMapper.convertToDTO(orderExpected)).thenReturn(orderExpectedDTO);
        orderService.add(orderExpectedDTO);

        verify(orderDAO).add(orderExpected);
        verify(orderMapper).convertToEntity(orderExpectedDTO);
        verify(orderMapper).convertToDTO(orderExpected);
        verifyNoMoreInteractions(orderDAO, orderMapper);
    }

    @Test
    public void getShouldBeNotNull() {
        when(orderDAO.get(ID_EXISTS)).thenReturn(orderExpected);
        when(orderMapper.convertToDTO(orderExpected)).thenReturn(orderExpectedDTO);

        assertNotNull(orderService.get(ID_EXISTS));

        verify(orderDAO).get(ID_EXISTS);
        verify(orderMapper).convertToDTO(orderExpected);
        verifyNoMoreInteractions(orderDAO, orderMapper);
    }

    @Test
    public void getShouldException() {
        when(orderDAO.get(ID_NOT_EXISTS)).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> orderService.get(ID_NOT_EXISTS));
        verify(orderDAO).get(ID_NOT_EXISTS);
    }

    @Test
    public void get() {
        when(orderDAO.get()).thenReturn(orderList);
        when(orderMapper.convertToDTO(orderExpected)).thenReturn(orderExpectedDTO);
        when(orderMapper.convertToDTO(secondOrder)).thenReturn(secondOrderDTO);

        assertEquals(orderListDTO, orderService.get());

        verify(orderDAO).get();
        verify(orderMapper, times(2)).convertToDTO(any(Order.class));
        verifyNoMoreInteractions(orderDAO, orderMapper);
    }

}
