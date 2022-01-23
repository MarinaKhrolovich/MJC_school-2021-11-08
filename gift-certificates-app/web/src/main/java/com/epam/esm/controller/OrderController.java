package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public List<OrderDTO> get(PageDTO pageDTO) {
        List<OrderDTO> orderDTOS = orderService.get(pageDTO);
        if (!CollectionUtils.isEmpty(orderDTOS)) {
            orderDTOS.forEach(orderDTO -> {
                addUserLink(orderDTO.getUser());
                addCertificateLink(orderDTO.getCertificates());
            });
        }
        return orderDTOS;
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
