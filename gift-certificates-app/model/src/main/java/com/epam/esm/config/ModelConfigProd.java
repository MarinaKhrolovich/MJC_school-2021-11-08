package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
@PropertySource("classpath:properties/prod.properties")
@ComponentScan("com.epam.esm")
public class ModelConfigProd {

    @Value("${db.driverClassName}")
    private String DB_DRIVER_CLASS_NAME;

    @Value("${db.jdbcUrl}")
    private String DB_JDBC_URL;

    @Value("${db.username}")
    private String DB_USERNAME;

    @Value("${db.password}")
    private String DB_PASSWORD;

    @Value("${db.poolsize}")
    private int DB_POOL_SIZE;

    @Bean
    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DB_DRIVER_CLASS_NAME);
        config.setUsername(DB_USERNAME);
        config.setPassword(DB_PASSWORD);
        config.setJdbcUrl(DB_JDBC_URL);
        config.setMaximumPoolSize(DB_POOL_SIZE);

        return new HikariDataSource(config);
    }
    @Bean
    public JdbcTemplate jdbcTemplateDev() {
        return new JdbcTemplate(dataSource());
    }

}
