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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        userExpected.setUsername(NEW_USER);
        userExpected.setPassword(NEW_USER);

        secondUser = new User();
        secondUser.setUsername(SECOND_USER);
        secondUser.setPassword(SECOND_USER);

        userList = new ArrayList<>();
        userList.add(userExpected);
        userList.add(secondUser);

        userExpectedDTO = new UserDTO();
        userExpectedDTO.setUsername(NEW_USER);
        userExpectedDTO.setPassword(NEW_USER);

        secondUserDTO = new UserDTO();
        secondUserDTO.setUsername(SECOND_USER);
        secondUserDTO.setPassword(SECOND_USER);

        userListDTO = new ArrayList<>();
        userListDTO.add(userExpectedDTO);
        userListDTO.add(secondUserDTO);
    }

    @Test
    public void add() {
        when(userDAO.add(userExpected)).thenReturn(userExpected);
        when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn(NEW_USER);
        when(userMapper.convertToEntity(userExpectedDTO)).thenReturn(userExpected);
        when(userMapper.convertToDTO(userExpected)).thenReturn(userExpectedDTO);

        userService.add(userExpectedDTO);

        verify(userDAO).add(userExpected);
        verify(bCryptPasswordEncoder).encode(any(String.class));
        verify(userMapper).convertToEntity(userExpectedDTO);
        verify(userMapper).convertToDTO(userExpected);
        verifyNoMoreInteractions(userDAO, bCryptPasswordEncoder, userMapper);
    }

    @Test
    public void addExists() {
        doThrow(new ResourceAlreadyExistsException()).when(userDAO).add(userExpected);
        when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn(NEW_USER);
        when(userMapper.convertToEntity(userExpectedDTO)).thenReturn(userExpected);

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.add(userExpectedDTO));

        verify(userDAO).add(userExpected);
        verify(bCryptPasswordEncoder).encode(any(String.class));
        verify(userMapper).convertToEntity(userExpectedDTO);
        verifyNoMoreInteractions(userDAO, bCryptPasswordEncoder, userMapper);
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

    @Test
    void loadUserByUsername() {
        when(userDAO.get(NEW_USER)).thenReturn(Optional.ofNullable(userExpected));
        when(userMapper.convertToDTO(userExpected)).thenReturn(userExpectedDTO);

        assertNotNull(userService.loadUserByUsername(NEW_USER));

        verify(userDAO).get(NEW_USER);
        verify(userMapper).convertToDTO(any(User.class));
        verifyNoMoreInteractions(userDAO, userMapper);
    }

    @Test
    void loadUserByUsernameException() {
        when(userDAO.get(NEW_USER)).thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(NEW_USER));
        verify(userDAO).get(NEW_USER);
    }

}
