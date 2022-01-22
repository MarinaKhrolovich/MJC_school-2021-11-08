package com.epam.esm.mapper;

import com.epam.esm.bean.User;
import com.epam.esm.config.ServiceConfig;
import com.epam.esm.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfig.class})
public class UserMapperTest {

    public static final String NEW_USER = "new user";
    public static final int ID_USER = 1;

    private final UserMapper userMapper;

    @Autowired
    UserMapperTest(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    void convertToEntity() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID_USER);
        userDTO.setLogin(NEW_USER);

        User actualUser = userMapper.convertToEntity(userDTO);

        assertEquals(userDTO.getId(), actualUser.getId());
        assertEquals(userDTO.getLogin(), actualUser.getLogin());
    }

    @Test
    void convertToDTO() {
        User user = new User();
        user.setId(ID_USER);
        user.setName(NEW_USER);

        UserDTO actualUserDTO = userMapper.convertToDTO(user);

        assertEquals(user.getId(), actualUserDTO.getId());
        assertEquals(user.getLogin(), actualUserDTO.getLogin());
    }

}
