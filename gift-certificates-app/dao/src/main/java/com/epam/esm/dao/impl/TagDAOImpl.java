package com.epam.esm.dao.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TagDAOImpl implements TagDAO {

    @PersistenceContext
    private EntityManager entityManager;

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
        TypedQuery<Tag> query = entityManager.createQuery("SELECT t FROM Tag t WHERE t.name =:nameParam", Tag.class);
        query.setParameter("nameParam", name);
        return query.getResultStream().findAny();
    }

    @Override
    public List<Tag> get() {
        return entityManager.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
    }

    @Override
    public void delete(int id) {
        Tag tag = Optional.ofNullable(entityManager.find(Tag.class, id))
                .stream().findAny().orElseThrow(()-> new ResourceNotFoundException(id));
        entityManager.remove(tag);
    }

    @Override
    public Tag getMostPopular() {
        Query nativeQuery = entityManager.createNativeQuery("");
        return new Tag();
    }

}
