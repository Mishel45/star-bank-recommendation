package com.starbank.recommendation.controller;

import com.starbank.recommendation.entity.DynamicRule;
import com.starbank.recommendation.service.DynamicRuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {

    private final DynamicRuleService service;

    public DynamicRuleController(DynamicRuleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DynamicRule> createRule(@RequestBody DynamicRule rule) {
        DynamicRule created = service.createRule(rule);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<DynamicRule>> getAllRules() {
        return ResponseEntity.ok(service.getAllRules());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable UUID id) {
        service.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
}