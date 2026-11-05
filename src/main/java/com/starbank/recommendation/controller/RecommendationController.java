package com.starbank.recommendation.controller;

import com.starbank.recommendation.dto.RecommendationDto;
import com.starbank.recommendation.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{user_id}")
    public Map<String, Object> getRecommendations(@PathVariable UUID user_id) {
        // Получаем рекомендации из сервиса
        List<RecommendationDto> recommendations = recommendationService.getAllRecommendationsForUser(user_id);

        // Ответ в формате, который требует ТЗ
        Map<String, Object> response = new HashMap<>();
        response.put("user_id", user_id);
        response.put("recommendations", recommendations);

        return response;
    }
}