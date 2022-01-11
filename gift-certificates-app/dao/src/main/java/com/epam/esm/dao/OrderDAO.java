package com.epam.esm.dao;

import com.epam.esm.bean.Order;

import java.util.List;

public interface OrderDAO {

    Order add(Order order);

    Order get(int id);

    List<Order> get();

}
