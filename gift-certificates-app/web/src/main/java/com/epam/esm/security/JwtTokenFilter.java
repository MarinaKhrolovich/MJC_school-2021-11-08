package com.epam.esm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenService jwtTokenService;

    @Autowired
    public JwtTokenFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        Optional<String> token = jwtTokenService.getHeader((HttpServletRequest) servletRequest);
        if (token.isPresent()) {
            SecurityContextHolder.getContext().setAuthentication(
                    jwtTokenService.getAuthentication(token.get()));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
