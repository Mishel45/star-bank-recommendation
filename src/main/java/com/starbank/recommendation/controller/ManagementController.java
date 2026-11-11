package com.starbank.recommendation.controller;

import com.starbank.recommendation.repository.TransactionRepository;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Контроллер управления приложением (информация о сборке и сброс кэшей).
 * Использует внедрение зависимостей через конструктор.
 */
@RestController
@RequestMapping("/management")
public class ManagementController {

    private final TransactionRepository transactionRepository;
    private final BuildProperties buildProperties;

    // Конструктор внедрения зависимостей без использования Lombok
    public ManagementController(TransactionRepository transactionRepository, BuildProperties buildProperties) {
        this.transactionRepository = transactionRepository;
        this.buildProperties = buildProperties;
    }

    /**
     * Сброс кеша рекомендаций (POST /management/clear-caches).
     * Очищает кэши Caffeine в репозитории транзакций.
     */
    @PostMapping("/clear-caches")
    public ResponseEntity<Void> clearCaches() {
        transactionRepository.clearAllCaches();
        return ResponseEntity.ok().build();
    }

    /**
     * Информация о сервисе для продакшена (GET /management/info).
     * Динамически извлекает имя и версию проекта из pom.xml.
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getServiceInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("name", buildProperties.getArtifact());
        info.put("version", buildProperties.getVersion());
        return ResponseEntity.ok(info);
    }
}