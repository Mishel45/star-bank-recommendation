package com.starbank.recommendation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${starbank.transactions.datasource.url}")
    private String transactionDbUrl;

    @Value("${starbank.transactions.datasource.driver-class-name}")
    private String transactionDriver;

    /**
     * Конфигурация ОСНОВНОЙ базы данных (PostgreSQL).
     * Аннотация @Primary указывает Spring Data JPA и Liquibase, что они должны
     * разворачивать таблицы динамических правил именно в PostgreSQL.
     */
    @Primary
    @Bean(name = "defaultDataSource")
    public DataSource defaultDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    /**
     * Конфигурация ВТОРОЙ базы данных (H2 File).
     * Настраивается вручную на основе параметров из application.properties.
     */
    @Bean(name = "transactionDataSource")
    public DataSource transactionDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(transactionDriver);
        dataSource.setUrl(transactionDbUrl);
        return dataSource;
    }

    /**
     * Создание JdbcTemplate, жестко привязанного ко второй базе данных (H2).
     * Именно этот бин будет использовать ваш TransactionRepository.
     */
    @Bean
    public JdbcTemplate transactionJdbcTemplate() {
        return new JdbcTemplate(transactionDataSource());
    }
}
