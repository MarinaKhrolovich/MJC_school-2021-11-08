package com.epam.esm.dao.mapper;

import com.epam.esm.bean.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setCreateDate(rs.getTimestamp("create_date").toInstant());
        order.setPrice(rs.getBigDecimal("cost"));
        return order;
    }
}
