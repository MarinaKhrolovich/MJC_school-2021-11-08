package com.epam.esm.dao.impl;

import com.epam.esm.bean.User;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    private static final String SELECT_FROM_USER = "SELECT u FROM User u";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User add(User user) {
        try {
            entityManager.persist(user);
        } catch (PersistenceException exception) {
            throw new ResourceAlreadyExistsException(user.getLogin());
        }
        return user;
    }

    @Override
    public User get(int id) {
        Optional<User> user = Optional.ofNullable(entityManager.find(User.class, id));
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<User> get() {
        return entityManager.createQuery(SELECT_FROM_USER, User.class).getResultList();
    }

}
