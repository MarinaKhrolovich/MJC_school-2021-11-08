package com.epam.esm.service.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
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

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, TagMapper tagMapper) {
        this.tagDAO = tagDAO;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional
    public TagDTO add(TagDTO tagDTO) {
        Tag addedTag = tagDAO.add(tagMapper.сonvertToEntity(tagDTO));
        return tagMapper.сonvertToDTO(addedTag);
    }

    @Override
    public TagDTO get(int id) {
        return tagMapper.сonvertToDTO(tagDAO.get(id));
    }

    @Override
    public List<TagDTO> get() {
        return tagDAO.get().stream().map(tagMapper::сonvertToDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        tagDAO.delete(id);
    }

    @Override
    public TagDTO getMostPopular() {
        return tagMapper.сonvertToDTO(tagDAO.getMostPopular());
    }

}
