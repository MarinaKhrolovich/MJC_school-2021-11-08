package com.epam.esm.config;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Profile("dev")
@Configuration
@PropertySource("classpath:properties/${spring.profiles.active}.properties")
@ComponentScan("com.epam.esm")
public class ConfigTest {

    public static final String UTF_8 = "UTF-8";

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().
                addDefaultScripts().
                generateUniqueName(true).
                setScriptEncoding(UTF_8)
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplateDev() {
        return new JdbcTemplate(dataSource());
    }


}
