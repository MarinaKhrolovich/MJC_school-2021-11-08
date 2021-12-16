package com.epam.esm.config;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Profile("dev")
@Configuration
@ComponentScan("com.epam.esm")
public class ConfigTest {

    public static final String UTF_8 = "UTF-8";
    public static final String CLASSPATH_DB_CREATE_SQL = "classpath:db_create.sql";

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().
                generateUniqueName(true).
                setScriptEncoding(UTF_8)
                .setType(EmbeddedDatabaseType.H2).addScript(CLASSPATH_DB_CREATE_SQL)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }


}
