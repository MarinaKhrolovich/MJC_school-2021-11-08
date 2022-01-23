package com.epam.esm.mapper;

import com.epam.esm.bean.Page;
import com.epam.esm.bean.Tag;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.TagDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class PageMapperTest {

    private final PageMapper pageMapper = Mappers.getMapper(PageMapper.class);

    @Test
    void convertToEntity() {
        PageDTO pageDTO = new PageDTO(10,0);

        Page actualPage = pageMapper.convertToEntity(pageDTO);

        assertEquals(pageDTO.getLimit(), actualPage.getLimit());
        assertEquals(pageDTO.getOffset(), actualPage.getOffset());
    }

    @Test
    void convertToDTO() {
        Page page = new Page(10,0);

        PageDTO actualPageDTO = pageMapper.convertToDTO(page);

        assertEquals(page.getLimit(), actualPageDTO.getLimit());
        assertEquals(page.getOffset(), actualPageDTO.getOffset());
    }

}