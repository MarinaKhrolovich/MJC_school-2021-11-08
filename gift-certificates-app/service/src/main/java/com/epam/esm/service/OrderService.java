package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageDTO;

import java.util.List;

public interface OrderService {

    /**
     * Add Order to the database according to provided object User
     *
     * @param order is object {@link OrderDTO}
     */
    OrderDTO add(OrderDTO order);

    /**
     * Get Order from the database according to provided id
     *
     * @param id is id of Order {@link OrderDTO} to be getting
     * @return OrderDTO is object {@link OrderDTO}
     */
    OrderDTO get(int id);

    /**
     * Get all Orders from the database
     *
     * @param pageDTO is object {@link PageDTO} for pagination
     * @return list of orders {@link OrderDTO}
     */
    List<OrderDTO> get(PageDTO pageDTO);

    /**
     * Get Order of user from according to provided id
     *
     * @param id is id of Order {@link OrderDTO} to be getting
     * @return list of object {@link OrderDTO}
     */
    List<OrderDTO> getUserOrders(int id);

}
