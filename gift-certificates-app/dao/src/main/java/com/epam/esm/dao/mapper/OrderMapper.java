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
        order.setUserId(rs.getInt("user_id"));
        order.setCertificateId(rs.getInt("certificate_id"));
        order.setCost(rs.getBigDecimal("cost"));
        order.setCreateDate(rs.getTimestamp("create_date").toInstant());
        return order;
    }
}
