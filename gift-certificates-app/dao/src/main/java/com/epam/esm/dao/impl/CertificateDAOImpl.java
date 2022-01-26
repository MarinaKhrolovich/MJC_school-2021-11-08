package com.epam.esm.dao.impl;

import com.epam.esm.bean.*;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceHasLinksException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    private static final String ASC = "ASC";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String TAG_LIST = "tagList";
    private static final String ID = "id";

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
    public List<Certificate> get(Page page, Sort sort, Search search) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);
        criteriaQuery.select(root);

        Optional<Predicate> predicateAnd = createSearchBuilder(search, criteriaBuilder, root);
        if (predicateAnd.isPresent()) {
            criteriaQuery.where(predicateAnd.get());
        }

        Order order = createSortBuilder(sort, criteriaBuilder, root);
        criteriaQuery.orderBy(order);

        TypedQuery<Certificate> query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page.getOffset()).setMaxResults(page.getLimit());
        return query.getResultList();
    }

    private Order createSortBuilder(Sort sort, CriteriaBuilder criteriaBuilder, Root<Certificate> root) {
        Optional<String> orderBy = Optional.ofNullable(sort.getOrderBy());
        Optional<String> sortBy = Optional.ofNullable(sort.getSortBy());
        Order order = criteriaBuilder.desc(root.get(ID));
        if (orderBy.isPresent() && sortBy.isPresent()) {
            if (orderBy.get().equalsIgnoreCase(ASC)) {
                order = criteriaBuilder.asc(root.get(sortBy.get()));
            } else {
                order = criteriaBuilder.desc(root.get(sortBy.get()));
            }
        } else if (sortBy.isPresent()) {
            order = criteriaBuilder.desc(root.get(sortBy.get()));
        } else if (orderBy.isPresent()) {
            if (orderBy.get().equalsIgnoreCase(ASC)) {
                order = criteriaBuilder.asc(root.get(ID));
            }
        }
        return order;
    }

    private Optional<Predicate> createSearchBuilder(Search search, CriteriaBuilder criteriaBuilder,
                                                    Root<Certificate> root) {
        List<String> tagName = search.getTagName();
        Optional<String> name = Optional.ofNullable(search.getName());
        Optional<String> description = Optional.ofNullable(search.getDescription());

        List<Predicate> predicates = new ArrayList<>();
        if (!CollectionUtils.isEmpty(tagName)) {
            for (String tag : tagName) {
                Predicate equalTagName = criteriaBuilder.equal(root.join(TAG_LIST).get(NAME), tag);
                predicates.add(equalTagName);
            }
        }
        if (name.isPresent()) {
            Predicate likeName = criteriaBuilder.like(root.get(NAME), '%' + name.get() + '%');
            predicates.add(likeName);
        }
        if (description.isPresent()) {
            Predicate likeDescription = criteriaBuilder.like(root.get(DESCRIPTION), '%' + description.get() + '%');
            predicates.add(likeDescription);
        }

        Predicate predicateAnd = null;
        if (!predicates.isEmpty()) {
            predicateAnd = predicates.get(0);
            for (int i = 1; i < predicates.size(); i++) {
                predicateAnd = criteriaBuilder.and(predicateAnd, predicates.get(i));
            }
        }
        return Optional.ofNullable(predicateAnd);
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
            throw new ResourceHasLinksException(id);
        }
    }

    private void addTagIfNotExists(Set<Tag> tags) {
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
