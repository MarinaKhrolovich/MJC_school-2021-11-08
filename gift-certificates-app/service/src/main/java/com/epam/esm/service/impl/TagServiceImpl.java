package com.epam.esm.service.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.mapper.PageMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;
    private final TagMapper tagMapper;
    private final PageMapper pageMapper;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, TagMapper tagMapper, PageMapper pageMapper) {
        this.tagDAO = tagDAO;
        this.tagMapper = tagMapper;
        this.pageMapper = pageMapper;
    }

    @Override
    @Transactional
    public TagDTO add(TagDTO tagDTO) {
        Tag addedTag = tagDAO.add(tagMapper.convertToEntity(tagDTO));
        return tagMapper.convertToDTO(addedTag);
    }

    @Override
    public TagDTO get(int id) {
        return tagMapper.convertToDTO(tagDAO.get(id));
    }

    @Override
    public List<TagDTO> get(PageDTO pageDTO) {
        return tagDAO.get(pageMapper.convertToEntity(pageDTO))
                .stream().map(tagMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(int id) {
        tagDAO.delete(id);
    }

    @Override
    public TagDTO getMostPopular() {
        return tagMapper.convertToDTO(tagDAO.getMostPopular());
    }

}
