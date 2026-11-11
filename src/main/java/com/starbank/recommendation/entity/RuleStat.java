package com.starbank.recommendation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rule_stats")
public class RuleStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь с правилом (1 к 1). При удалении правила статистика удалится каскадно.
    @OneToOne
    @JoinColumn(name = "rule_id", nullable = false)
    private DynamicRule rule;

    @Column(nullable = false)
    private Long count = 0L;

    public RuleStat() {
    }

    public RuleStat(DynamicRule rule, Long count) {
        this.rule = rule;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DynamicRule getRule() {
        return rule;
    }

    public void setRule(DynamicRule rule) {
        this.rule = rule;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}