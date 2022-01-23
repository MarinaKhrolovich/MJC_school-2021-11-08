package com.epam.esm.mapper;

import com.epam.esm.bean.Search;
import com.epam.esm.bean.Sort;
import com.epam.esm.dto.SearchDTO;
import com.epam.esm.dto.SortDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SortSearchMapper {

    Sort convertToEntity(SortDTO sortDTO);

    Search convertToEntity(SearchDTO searchDTO);

}
