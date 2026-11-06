package com.starbank.recommendation.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository

public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Проверяет, использует ли пользователь продукт определенного типа.
     * Критерий из ТЗ: "Клиент является пользователем продукта тогда и только тогда,
     * когда в таблице транзакций по этому продукту есть любая транзакция".
     *
     * Оптимизация: Используется SELECT EXISTS вместо выгрузки строк или тяжелого COUNT(*),
     * что позволяет СУБД H2 остановить сканирование при первом же совпадении.
     */
    public boolean hasProductType(UUID userId, String productType) {
        String sql = """
            SELECT EXISTS (
                SELECT 1 
                FROM transactions t
                JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ?
            )
        """;

        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, userId, productType);
        return Boolean.TRUE.equals(result);
    }

    /**
     * Вычисляет агрегированную сумму транзакций конкретного типа операции (DEPOSIT / WITHDRAW)
     * для всех продуктов определенного типа (DEBIT / SAVING / INVEST / CREDIT).
     *
     * Оптимизация: Вся математика выполняется на стороне СУБД H2.
     * Функция COALESCE гарантирует, что если у пользователя нет транзакций данного типа,
     * вместо NULL вернется корректный 0.0, предотвращая NullPointerException в Java.
     */
    public double getSumByProductAndTransactionType(UUID userId, String productType, String transactionType) {
        String sql = """
            SELECT COALESCE(SUM(t.amount), 0)
            FROM transactions t
            JOIN products p ON t.product_id = p.id
            WHERE t.user_id = ? AND p.type = ? AND t.type = ?
        """;

        Double sum = jdbcTemplate.queryForObject(sql, Double.class, userId, productType, transactionType);
        return sum != null ? sum : 0.0;
    }
}