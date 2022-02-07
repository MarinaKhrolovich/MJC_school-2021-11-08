package com.epam.esm.service;

import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.UserDTO;

import java.util.List;

public interface UserService {

    /**
     * Add User to the database according to provided object User
     *
     * @param user is object {@link UserDTO}
     */
    UserDTO add(UserDTO user);


    /**
     * Get User from the database according to provided id
     *
     * @param id is id of User {@link UserDTO} to be getting
     * @return userDTO is object {@link UserDTO}
     */
    UserDTO get(int id);

    /**
     * Get all Users from the database
     *
     * @param pageDTO is object {@link PageDTO} for pagination
     * @return list of users {@link UserDTO}
     */
    List<UserDTO> get(PageDTO pageDTO);

}
