package com.starbank.recommendation.repository;

import com.starbank.recommendation.entity.RuleStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RuleStatRepository extends JpaRepository<RuleStat, Long> {
    // Поиск статистики по ID правила
    RuleStat findByRuleId(UUID ruleId);
}