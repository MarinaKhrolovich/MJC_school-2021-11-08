package com.epam.esm.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenService {

    private static final String JWT_TOKEN_HEADER_NAME = "X-Auth-Token";

    @Value("${jwt.token.secret-key:secret}")
    private String secretKey;

    @Value("${jwt.token.expiration}")
    private String expiration;

    private final UserDetailsService userService;

    @Autowired
    public JwtTokenService(@Qualifier("userServiceImpl") UserDetailsService userService) {
        this.userService = userService;
    }

    public Authentication getAuthentication(HttpServletRequest servletRequest) {
        Optional<String> jwtToken = Optional
                .ofNullable(servletRequest.getHeader(servletRequest.getHeader(JWT_TOKEN_HEADER_NAME)));
        if (jwtToken.isPresent()) {
            String jwtTokenValue = jwtToken.get();
            if (validateJwtToken(jwtTokenValue)) {
                String username = getUsername(jwtTokenValue);
                UserDetails userDetails = userService.loadUserByUsername(username);
                return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
            }
        }
        return null;
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return claimsJws.getBody().getExpiration().after(Date.from(Instant.now()));
    }

}
