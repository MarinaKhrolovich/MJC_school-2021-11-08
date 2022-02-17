package com.epam.esm.service.impl;

import com.epam.esm.bean.Order;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.PageMapper;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final OrderMapper orderMapper;
    private final PageMapper pageMapper;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, OrderMapper orderMapper, PageMapper pageMapper) {
        this.orderDAO = orderDAO;
        this.orderMapper = orderMapper;
        this.pageMapper = pageMapper;
    }

    @Override
    @Transactional
    public OrderDTO add(OrderDTO orderDTO) {
        if (orderDTO.getUser() == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDTO userDTO = (UserDTO) authentication.getPrincipal();
            orderDTO.setUser(userDTO);
        }
        Order addedOrder = orderDAO.add(orderMapper.convertToEntity(orderDTO));
        return orderMapper.convertToDTO(addedOrder);
    }

    @Override
    public OrderDTO get(int id) {
        return orderMapper.convertToDTO(orderDAO.get(id));
    }

    @Override
    public List<OrderDTO> get(PageDTO pageDTO) {
        return orderDAO.get(pageMapper.convertToEntity(pageDTO))
                .stream().map(orderMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getUserOrders(int id, PageDTO pageDTO) {
        return orderDAO.getUserOrders(id, pageMapper.convertToEntity(pageDTO))
                .stream().map(orderMapper::convertToDTO).collect(Collectors.toList());
    }

}
