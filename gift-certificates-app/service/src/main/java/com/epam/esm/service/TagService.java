package com.epam.esm.service;

import com.epam.esm.bean.Tag;
import com.epam.esm.dto.TagDTO;

import java.util.List;

/**
 * Service interface responsible for CRD operation with object Tag
 * @author Marina Khrolovich
 */
public interface TagService {

    /**
     * Add Tag to the database according to provided object Tag
     * @param tag is object {@link Tag}
     */
    void add(TagDTO tag);

    /**
     * Get Tag from the database according to provided id
     * @param id is id of Tag {@link Tag} to be getting
     * @return tag is object {@link Tag}
     */
    TagDTO get(int id);

    /**
     * Get all Tags from the database
     * @return list of tags {@link Tag}
     */
    List<TagDTO> get();

    /**
     * Delete Tag from the database according to provided id
     * @param id is id of Tag to be deleting
     */
    void delete(int id);
}
