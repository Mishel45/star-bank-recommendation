package com.starbank.recommendation.service;

import com.starbank.recommendation.entity.DynamicRule;
import com.starbank.recommendation.entity.RuleStat;
import com.starbank.recommendation.repository.DynamicRuleRepository;
import com.starbank.recommendation.repository.RuleStatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DynamicRuleService {

    private final DynamicRuleRepository repository;
    private final RuleStatRepository statRepository;

    public DynamicRuleService(DynamicRuleRepository repository, RuleStatRepository statRepository) {
        this.repository = repository;
        this.statRepository = statRepository;
    }

    @Transactional
    public DynamicRule createRule(DynamicRule rule) {
        rule.setId(null);
        if (rule.getConditions() != null) {
            rule.getConditions().forEach(condition -> condition.setRule(rule));
        }
        DynamicRule savedRule = repository.save(rule);

        // Создаем нулевую статистику для нового правила
        statRepository.save(new RuleStat(savedRule, 0L));
        return savedRule;
    }

    public List<DynamicRule> getAllRules() {
        return repository.findAll();
    }

    public Optional<DynamicRule> getRuleById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteRule(UUID id) {
        // Благодаря CascadeType в БД или сущности, статистика удалится сама
        repository.deleteById(id);
    }

    @Transactional
    public void incrementStat(UUID ruleId) {
        RuleStat stat = statRepository.findByRuleId(ruleId);
        if (stat != null) {
            stat.setCount(stat.getCount() + 1);
            statRepository.save(stat);
        }
    }
}