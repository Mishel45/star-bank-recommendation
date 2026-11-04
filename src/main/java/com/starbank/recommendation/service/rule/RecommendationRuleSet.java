package com.starbank.recommendation.service.rule;

import com.starbank.recommendation.dto.RecommendationDto;
import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    /**
     * Проверяет, подходит ли пользователь под правила конкретной рекомендации.
     * Если подходит — возвращает заполненный Optional с DTO продукта,
     * если нет — возвращает Optional.empty().
     */
    Optional<RecommendationDto> check(UUID userId);
}
