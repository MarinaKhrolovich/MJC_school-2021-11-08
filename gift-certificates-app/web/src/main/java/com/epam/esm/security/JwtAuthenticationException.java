package com.epam.esm.security;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }

}
