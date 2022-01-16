package com.epam.esm.dao.impl;

import com.epam.esm.bean.User;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User add(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        try {
            Serializable save = currentSession.save(user);
        } catch (ConstraintViolationException exception) {
            throw new ResourceAlreadyExistsException(user.getLogin());
        }
        return user;
    }

    @Override
    public User get(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        User user = currentSession.get(User.class, id);
        if (user == null) {
            new ResourceNotFoundException(id);
        }
        return user;
    }

    @Override
    public List<User> get() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> userQuery = currentSession.createQuery("from User", User.class);
        return userQuery.getResultList();
    }

}
