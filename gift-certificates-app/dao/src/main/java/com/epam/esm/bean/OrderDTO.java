package com.epam.esm.bean;

public class OrderDTO {

    private final String orderByDate;
    private final String orderByName;

    public OrderDTO(String orderByDate, String orderByName) {
        this.orderByDate = orderByDate;
        this.orderByName = orderByName;
    }

    public String getOrderByDate() {
        return orderByDate;
    }

    public String getOrderByName() {
        return orderByName;
    }

}
