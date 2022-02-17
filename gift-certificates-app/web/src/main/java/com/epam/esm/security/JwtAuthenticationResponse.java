package com.epam.esm.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {

        private int id;

        private String username;

        private String token;

}
