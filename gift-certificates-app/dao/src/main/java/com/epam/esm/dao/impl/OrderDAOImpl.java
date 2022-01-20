package com.epam.esm.dao.impl;

import com.epam.esm.bean.Order;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderDAOImpl implements OrderDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order add(Order order) {
        order.setPrice(BigDecimal.valueOf(100));
        entityManager.persist(order);
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
