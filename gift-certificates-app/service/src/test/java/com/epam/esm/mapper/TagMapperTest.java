package com.epam.esm.mapper;

import com.epam.esm.bean.Tag;
import com.epam.esm.config.ServiceConfig;
import com.epam.esm.dto.TagDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfig.class})
class TagMapperTest {

    public static final String NEW_TAG = "new tag";
    public static final int ID_TAG = 1;

    private final TagMapper tagMapper;

    @Autowired
    TagMapperTest(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Test
    void convertToEntity() {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(ID_TAG);
        tagDTO.setName(NEW_TAG);

        Tag actualTag = tagMapper.сonvertToEntity(tagDTO);

        assertEquals(tagDTO.getId(), actualTag.getId());
        assertEquals(tagDTO.getName(), actualTag.getName());
    }

    @Test
    void convertToDTO() {
        Tag tag = new Tag();
        tag.setId(ID_TAG);
        tag.setName(NEW_TAG);

        TagDTO actualTagDTO = tagMapper.сonvertToDTO(tag);

        assertEquals(tag.getId(), actualTagDTO.getId());
        assertEquals(tag.getName(), actualTagDTO.getName());
    }
}