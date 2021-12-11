package com.epam.esm.dao;

import com.epam.esm.bean.Tag;

import java.util.List;

public interface CertificateTagDAO {

    void addTagToCertificate(int certificate_id, int tag_id);

    List<Tag> getAllTagsOfCertificate(int certificate_id);

    Tag getTagOfCertificate(int certificate_id, int tag_id);

    void deleteTagsOfCertificate(int certificate_id);

    void deleteTagFromCertificates(int tag_id);
}
