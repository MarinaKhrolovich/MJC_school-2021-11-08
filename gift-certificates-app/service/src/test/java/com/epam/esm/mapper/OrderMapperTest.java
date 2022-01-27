package com.epam.esm.mapper;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Order;
import com.epam.esm.bean.User;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class OrderMapperTest {

    public static final String USER = "new user";
    public static final int ID_USER = 1;
    public static final double PRICE_OF_CERTIFICATE = 10.00;
    public static final int DURATION_OF_CERTIFICATE = 30;
    public static final String NEW_CERTIFICATE = "new certificate";

    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Test
    void convertToEntity() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID_USER);
        userDTO.setLogin(USER);

        CertificateDTO certificateDTO = new CertificateDTO();
        certificateDTO.setName(NEW_CERTIFICATE);
        certificateDTO.setDescription(NEW_CERTIFICATE);
        certificateDTO.setPrice(BigDecimal.valueOf(PRICE_OF_CERTIFICATE));
        certificateDTO.setDuration(DURATION_OF_CERTIFICATE);

        List<CertificateDTO> certificateDTOs = new ArrayList<>();
        certificateDTOs.add(certificateDTO);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUser(userDTO);
        orderDTO.setCertificates(certificateDTOs);

        Order actualOrder = orderMapper.convertToEntity(orderDTO);

        assertEquals(orderDTO.getId(), actualOrder.getId());
        assertEquals(orderDTO.getUser().getId(), actualOrder.getUser().getId());
        assertEquals(orderDTO.getUser().getLogin(), actualOrder.getUser().getLogin());

        CertificateDTO firstCertificateDTO = orderDTO.getCertificates().get(0);
        Certificate firstCertificate = actualOrder.getCertificates().get(0);

        assertEquals(firstCertificateDTO.getId(), firstCertificate.getId());
        assertEquals(firstCertificateDTO.getName(), firstCertificate.getName());
        assertEquals(firstCertificateDTO.getDescription(), firstCertificate.getDescription());
    }

    @Test
    void convertToDTO() {
        User user = new User();
        user.setId(ID_USER);
        user.setLogin(USER);

        Certificate certificate = new Certificate();
        certificate.setName(NEW_CERTIFICATE);
        certificate.setDescription(NEW_CERTIFICATE);
        certificate.setPrice(BigDecimal.valueOf(PRICE_OF_CERTIFICATE));
        certificate.setDuration(DURATION_OF_CERTIFICATE);

        List<Certificate> certificates = new ArrayList<>();
        certificates.add(certificate);
        Order order = new Order();
        order.setUser(user);
        order.setCertificates(certificates);

        OrderDTO actualOrder = orderMapper.convertToDTO(order);

        assertEquals(order.getId(), actualOrder.getId());
        assertEquals(order.getId(), actualOrder.getId());
        assertEquals(order.getUser().getId(), actualOrder.getUser().getId());
        assertEquals(order.getUser().getLogin(), actualOrder.getUser().getLogin());

        Certificate firstCertificate = order.getCertificates().get(0);
        CertificateDTO firstCertificateDTO = actualOrder.getCertificates().get(0);

        assertEquals(firstCertificate.getId(), firstCertificateDTO.getId());
        assertEquals(firstCertificate.getName(), firstCertificateDTO.getName());
        assertEquals(firstCertificate.getDescription(), firstCertificateDTO.getDescription());
    }

}
