package com.epam.esm.dao.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TagDAOImpl implements TagDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Tag add(Tag tag) {
        Session currentSession = sessionFactory.getCurrentSession();
        try {
            currentSession.save(tag);
        } catch (ConstraintViolationException exception) {
            throw new ResourceAlreadyExistsException(tag.getName());
        }
        return tag;
    }

    @Override
    public Tag get(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Optional<Tag> tag = Optional.ofNullable(currentSession.get(Tag.class, id));
        return tag.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public Optional<Tag> get(String name) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query tagQuery = currentSession.createQuery("from Tag where name=:tagName");
        tagQuery.setParameter("tagName", name);
        return Optional.ofNullable((Tag) tagQuery.uniqueResult());
    }

    @Override
    public List<Tag> get() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Tag> tagQuery = currentSession.createQuery("from Tag", Tag.class);
        return tagQuery.getResultList();
    }

    @Override
    public void delete(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query theQuery = currentSession.createQuery("delete from Tag where id=:tagId");
        theQuery.setParameter("tagId", id);
        theQuery.executeUpdate();
    }

    private boolean isExists(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Optional<Tag> tag = Optional.ofNullable(currentSession.get(Tag.class, id));
        return tag.isPresent();
    }
}
