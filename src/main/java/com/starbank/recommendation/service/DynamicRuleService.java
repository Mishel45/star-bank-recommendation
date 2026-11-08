package com.starbank.recommendation.service;

import com.starbank.recommendation.entity.DynamicRule;
import com.starbank.recommendation.repository.DynamicRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DynamicRuleService {

    private final DynamicRuleRepository repository;

    public DynamicRuleService(DynamicRuleRepository repository) {
        this.repository = repository;
    }

    public DynamicRule createRule(DynamicRule rule) {
        // Обнуляем ID, чтобы база сама его сгенерировала
        rule.setId(null);
        // Связываем условия с правилом
        if (rule.getConditions() != null) {
            rule.getConditions().forEach(condition -> condition.setRule(rule));
        }
        return repository.save(rule);
    }

    public List<DynamicRule> getAllRules() {
        return repository.findAll();
    }

    public Optional<DynamicRule> getRuleById(UUID id) {
        return repository.findById(id);
    }

    public void deleteRule(UUID id) {
        repository.deleteById(id);
    }
}