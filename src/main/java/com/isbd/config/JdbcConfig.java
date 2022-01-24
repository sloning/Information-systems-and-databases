package com.isbd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class JdbcConfig {
    @Bean
    public DataSource postgresqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Map<String, String> env = System.getenv();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(env.get("db_host") + "isbd");
        dataSource.setUsername(env.get("db_username"));
        dataSource.setPassword(env.get("db_password"));

        return dataSource;
    }
}
