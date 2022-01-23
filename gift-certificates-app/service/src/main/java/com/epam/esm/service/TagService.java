package com.epam.esm.service;

import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.TagDTO;

import java.util.List;

/**
 * Service interface responsible for CRD operation with object Tag
 * @author Marina Khrolovich
 */
public interface TagService {

    /**
     * Add Tag to the database according to provided object Tag
     * @param tag is object {@link TagDTO}
     */
    TagDTO add(TagDTO tag);

    /**
     * Get Tag from the database according to provided id
     * @param id is id of Tag {@link TagDTO} to be getting
     * @return tag is object {@link TagDTO}
     */
    TagDTO get(int id);

    /**
     * Get all Tags from the database
     * @return list of tags {@link TagDTO}
     */
    List<TagDTO> get(PageDTO pageDTO);

    /**
     * Delete Tag from the database according to provided id
     * @param id is id of Tag to be deleting
     */
    void delete(int id);

    /**
     * Get most popular Tag from the database
     * @return tag is object {@link TagDTO}
     */
    TagDTO getMostPopular();

}
