package com.epam.esm.controller;

import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable @Min(1) int id) {
        UserDTO userDTO = userService.get(id);
        userDTO.add(linkTo(methodOn(UserController.class).get(id)).withSelfRel());
        return userDTO;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDTO> get(PageDTO pageDTO) {
        List<UserDTO> userDTOS = userService.get(pageDTO);
        if (!CollectionUtils.isEmpty(userDTOS)) {
            userDTOS.forEach(userDTO -> {
                int id = userDTO.getId();
                userDTO.add(linkTo(methodOn(UserController.class).get(id)).withSelfRel());
            });
        }
        return userDTOS;
    }

}
