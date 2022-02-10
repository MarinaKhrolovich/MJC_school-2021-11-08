package com.epam.esm.service.impl;

import com.epam.esm.bean.Role;
import com.epam.esm.bean.User;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.mapper.PageMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDAO userDAO;
    private final UserMapper userMapper;
    private final PageMapper pageMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserMapper userMapper, PageMapper pageMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
        this.pageMapper = pageMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public UserDTO add(UserDTO userDTO) {
        userDTO.setAuthorities(Collections.singleton(Role.USER));
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        User addedUser = userDAO.add(userMapper.convertToEntity(userDTO));
        return userMapper.convertToDTO(addedUser);
    }

    @Override
    public UserDTO get(int id) {
        return userMapper.convertToDTO(userDAO.get(id));
    }

    @Override
    public List<UserDTO> get(PageDTO pageDTO) {
        return userDAO.get(pageMapper.convertToEntity(pageDTO))
                .stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        return userDAO.get(username).
                orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
