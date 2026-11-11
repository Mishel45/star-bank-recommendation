package com.starbank.recommendation.controller;

import com.starbank.recommendation.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    private BuildProperties buildProperties;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getInfo() {
        return ResponseEntity.ok(Map.of(
                "name", buildProperties.getArtifact(),
                "version", buildProperties.getVersion()
        ));
    }

    @PostMapping("/clear-caches")
    public ResponseEntity<Void> clearCaches() {
        transactionRepository.clearCaches();
        return ResponseEntity.ok().build();
    }
}