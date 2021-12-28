package com.epam.esm.bean;

import java.util.Optional;

public class OrderDTO {

    private String orderByDate;
    private String orderByName;

    public OrderDTO(String orderByDate, String orderByName) {
        this.orderByDate = orderByDate;
        this.orderByName = orderByName;
    }

    public Optional<String> getOrderByDate() {
        return Optional.ofNullable(orderByDate);
    }

    public Optional<String> getOrderByName() {
        return Optional.ofNullable(orderByName);
    }

}
