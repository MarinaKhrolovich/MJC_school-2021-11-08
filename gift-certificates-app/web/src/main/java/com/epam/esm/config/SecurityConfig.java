package com.epam.esm.config;

import com.epam.esm.security.JwtEntryPoint;
import com.epam.esm.security.JwtExceptionHandlerFilter;
import com.epam.esm.security.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTRATION_ENDPOINT = "/registration";
    private static final String GET_CERTIFICATES_ENDPOINT = "/certificates/**";

    private final JwtTokenFilter jwtTokenFilter;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final JwtEntryPoint jwtEntryPoint;

    @Autowired
    public SecurityConfig(JwtTokenFilter jwtTokenFilter, JwtExceptionHandlerFilter jwtExceptionHandlerFilter,
                          JwtEntryPoint jwtEntryPoint) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.jwtExceptionHandlerFilter = jwtExceptionHandlerFilter;
        this.jwtEntryPoint = jwtEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtExceptionHandlerFilter, LogoutFilter.class)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(REGISTRATION_ENDPOINT).not().fullyAuthenticated()
                .antMatchers(HttpMethod.GET, GET_CERTIFICATES_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public FilterRegistrationBean<JwtTokenFilter> registration() {
        FilterRegistrationBean<JwtTokenFilter> registration = new FilterRegistrationBean<>(jwtTokenFilter);
        registration.setEnabled(false);
        return registration;
    }

}
