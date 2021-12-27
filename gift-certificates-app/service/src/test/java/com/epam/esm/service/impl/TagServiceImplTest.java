package com.epam.esm.service.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.validator.TagCheck;
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
    TagCheck tagCheck;

    public static final String NEW_TAG = "new tag";
    public static final String SECOND_TAG = "second tag";
    public static final int ID_EXISTS = 1;
    public static final int ID_NOT_EXISTS = 100;
    public static final int ID_DELETE = 2;

    private static List<Tag> tagList;
    private static Tag tagExpected;

    @BeforeAll
    static void beforeAll() {
        tagExpected = new Tag();
        tagExpected.setName(NEW_TAG);

        Tag secondTag = new Tag();
        secondTag.setName(SECOND_TAG);

        tagList = new ArrayList<>();
        tagList.add(tagExpected);
        tagList.add(secondTag);
    }

    @Test
    public void add() {
        doNothing().when(tagCheck).check(tagExpected);
        doNothing().when(tagDAO).add(tagExpected);

        tagService.add(tagExpected);

        verify(tagCheck).check(tagExpected);
        verify(tagDAO).add(tagExpected);
        verifyNoMoreInteractions(tagCheck,tagDAO);
    }

    @Test
    public void addExists() {
        doNothing().when(tagCheck).check(tagExpected);
        doThrow(new ResourceAlreadyExistsException()).when(tagDAO).add(tagExpected);
        assertThrows(ResourceAlreadyExistsException.class, () -> tagService.add(tagExpected));
        verify(tagDAO).add(tagExpected);
    }

    @Test
    public void getShouldBeNotNull() {
        when(tagDAO.get(ID_EXISTS)).thenReturn(tagExpected);
        assertNotNull(tagService.get(ID_EXISTS));
        verify(tagDAO).get(ID_EXISTS);
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
        assertEquals(tagList, tagService.get());
        verify(tagDAO).get();
    }

    @Test
    public void delete() {
        when(tagDAO.get(ID_DELETE)).thenReturn(tagExpected);
        doNothing().when(tagDAO).delete(ID_DELETE);

        tagService.delete(ID_DELETE);

        verify(tagDAO).get(ID_DELETE);
        verify(tagDAO).delete(ID_DELETE);
        verifyNoMoreInteractions(tagDAO);
    }
}
