package com.starbank.recommendation.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    // Три отдельных кэша Caffeine по критериям ТЗ
    private final Cache<String, Boolean> productTypeCache;
    private final Cache<String, Double> sumCache;
    private final Cache<String, Long> countCache;

    public TransactionRepository(JdbcTemplate transactionJdbcTemplate) {
        this.jdbcTemplate = transactionJdbcTemplate;

        // Инициализация кэшей
        this.productTypeCache = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(10000).build();
        this.sumCache = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(10000).build();
        this.countCache = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(10000).build();
    }

    public boolean hasProductType(UUID userId, String productType) {
        String cacheKey = userId.toString() + ":" + productType;

        // Паттерн get-if-absent-compute
        return Boolean.TRUE.equals(productTypeCache.get(cacheKey, key -> {
            String sql = "SELECT EXISTS (SELECT 1 FROM transactions t JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ?)";
            Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, userId, productType);
            return result != null ? result : false;
        }));
    }

    public double getSumByProductAndTransactionType(UUID userId, String productType, String transactionType) {
        String cacheKey = userId.toString() + ":" + productType + ":" + transactionType;

        return sumCache.get(cacheKey, key -> {
            String sql = "SELECT COALESCE(SUM(t.amount), 0) FROM transactions t JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? AND t.type = ?";
            Double sum = jdbcTemplate.queryForObject(sql, Double.class, userId, productType, transactionType);
            return sum != null ? sum : 0.0;
        });
    }

    public long getTransactionCountByProductType(UUID userId, String productType) {
        String cacheKey = userId.toString() + ":" + productType;

        return countCache.get(cacheKey, key -> {
            String sql = "SELECT COUNT(t.id) FROM transactions t JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ?";
            Long count = jdbcTemplate.queryForObject(sql, Long.class, userId, productType);
            return count != null ? count : 0L;
        });
    }

    public void clearCaches() {
        productTypeCache.invalidateAll();
        sumCache.invalidateAll();
        countCache.invalidateAll();
    }
}