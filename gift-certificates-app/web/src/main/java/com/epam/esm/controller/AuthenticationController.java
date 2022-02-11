package com.epam.esm.controller;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.security.JwtAuthenticationResponse;
import com.epam.esm.security.JwtTokenService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public AuthenticationController(UserService userService, AuthenticationManager authenticationManager,
                                    @Qualifier("userServiceImpl") UserDetailsService userDetailsService,
                                    JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@Valid @RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, userDTO.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtTokenService.createJwtToken(userDetails);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse(username, token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    public UserDTO registration(@Valid @RequestBody UserDTO userDTO) {
        UserDTO addedDTO = userService.add(userDTO);
        int id = addedDTO.getId();
        addedDTO.add(linkTo(methodOn(UserController.class).get(id)).withSelfRel());
        return addedDTO;
    }

}
