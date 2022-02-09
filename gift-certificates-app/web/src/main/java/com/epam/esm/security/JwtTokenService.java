package com.epam.esm.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenService {

    @Value("${jwt.token.secret-key:secret}")
    private String secretKey;

    @Value("${jwt.token.expiration-sec}")
    private long expirationSec;

    @Value("${jwt.token.header-name}")
    private String tokenHeaderName;

    private final UserDetailsService userService;

    @Autowired
    public JwtTokenService(@Qualifier("userServiceImpl") UserDetailsService userService) {
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createJwtToken(UserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("authorities", userDetails.getAuthorities());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationSec*1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        if (validateJwtToken(token)) {
            String username = getUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } else {
            throw new JwtAuthenticationException("The token is expired");
        }
    }

    public Optional<String> getHeader(HttpServletRequest servletRequest) {
        return Optional
                .ofNullable(servletRequest.getHeader(tokenHeaderName));
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return claimsJws.getBody().getExpiration().after(Date.from(Instant.now()));
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Invalid token");
        }
    }

}
