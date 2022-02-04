package com.epam.esm.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class JwtTokenService {

    public Optional<Authentication> getAuthentication(HttpServletRequest servletRequest) {
        return null;
    }

}
