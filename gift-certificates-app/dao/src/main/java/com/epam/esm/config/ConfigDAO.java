package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;

@PropertySource({"classpath:properties/application.properties",
        "classpath:properties/application-${spring.profiles.active}.properties"})
@SpringBootApplication
@EntityScan("com.epam.esm.bean")
public class ConfigDAO {

    public static void main(String[] args) {
        SpringApplication.run(ConfigDAO.class, args);
    }

}
