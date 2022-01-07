package com.epam.esm.mapper;

import com.epam.esm.bean.Tag;
import com.epam.esm.dto.TagDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag сonvertToEntity(TagDTO tagDTO);

    TagDTO сonvertToDTO(Tag tag);

}
