package com.epam.esm.dao.impl;

import com.epam.esm.bean.Order;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.exception.ResourceNoLinks;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderDAOImpl implements OrderDAO {

    public static final String SELECT_SUM_PRICE = "SELECT SUM(certificate.price) as price FROM orders " +
            "JOIN order_certificate ON orders.id = order_certificate.order_id " +
            "JOIN certificate ON order_certificate.certificate_id=certificate.id WHERE orders.id =?1";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order add(Order order) {
        try {
            entityManager.persist(order);
            entityManager.flush();

            Query nativeQuery = entityManager.createNativeQuery(SELECT_SUM_PRICE);
            nativeQuery.setParameter(1, order.getId());
            order.setPrice((BigDecimal) nativeQuery.getSingleResult());
        } catch (PersistenceException exception) {
            throw new ResourceNoLinks(exception);
        }
        return order;
    }

    @Override
    public Order get(int id) {
        Optional<Order> order = Optional.ofNullable(entityManager.find(Order.class, id));
        return order.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<Order> get() {
        return entityManager.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }

}
