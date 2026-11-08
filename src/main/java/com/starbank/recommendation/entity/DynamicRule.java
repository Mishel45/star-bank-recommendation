package com.starbank.recommendation.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recommendation_rules")
public class DynamicRule {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "product_text", nullable = false, length = 2000)
    private String productText;

    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QueryCondition> conditions;


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }
    public String getProductText() { return productText; }
    public void setProductText(String productText) { this.productText = productText; }
    public List<QueryCondition> getConditions() { return conditions; }
    public void setConditions(List<QueryCondition> conditions) { this.conditions = conditions; }
}