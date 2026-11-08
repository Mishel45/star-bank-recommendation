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

@RestController
@RequestMapping("/management")
public class ManagementController {

    private final TransactionRepository transactionRepository;
    private final BuildProperties BuildProperties; // Будет доступен автоматически после сборки jar-файла

    // Конструктор внедрения зависимостей без Ломбока
    public ManagementController(TransactionRepository transactionRepository, BuildProperties buildInfo) {
        this.transactionRepository = transactionRepository;
        this.BuildProperties = buildInfo;
    }

    // 1. Сброс кеша рекомендаций (POST /management/clear-caches)
    @PostMapping("/clear-caches")
    public ResponseEntity<Void> clearCaches() {
        transactionRepository.clearAllCaches();
        return ResponseEntity.ok().build(); // Возвращает 200 OK без тела
    }

    // 2. Информация о сервисе для продакшена (GET /management/info)
    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getServiceInfo() {
        Map<String, String> info = new HashMap<>();
        // Извлекаем артефакт (имя) и версию динамически из pom.xml
        info.put("name", BuildProperties.getArtifact());
        info.put("version", BuildProperties.getVersion());
        return ResponseEntity.ok(info);
    }
}
