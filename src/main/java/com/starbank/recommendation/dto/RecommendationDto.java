package com.starbank.recommendation.dto;

import java.util.UUID;

public class RecommendationDto {

    private final UUID id;
    private final String name;
    private final String text;

    // Конструктор для создания объекта рекомендации
    public RecommendationDto(UUID id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    // Геттеры обязательны, чтобы Spring Boot смог автоматически преобразовать этот объект в JSON
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}