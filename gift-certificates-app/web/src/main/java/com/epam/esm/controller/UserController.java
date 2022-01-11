package com.epam.esm.controller;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

     private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    UserDTO add(@Valid @RequestBody UserDTO userDTO){
        return userService.add(userDTO);
    }

    @GetMapping("/{id}")
    UserDTO get(@PathVariable @Min(1) int id){
        return userService.get(id);
    }

    @GetMapping
    List<UserDTO> get(){
        return userService.get();
    }

}
