package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends RepresentationModel<UserDTO> {

    private int id;

    @NotBlank(message = "{message.user.login.fill}")
    @Size(min = 3, max = 45, message = "{message.user.login.length}")
    private String login;

    @Size(min = 3, max = 45, message = "{message.user.name.length}")
    private String name;

    @Size(min = 3, max = 45, message = "{message.user.surname.length}")
    private String surname;

}
