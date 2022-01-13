package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringConfig implements WebMvcConfigurer {

    public static final String UTF_8 = "UTF-8";
    public static final String LOCALIZATION_LOCAL = "localization/local";

    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class, args);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(LOCALIZATION_LOCAL);
        messageSource.setDefaultEncoding(UTF_8);
        return messageSource;
    }

}
