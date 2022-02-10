package com.epam.esm.security;

import com.epam.esm.exception.MessageLocal;
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
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenService {

    public static final String AUTHORITIES = "authorities";

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
        claims.put(AUTHORITIES, userDetails.getAuthorities());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationSec * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Optional<String> getHeader(HttpServletRequest servletRequest) {
        return Optional
                .ofNullable(servletRequest.getHeader(tokenHeaderName));
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public void validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException(MessageLocal.MESSAGE_TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException(MessageLocal.MESSAGE_TOKEN_INVALID);
        }
    }

}

