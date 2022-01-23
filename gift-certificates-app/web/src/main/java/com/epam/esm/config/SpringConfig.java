package com.epam.esm.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class SpringConfig {

    public static final String UTF_8 = "UTF-8";
    public static final String LOCALIZATION_LOCAL = "localization/local";

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(LOCALIZATION_LOCAL);
        messageSource.setDefaultEncoding(UTF_8);
        return messageSource;
    }

}
