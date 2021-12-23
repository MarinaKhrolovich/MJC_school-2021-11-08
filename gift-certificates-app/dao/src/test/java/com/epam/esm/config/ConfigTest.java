package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Profile("test")
@Configuration
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
@PropertySource({"classpath:properties/application.properties", "classpath:properties/application-test.properties"})
public class ConfigTest {

    private final Environment env;

    @Value("${db.poolsize:5}")
    private int MAX_POOL_SIZE;

    @Autowired
    public ConfigTest(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getProperty("db.driverClassName"));
        config.setUsername(env.getProperty("db.username"));
        config.setPassword(env.getProperty("db.password"));
        config.setJdbcUrl(env.getProperty("db.jdbcUrl"));
        config.setMaximumPoolSize(MAX_POOL_SIZE);
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

}
