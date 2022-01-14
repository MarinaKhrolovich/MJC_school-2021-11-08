package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource({"classpath:properties/application.properties",
        "classpath:properties/application-${spring.profiles.active}.properties"})
@SpringBootApplication
public class ConfigDAO {

    public static void main(String[] args) {
        SpringApplication.run(ConfigDAO.class, args);
    }

}
