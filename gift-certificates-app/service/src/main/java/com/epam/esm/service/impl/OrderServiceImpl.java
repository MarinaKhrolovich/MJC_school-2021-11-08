package com.epam.esm.service.impl;

import com.epam.esm.bean.Order;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public OrderDTO add(OrderDTO orderDTO) {
        Order addedOrder = orderDAO.add(orderMapper.convertToEntity(orderDTO));
        return orderMapper.convertToDTO(addedOrder);
    }

    @Override
    public OrderDTO get(int id) {
        return orderMapper.convertToDTO(orderDAO.get(id));
    }

    @Override
    public List<OrderDTO> get() {
        return orderDAO.get().stream().map(orderMapper::convertToDTO).collect(Collectors.toList());
    }

}
