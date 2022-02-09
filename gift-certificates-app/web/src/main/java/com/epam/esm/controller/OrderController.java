package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Validated
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderDTO add(@Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO addedDTO = orderService.add(orderDTO);
        addUserLink(addedDTO.getUser());
        addCertificateLink(addedDTO.getCertificates());
        return addedDTO;
    }

    @GetMapping("/{id}")
    public OrderDTO get(@PathVariable @Min(1) int id) {
        OrderDTO orderDTO = orderService.get(id);
        orderDTO.add(linkTo(methodOn(OrderController.class).get(id)).withSelfRel());

        addUserLink(orderDTO.getUser());
        addCertificateLink(orderDTO.getCertificates());

        return orderDTO;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<OrderDTO> get(PageDTO pageDTO) {
        List<OrderDTO> orderDTOS = orderService.get(pageDTO);
        return getOrderDTOS(orderDTOS);
    }

    @GetMapping("/users/{id}")
    public List<OrderDTO> getUserOrder(@PathVariable @Min(1) int id, PageDTO pageDTO) {
        List<OrderDTO> orderDTOs = orderService.getUserOrders(id, pageDTO);
        return getOrderDTOS(orderDTOs);
    }

    private List<OrderDTO> getOrderDTOS(List<OrderDTO> orderDTOs) {
        if (!CollectionUtils.isEmpty(orderDTOs)) {
            orderDTOs.forEach(orderDTO -> {
                addUserLink(orderDTO.getUser());
                addCertificateLink(orderDTO.getCertificates());
                orderDTO.add(linkTo(methodOn(OrderController.class).get(orderDTO.getId())).withSelfRel());
            });
        }
        return orderDTOs;
    }

    private void addCertificateLink(List<CertificateDTO> certificates) {
        if (!CollectionUtils.isEmpty(certificates)) {
            certificates.forEach(certificateDTO -> {
                int certificateId = certificateDTO.getId();
                certificateDTO.add(linkTo(methodOn(CertificateController.class)
                        .getCertificate(certificateId)).withSelfRel());
            });
        }
    }

    private void addUserLink(UserDTO user) {
        user.add(linkTo(methodOn(UserController.class).get(user.getId())).withSelfRel());
    }

}
