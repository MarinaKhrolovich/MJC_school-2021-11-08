package com.epam.esm.service.impl;

import com.epam.esm.bean.Page;
import com.epam.esm.bean.User;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.PageMapperImpl;
import com.epam.esm.mapper.UserMapperImpl;
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
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    UserDAO userDAO;
    @Mock
    private UserMapperImpl userMapper;
    @Mock
    private PageMapperImpl pageMapper;

    public static final String NEW_USER = "new user";
    public static final String SECOND_USER = "second user";
    public static final int ID_EXISTS = 1;
    public static final int ID_NOT_EXISTS = 100;

    private static List<User> userList;
    private static User userExpected;
    private static User secondUser;

    private static List<UserDTO> userListDTO;
    private static UserDTO userExpectedDTO;
    private static UserDTO secondUserDTO;

    @BeforeAll
    static void beforeAll() {
        userExpected = new User();
        userExpected.setName(NEW_USER);

        secondUser = new User();
        secondUser.setName(SECOND_USER);

        userList = new ArrayList<>();
        userList.add(userExpected);
        userList.add(secondUser);

        userExpectedDTO = new UserDTO();
        userExpectedDTO.setName(NEW_USER);

        secondUserDTO = new UserDTO();
        secondUserDTO.setName(SECOND_USER);

        userListDTO = new ArrayList<>();
        userListDTO.add(userExpectedDTO);
        userListDTO.add(secondUserDTO);
    }

    @Test
    public void add() {
        when(userDAO.add(userExpected)).thenReturn(userExpected);
        when(userMapper.convertToEntity(userExpectedDTO)).thenReturn(userExpected);
        when(userMapper.convertToDTO(userExpected)).thenReturn(userExpectedDTO);
        userService.add(userExpectedDTO);

        verify(userDAO).add(userExpected);
        verify(userMapper).convertToEntity(userExpectedDTO);
        verify(userMapper).convertToDTO(userExpected);
        verifyNoMoreInteractions(userDAO, userMapper);
    }

    @Test
    public void addExists() {
        doThrow(new ResourceAlreadyExistsException()).when(userDAO).add(userExpected);
        when(userMapper.convertToEntity(userExpectedDTO)).thenReturn(userExpected);

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.add(userExpectedDTO));

        verify(userDAO).add(userExpected);
        verify(userMapper).convertToEntity(userExpectedDTO);
        verifyNoMoreInteractions(userDAO, userMapper);
    }

    @Test
    public void getShouldBeNotNull() {
        when(userDAO.get(ID_EXISTS)).thenReturn(userExpected);
        when(userMapper.convertToDTO(userExpected)).thenReturn(userExpectedDTO);

        assertNotNull(userService.get(ID_EXISTS));

        verify(userDAO).get(ID_EXISTS);
        verify(userMapper).convertToDTO(userExpected);
        verifyNoMoreInteractions(userDAO, userMapper);
    }

    @Test
    public void getShouldException() {
        when(userDAO.get(ID_NOT_EXISTS)).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> userService.get(ID_NOT_EXISTS));
        verify(userDAO).get(ID_NOT_EXISTS);
    }

    @Test
    public void get() {
        PageDTO pageDTO = new PageDTO(10, 0);
        Page page = new Page(10, 0);

        when(userDAO.get(page)).thenReturn(userList);
        when(pageMapper.convertToEntity(pageDTO)).thenReturn(page);
        when(userMapper.convertToDTO(userExpected)).thenReturn(userExpectedDTO);
        when(userMapper.convertToDTO(secondUser)).thenReturn(secondUserDTO);

        assertEquals(userListDTO, userService.get(pageDTO));

        verify(userDAO).get(page);
        verify(pageMapper).convertToEntity(pageDTO);
        verify(userMapper, times(2)).convertToDTO(any(User.class));
        verifyNoMoreInteractions(userDAO, userMapper, pageMapper);
    }

}
