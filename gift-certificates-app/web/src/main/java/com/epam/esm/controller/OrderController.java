package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    OrderDTO add(@Valid @RequestBody OrderDTO orderDTO){
        return orderService.add(orderDTO);
    }

    @GetMapping("/{id}")
    OrderDTO get(@PathVariable @Min(1) int id){
        return orderService.get(id);
    }

    @GetMapping
    List<OrderDTO> get(){
        return orderService.get();
    }

}
