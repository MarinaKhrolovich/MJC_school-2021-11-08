package com.epam.esm.mapper;

import com.epam.esm.bean.User;
import com.epam.esm.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO convertToDTO(User user);

    User convertToEntity(UserDTO userDTO);

}
