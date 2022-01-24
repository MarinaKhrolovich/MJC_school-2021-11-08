package com.epam.esm.dao;

import com.epam.esm.bean.Order;
import com.epam.esm.bean.Page;

import java.util.List;

public interface OrderDAO {

    Order add(Order order);

    Order get(int id);

    List<Order> get(Page page);

    List<Order> getUserOrders(int id,Page page);

}
