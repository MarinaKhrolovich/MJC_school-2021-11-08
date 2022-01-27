package com.epam.esm.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource({"classpath:properties/application.properties",
        "classpath:properties/application-${spring.profiles.active}.properties"})
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.epam.esm")
@EntityScan("com.epam.esm.bean")
public class ConfigDAO {

}
