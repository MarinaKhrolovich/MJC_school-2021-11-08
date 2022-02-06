package com.epam.esm.dao.impl;

import com.epam.esm.bean.Page;
import com.epam.esm.bean.User;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final String ID = "id";
    private static final String SELECT_FROM_USER_WHERE_USERNAME = "SELECT u FROM User u left join fetch u.authorities" +
            " WHERE u.username =:nameParam";
    private static final String NAME_PARAM = "nameParam";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User get(int id) {
        Optional<User> user = Optional.ofNullable(entityManager.find(User.class, id));
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public Optional<User> get(String username) {
        TypedQuery<User> query = entityManager.createQuery(SELECT_FROM_USER_WHERE_USERNAME, User.class);
        query.setParameter(NAME_PARAM, username);
        return query.getResultStream().findAny();
    }

    @Override
    public List<User> get(Page page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(ID)));

        TypedQuery<User> query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page.getOffset()).setMaxResults(page.getLimit());
        return query.getResultList();
    }

}
