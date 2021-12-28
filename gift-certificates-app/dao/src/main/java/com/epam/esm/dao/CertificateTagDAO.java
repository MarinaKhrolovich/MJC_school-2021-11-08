package com.epam.esm.dao;

import com.epam.esm.bean.Tag;

import java.util.List;
import java.util.Optional;

public interface CertificateTagDAO {

    void addTagToCertificate(int certificate_id, int tag_id);

    List<Tag> getAllTagsOfCertificate(int certificate_id);

    Optional<Tag> getTagOfCertificate(int certificate_id, int tag_id);

    void deleteTagsOfCertificate(int certificate_id);

}
