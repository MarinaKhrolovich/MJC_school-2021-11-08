package com.epam.esm.dao.impl;

import com.epam.esm.bean.Order;
import com.epam.esm.bean.Page;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.exception.ResourceNoLinksException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private static final String SELECT_PRICE_OF_CERTIFICATES = "SELECT SUM(c.price) FROM Certificate c " +
            "WHERE c.id IN(:idParam)";
    private static final String ID = "id";
    private static final String USER = "user";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order add(Order order) {
        try {
            TypedQuery<BigDecimal> query = entityManager.createQuery(SELECT_PRICE_OF_CERTIFICATES, BigDecimal.class);
            Object[] idList = order.getCertificates().stream().map(cert -> cert.getId()).toArray();
            query.setParameter("idParam", Arrays.asList(idList));
            order.setPrice(query.getSingleResult());

            entityManager.persist(order);
            entityManager.flush();
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
