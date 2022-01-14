package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("test")
@ComponentScan("com.epam.esm")
@PropertySource({"classpath:properties/application.properties", "classpath:properties/application-test.properties"})
@SpringBootApplication
public class ConfigTest {

    public static void main(String[] args) {
        SpringApplication.run(ConfigTest.class, args);
    }

}