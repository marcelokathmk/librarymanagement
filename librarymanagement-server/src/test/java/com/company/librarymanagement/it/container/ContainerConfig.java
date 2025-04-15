package com.company.librarymanagement.it.container;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.sql.DataSource;

@TestConfiguration
public class ContainerConfig {

    static final PostgreSQLContainer<?> POSTGRES_CONTAINER;

    static {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("library")
                .withUsername("postgres")
                .withPassword("postgres");
        POSTGRES_CONTAINER.start();
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(POSTGRES_CONTAINER.getJdbcUrl());
        ds.setUsername(POSTGRES_CONTAINER.getUsername());
        ds.setPassword(POSTGRES_CONTAINER.getPassword());
        ds.setDriverClassName(POSTGRES_CONTAINER.getDriverClassName());
        return ds;
    }
}
