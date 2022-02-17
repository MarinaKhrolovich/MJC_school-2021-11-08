package com.epam.esm.dto;

import com.epam.esm.bean.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends RepresentationModel<UserDTO> implements UserDetails {

    private int id;

    @NotBlank(message = "{message.user.login.fill}")
    @Size(min = 3, max = 45, message = "{message.user.login.length}")
    private String username;

    @NotBlank(message = "{message.user.password.fill}")
    @Size(min = 8, max = 100, message = "{message.user.password.length}")
    private String password;

    @Size(min = 3, max = 45, message = "{message.user.name.length}")
    private String name;

    @Size(min = 3, max = 45, message = "{message.user.surname.length}")
    private String surname;

    private Set<Role> authorities;

    @JsonIgnore
    private boolean accountNonExpired = true;

    @JsonIgnore
    private boolean accountNonLocked = true;

    @JsonIgnore
    private boolean credentialsNonExpired = true;

    @JsonIgnore
    private boolean enabled = true;

}
