package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Search;
import com.epam.esm.bean.Sort;
import com.epam.esm.bean.Tag;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceHasLinks;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public class CertificateDAOImpl implements CertificateDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private final TagDAO tagDAO;

    @Autowired
    public CertificateDAOImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public Certificate add(Certificate certificate) {
        try {
            addTagIfNotExists(certificate.getTagList());
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
        certificate.setId(id);
        addTagIfNotExists(certificate.getTagList());

        Certificate certificateFromBase = get(id);
        BeanUtils.copyProperties(certificateFromBase, certificate, getNotNullPropertyNames(certificate));

        entityManager.merge(certificate);
        return certificate;
    }

    @Override
    public void delete(int id) {
        Certificate certificate = Optional.ofNullable(entityManager.find(Certificate.class, id))
                .stream().findAny().orElseThrow(() -> new ResourceNotFoundException(id));
        try {
            entityManager.remove(certificate);
            entityManager.flush();
        } catch (PersistenceException exception) {
            throw new ResourceHasLinks(id);
        }
    }

    private void addTagIfNotExists(List<Tag> tags) {
        if (!CollectionUtils.isEmpty(tags)) {
            for (Tag tag : tags) {
                Optional<Tag> optionalTag = tagDAO.get(tag.getName());
                if (optionalTag.isEmpty()) {
                    tagDAO.add(tag);
                } else {
                    tag.setId(optionalTag.get().getId());
                }
            }
        }
    }

    private static String[] getNotNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
