package com.epam.esm.dao.impl;

import com.epam.esm.bean.Order;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.mapper.OrderMapper;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private static final String SELECT_FROM_ORDER = "SELECT * FROM order";
    private static final String SELECT_FROM_ORDER_WHERE_ID = "SELECT * FROM order WHERE id = ?";
    private static final String CREATE_ORDER = "INSERT INTO order(user_id, certificate_id, create_date, cost)" +
            " VALUES(?,?,?,?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order add(Order order) {
        //TODO get cost of certificate
        order.setCreateDate(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(CREATE_ORDER, new String[]{"id"});
                    ps.setInt(1, order.getUserId());
                    ps.setInt(2, order.getCertificateId());
                    ps.setTimestamp(3, Timestamp.from(order.getCreateDate()));
                    ps.setBigDecimal(4, order.getCost());
                    return ps;
                },
                keyHolder);
        if (keyHolder.getKey() != null) {
            order.setId(keyHolder.getKey().intValue());
        }
        return order;
    }

    @Override
    public Order get(int id) {
        return jdbcTemplate.query(SELECT_FROM_ORDER_WHERE_ID, new OrderMapper(), id).stream().findAny().
                orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<Order> get() {
        return jdbcTemplate.query(SELECT_FROM_ORDER, new OrderMapper());
    }

}
