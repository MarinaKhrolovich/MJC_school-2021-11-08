package com.epam.esm.service.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.TagMapperImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    TagDAO tagDAO;
    @Mock
    private TagMapperImpl tagMapper;

    public static final String NEW_TAG = "new tag";
    public static final String SECOND_TAG = "second tag";
    public static final int ID_EXISTS = 1;
    public static final int ID_NOT_EXISTS = 100;
    public static final int ID_DELETE = 2;

    private static List<Tag> tagList;
    private static Tag tagExpected;
    private static Tag secondTag;

    private static List<TagDTO> tagListDTO;
    private static TagDTO tagExpectedDTO;
    private static TagDTO secondTagDTO;

    @BeforeAll
    static void beforeAll() {
        tagExpected = new Tag();
        tagExpected.setName(NEW_TAG);

        secondTag = new Tag();
        secondTag.setName(SECOND_TAG);

        tagList = new ArrayList<>();
        tagList.add(tagExpected);
        tagList.add(secondTag);

        tagExpectedDTO = new TagDTO();
        tagExpectedDTO.setName(NEW_TAG);

        secondTagDTO = new TagDTO();
        secondTagDTO.setName(SECOND_TAG);

        tagListDTO = new ArrayList<>();
        tagListDTO.add(tagExpectedDTO);
        tagListDTO.add(secondTagDTO);
    }

    @Test
    public void add() {
        doNothing().when(tagDAO).add(tagExpected);
        when(tagMapper.сonvertToEntity(tagExpectedDTO)).thenReturn(tagExpected);

        tagService.add(tagExpectedDTO);

        verify(tagDAO).add(tagExpected);
        verify(tagMapper).сonvertToEntity(tagExpectedDTO);
        verifyNoMoreInteractions(tagDAO, tagMapper);
    }

    @Test
    public void addExists() {
        doThrow(new ResourceAlreadyExistsException()).when(tagDAO).add(tagExpected);
        when(tagMapper.сonvertToEntity(tagExpectedDTO)).thenReturn(tagExpected);

        assertThrows(ResourceAlreadyExistsException.class, () -> tagService.add(tagExpectedDTO));

        verify(tagDAO).add(tagExpected);
        verify(tagMapper).сonvertToEntity(tagExpectedDTO);
        verifyNoMoreInteractions(tagDAO, tagMapper);
    }

    @Test
    public void getShouldBeNotNull() {
        when(tagDAO.get(ID_EXISTS)).thenReturn(tagExpected);
        when(tagMapper.сonvertToDTO(tagExpected)).thenReturn(tagExpectedDTO);

        assertNotNull(tagService.get(ID_EXISTS));

        verify(tagDAO).get(ID_EXISTS);
        verify(tagMapper).сonvertToDTO(tagExpected);
        verifyNoMoreInteractions(tagDAO, tagMapper);
    }

    @Test
    public void getShouldException() {
        when(tagDAO.get(ID_NOT_EXISTS)).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> tagService.get(ID_NOT_EXISTS));
        verify(tagDAO).get(ID_NOT_EXISTS);
    }

    @Test
    public void get() {
        when(tagDAO.get()).thenReturn(tagList);
        when(tagMapper.сonvertToDTO(tagExpected)).thenReturn(tagExpectedDTO);
        when(tagMapper.сonvertToDTO(secondTag)).thenReturn(secondTagDTO);

        assertEquals(tagListDTO, tagService.get());

        verify(tagDAO).get();
        verify(tagMapper,times(2)).сonvertToDTO(any(Tag.class));
        verifyNoMoreInteractions(tagDAO,tagMapper);
    }

    @Test
    public void delete() {
        doNothing().when(tagDAO).delete(ID_DELETE);
        tagService.delete(ID_DELETE);
        verify(tagDAO).delete(ID_DELETE);
    }
}
