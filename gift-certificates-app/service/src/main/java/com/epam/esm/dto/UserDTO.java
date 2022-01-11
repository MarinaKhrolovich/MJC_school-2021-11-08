package com.epam.esm.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    private int id;

    @NotBlank(message = "{message.user.login.fill}")
    @Size(min = 3, max = 45, message = "{message.user.login.length}")
    private String login;

    @Size(min = 3, max = 45, message = "{message.user.name.length}")
    private String name;

    @Size(min = 3, max = 45, message = "{message.user.name.length}")
    private String surname;

}
