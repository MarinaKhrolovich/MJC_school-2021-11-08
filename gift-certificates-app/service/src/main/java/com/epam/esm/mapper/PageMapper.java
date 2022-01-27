package com.epam.esm.mapper;

import com.epam.esm.bean.Page;
import com.epam.esm.dto.PageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PageMapper {

    PageDTO convertToDTO(Page page);

    Page convertToEntity(PageDTO orderDTO);

}
