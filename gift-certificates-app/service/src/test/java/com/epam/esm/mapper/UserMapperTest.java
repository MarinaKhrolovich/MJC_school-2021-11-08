package com.epam.esm.mapper;

import com.epam.esm.bean.User;
import com.epam.esm.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class UserMapperTest {

    public static final String NEW_USER = "new user";
    public static final int ID_USER = 1;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void convertToEntity() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID_USER);
        userDTO.setUsername(NEW_USER);

        User actualUser = userMapper.convertToEntity(userDTO);

        assertEquals(userDTO.getId(), actualUser.getId());
        assertEquals(userDTO.getUsername(), actualUser.getUsername());
    }

    @Test
    void convertToDTO() {
        User user = new User();
        user.setId(ID_USER);
        user.setName(NEW_USER);

        UserDTO actualUserDTO = userMapper.convertToDTO(user);

        assertEquals(user.getId(), actualUserDTO.getId());
        assertEquals(user.getUsername(), actualUserDTO.getUsername());
    }

}
