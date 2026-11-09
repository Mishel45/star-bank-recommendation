package com.starbank.recommendation.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Поиск по username (из ТЗ)
    public List<String> findFullNameByUsername(String username) {
        String sql = "SELECT first_name || ' ' || last_name FROM users WHERE username = ?";
        return jdbcTemplate.queryForList(sql, String.class, username);
    }

    // Поиск по ID (если передан UUID)
    public List<String> findFullNameById(UUID id) {
        String sql = "SELECT first_name || ' ' || last_name FROM users WHERE id = ?";
        return jdbcTemplate.queryForList(sql, String.class, id);
    }

    // Поиск UUID по username (для вызова рекомендаций)
    public UUID findIdByUsername(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, UUID.class, username);
        } catch (Exception e) {
            return null;
        }
    }
}