package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, OrderMapper orderMapper) {
        this.orderDAO = orderDAO;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDTO add(OrderDTO order) {
        return null;
    }

    @Override
    public OrderDTO get(int id) {
        return null;
    }

    @Override
    public List<OrderDTO> get() {
        return null;
    }

}
