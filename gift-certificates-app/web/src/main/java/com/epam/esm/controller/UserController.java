package com.epam.esm.controller;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping
    public UserDTO add(@Valid @RequestBody UserDTO userDTO) {
        UserDTO addedDTO = userService.add(userDTO);
        int id = addedDTO.getId();
        addedDTO.add(linkTo(methodOn(UserController.class).get(id)).withSelfRel());
        return addedDTO;
    }

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable @Min(1) int id) {
        UserDTO userDTO = userService.get(id);
        userDTO.add(linkTo(methodOn(UserController.class).get(id)).withSelfRel());
        return userDTO;
    }

    @GetMapping
    public List<UserDTO> get() {
        List<UserDTO> userDTOS = userService.get();
        if (!CollectionUtils.isEmpty(userDTOS)) {
            userDTOS.forEach(userDTO -> {
                int id = userDTO.getId();
                userDTO.add(linkTo(methodOn(UserController.class).get(id)).withSelfRel());
            });
        }
        return userDTOS;
    }

}
