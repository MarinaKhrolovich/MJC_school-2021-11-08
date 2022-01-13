package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Order;
import com.epam.esm.bean.Tag;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.mapper.OrderMapper;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    private static final String SELECT_FROM_ORDER = "SELECT * FROM orders";
    private static final String SELECT_FROM_ORDER_WHERE_ID = "SELECT * FROM orders WHERE id = ?";
    private static final String CREATE_ORDER = "INSERT INTO orders(user_id, create_date) VALUES(?,?)";

    private static final String CREATE_ORDER_CERTIFICATE = "INSERT INTO order_certificate" +
            "(order_id, certificate_id, price) VALUES(?,?,1)";
    private static final String SELECT_CERTIFICATES_OF_ORDER = "SELECT order_certificate.certificate_id AS id FROM orders " +
            "JOIN order_certificate ON order_certificate.order_id  = orders.id WHERE orders.id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order add(Order order) {
        order.setCreateDate(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(CREATE_ORDER, new String[]{"id"});
                    ps.setInt(1, order.getUser().getId());
                    ps.setTimestamp(2, Timestamp.from(order.getCreateDate()));
                    return ps;
                },
                keyHolder);
        if (keyHolder.getKey() != null) {
            order.setId(keyHolder.getKey().intValue());
        }
        addCertificatesToOrder(order);
        return order;
    }

    @Override
    public Order get(int id) {
        Order order = jdbcTemplate.query(SELECT_FROM_ORDER_WHERE_ID, new OrderMapper(), id).stream().findAny().
                orElseThrow(() -> new ResourceNotFoundException(id));
        setCertificateList(id, order);
        return order;
    }

    @Override
    public List<Order> get() {
        return jdbcTemplate.query(SELECT_FROM_ORDER, new OrderMapper());
    }

    private void addCertificatesToOrder(Order order) {
        List<Certificate> certificates = order.getCertificates();
        if (certificates != null) {
            for (Certificate certificate : certificates) {
                jdbcTemplate.update(CREATE_ORDER_CERTIFICATE, order.getId(), certificate.getId());
            }
        }
    }

    private void setCertificateList(int id, Order order) {
        List<Certificate> certificateList = jdbcTemplate.query(SELECT_CERTIFICATES_OF_ORDER,
                new BeanPropertyRowMapper<>(Certificate.class), id);
        order.setCertificates(certificateList);
    }

}
