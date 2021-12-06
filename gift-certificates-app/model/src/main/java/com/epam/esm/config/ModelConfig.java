package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:properties/db.properties")
@ComponentScan("com.epam.esm")
public class ModelConfig {

    @Value("${db.driverClassName}")
    private String DB_DRIVER_CLASS_NAME;

    @Value("${db.jdbcUrl}")
    private String DB_JDBC_URL;

    @Value("${db.username}")
    private String DB_USERNAME;

    @Value("${db.password}")
    private String DB_PASSWORD;

    @Bean
    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DB_DRIVER_CLASS_NAME);
        config.setUsername(DB_USERNAME);
        config.setPassword(DB_PASSWORD);
        config.setJdbcUrl(DB_JDBC_URL);

        HikariDataSource dataSource = new HikariDataSource(config);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
