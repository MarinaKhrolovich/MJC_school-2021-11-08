package com.epam.esm.mapper;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Tag;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDTO;
import com.epam.esm.dto.TagDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CertificateMapper {

    Certificate convertToEntity(CertificateDTO certificateDTO);

    Certificate convertToEntity(CertificateUpdateDTO certificateUpdateDTO);

    Tag convertToEntity(TagDTO tagDTO);

    CertificateDTO convertToDTO(Certificate certificate);

    CertificateUpdateDTO convertToUpdateDTO(Certificate certificate);

}
