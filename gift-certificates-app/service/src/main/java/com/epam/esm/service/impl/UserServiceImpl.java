package com.epam.esm.service.impl;

import com.epam.esm.bean.User;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserMapper userMapper) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserDTO add(UserDTO userDTO) {
        User addedUser = userDAO.add(userMapper.convertToEntity(userDTO));
        return userMapper.convertToDTO(addedUser);
    }

    @Override
    public UserDTO get(int id) {
        return userMapper.convertToDTO(userDAO.get(id));
    }

    @Override
    public List<UserDTO> get() {
        return userDAO.get().stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

}
