package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Search;
import com.epam.esm.bean.Sort;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.util.CertificateGetSQLRequest;
import com.epam.esm.dao.util.CertificateUpdateSQLRequest;
import com.epam.esm.exception.ResourceNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CertificateDAOImpl implements CertificateDAO {

    private final CertificateGetSQLRequest getSQLRequest;
    private final CertificateUpdateSQLRequest updateSQLRequest;

    private final SessionFactory sessionFactory;

    @Autowired
    public CertificateDAOImpl(CertificateGetSQLRequest getSQLRequest, CertificateUpdateSQLRequest updateSQLRequest, SessionFactory sessionFactory) {
        this.getSQLRequest = getSQLRequest;
        this.updateSQLRequest = updateSQLRequest;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Certificate add(Certificate certificate) {
        certificate.setCreateDate(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        certificate.setLastUpdateDate(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(certificate);
        return certificate;
    }

    @Override
    public Certificate get(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Optional<Certificate> certificate = Optional.ofNullable(currentSession.get(Certificate.class, id));
        return certificate.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<Certificate> get(Sort sort, Search search) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Certificate> certificateQuery = currentSession.createQuery("from Certificate", Certificate.class);
        return certificateQuery.getResultList();
    }

    @Override
    public Certificate update(int id, Certificate certificate) {
        if (notExists(id)) {
            throw new ResourceNotFoundException(id);
        }
        certificate.setId(id);
        Session currentSession = sessionFactory.getCurrentSession();
        Certificate mergedCertificate = (Certificate)currentSession.merge(certificate);
        return mergedCertificate;
    }

    @Override
    public void delete(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<?> query = currentSession.createQuery("delete from Certificate where id=:certificateId");
        query.setParameter("certificateId", id);
        query.executeUpdate();
    }

    private boolean notExists(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Optional<Certificate> certificate = Optional.ofNullable(currentSession.get(Certificate.class, id));
        return certificate.isEmpty();
    }

}
