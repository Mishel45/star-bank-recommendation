package com.starbank.recommendation.controller;

import com.starbank.recommendation.entity.DynamicRule;
import com.starbank.recommendation.repository.RuleStatRepository;
import com.starbank.recommendation.service.DynamicRuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {

    private final DynamicRuleService service;
    private final RuleStatRepository statRepository;

    public DynamicRuleController(DynamicRuleService service, RuleStatRepository statRepository) {
        this.service = service;
        this.statRepository = statRepository;
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

    @GetMapping("/stats")
    public ResponseEntity<Map<String, List<Map<String, String>>>> getStats() {
        List<Map<String, String>> statsList = statRepository.findAll().stream()
                .map(stat -> Map.of(
                        "rule_id", stat.getRule().getId().toString(),
                        "count", stat.getCount().toString()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of("stats", statsList));
    }
}