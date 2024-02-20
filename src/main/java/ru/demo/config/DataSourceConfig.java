package ru.demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig("/hikaricp.properties");
        return new HikariDataSource(config);
    }
}
