package com.epam.esm.controller;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserDTO get(@Valid @RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        return userDTO;
    }

    @PreAuthorize("GUEST")
    @PostMapping("/signup")
    public UserDTO add(@Valid @RequestBody UserDTO userDTO) {
        UserDTO addedDTO = userService.add(userDTO);
        int id = addedDTO.getId();
        addedDTO.add(linkTo(methodOn(UserController.class).get(id)).withSelfRel());
        return addedDTO;
    }

}
