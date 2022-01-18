package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Search;
import com.epam.esm.bean.Sort;
import com.epam.esm.dao.CertificateDAO;
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
public class CertificateDAOImpl implements CertificateDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Certificate add(Certificate certificate) {
        try {
            entityManager.persist(certificate);
        } catch (PersistenceException exception) {
            throw new ResourceAlreadyExistsException(certificate.getName());
        }
        return certificate;
    }

    @Override
    public Certificate get(int id) {
        Optional<Certificate> certificate = Optional.ofNullable(entityManager.find(Certificate.class, id));
        return certificate.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<Certificate> get(Sort sort, Search search) {
        return entityManager.createQuery("SELECT c FROM Certificate c", Certificate.class).getResultList();
    }

    @Override
    public Certificate update(int id, Certificate certificate) {
        if (notExists(id)) {
            throw new ResourceNotFoundException(id);
        }
        certificate.setId(id);
        entityManager.merge(certificate);
        return certificate;
    }

    @Override
    public void delete(int id) {
        Optional<Certificate> certificate = Optional.ofNullable(entityManager.find(Certificate.class, id));
        if (certificate.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        entityManager.remove(certificate.get());
    }

    private boolean notExists(int id) {
        Optional<Certificate> certificate = Optional.ofNullable(entityManager.find(Certificate.class, id));
        return certificate.isEmpty();
    }

}
