package com.epam.esm.dao.impl;

import com.epam.esm.bean.Page;
import com.epam.esm.bean.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TagDAOImpl implements TagDAO {

    private static final String SELECT_FROM_TAG_WHERE_NAME = "SELECT t FROM Tag t WHERE t.name =:nameParam";

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_MOST_POPULAR_TAG =
            "select tag.id, tag.name, o.user_id, Count(tag.name) AS count_tag from orders AS o " +
                    "join (select osub.user_id,SUM(osub.price) AS price from orders AS osub group by osub.user_id " +
                    "order by price DESC LIMIT 1) AS user_limit on user_limit.user_id = o.user_id " +
                    "join order_certificate AS oc on o.id = oc.order_id " +
                    "join certificate_tag AS ct on oc.certificate_id = ct.certificate_id " +
                    "join tag on ct.tag_id = tag.id " +
                    "group by o.user_id, tag.name, tag.id order by count_tag DESC LIMIT 1";

    @Override
    public Tag add(Tag tag) {
        try {
            entityManager.persist(tag);
        } catch (PersistenceException exception) {
            throw new ResourceAlreadyExistsException(tag.getName());
        }
        return tag;
    }

    @Override
    public Tag get(int id) {
        Optional<Tag> tag = Optional.ofNullable(entityManager.find(Tag.class, id));
        return tag.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public Optional<Tag> get(String name) {
        TypedQuery<Tag> query = entityManager.createQuery(SELECT_FROM_TAG_WHERE_NAME, Tag.class);
        query.setParameter("nameParam", name);
        return query.getResultStream().findAny();
    }

    @Override
    public List<Tag> get(Page page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));

        Query query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page.getOffset()).setMaxResults(page.getLimit());
        return query.getResultList();
    }

    @Override
    public void delete(int id) {
        Tag tag = Optional.ofNullable(entityManager.find(Tag.class, id))
                .stream().findAny().orElseThrow(() -> new ResourceNotFoundException(id));
        entityManager.remove(tag);
    }

    @Override
    public Tag getMostPopular() {
        Query nativeQuery = entityManager.createNativeQuery(SELECT_MOST_POPULAR_TAG, Tag.class);
        return (Tag) nativeQuery.getSingleResult();
    }

}
