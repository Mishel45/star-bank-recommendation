package com.starbank.recommendation.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "query_conditions")
public class QueryCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rule_id", nullable = false)
    private DynamicRule rule;

    @Column(name = "query", nullable = false)
    private String query;

    @Column(name = "negate", nullable = false)
    private boolean negate;

    @ElementCollection
    @CollectionTable(name = "query_arguments", joinColumns = @JoinColumn(name = "condition_id"))
    @Column(name = "argument")
    private List<String> arguments;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public DynamicRule getRule() { return rule; }
    public void setRule(DynamicRule rule) { this.rule = rule; }
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
    public boolean isNegate() { return negate; }
    public void setNegate(boolean negate) { this.negate = negate; }
    public List<String> getArguments() { return arguments; }
    public void setArguments(List<String> arguments) { this.arguments = arguments; }
}