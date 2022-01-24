package com.epam.esm.dao.impl;

import com.epam.esm.bean.Order;
import com.epam.esm.bean.Page;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.exception.ResourceNoLinksException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private static final String SELECT_SUM_PRICE = "SELECT SUM(certificate.price) as price FROM orders " +
            "JOIN order_certificate ON orders.id = order_certificate.order_id " +
            "JOIN certificate ON order_certificate.certificate_id=certificate.id WHERE orders.id =?1";
    private static final String ID = "id";
    private static final String USER = "user";

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
            throw new ResourceNoLinksException(exception);
        }
        return order;
    }

    @Override
    public Order get(int id) {
        Optional<Order> order = Optional.ofNullable(entityManager.find(Order.class, id));
        return order.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<Order> get(Page page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(ID)));

        Query query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page.getOffset()).setMaxResults(page.getLimit());
        return query.getResultList();
    }

    @Override
    public List<Order> getUserOrders(int id, Page page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.join(USER).get(ID), id));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(ID)));

        Query query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page.getOffset()).setMaxResults(page.getLimit());

        return query.getResultList();
    }

}
