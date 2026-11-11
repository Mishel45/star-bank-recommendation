package com.starbank.recommendation.service;

import com.starbank.recommendation.dto.RecommendationDto;
import com.starbank.recommendation.service.rule.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис для управления и вычисления персональных рекомендаций клиентов.
 * Объединяет обработку статических стратегий и динамических предикатов.
 *
 * @author Михаил Павлов
 * @author Диана Скибицкая
 * @version 1.0.0
 */
@Service
public class RecommendationService {

    // Автоматически внедряем все реализации интерфейса через Spring IoC
    private final List<RecommendationRuleSet> ruleSets;

    // Классический конструктор для внедрения зависимостей
    public RecommendationService(List<RecommendationRuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }

    /**
     * Формирует полный гибридный список персональных предложений для конкретного пользователя.
     * Опрашивает фиксированные правила из первого ТЗ и динамические правила из PostgreSQL.
     *
     * @param userId уникальный идентификатор пользователя (UUID)
     * @return список объектов {@link RecommendationDto}, подходящих под условия транзакций клиента
     */
    public List<RecommendationDto> getAllRecommendationsForUser(UUID userId) {
        List<RecommendationDto> validRecommendations = new ArrayList<>();

        for (RecommendationRuleSet rule : ruleSets) {
            Optional<RecommendationDto> result = rule.check(userId);
            // Если правило вернуло заполненный объект — добавляем его в итоговый список
            if (result.isPresent()) {
                validRecommendations.add(result.get());
            }
        }

        return validRecommendations;
    }
}