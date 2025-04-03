package com.example.recommendation.dto;



import java.math.BigDecimal;

public class Investment {
    private Long id;
    private Long userId;
    private BigDecimal amount;

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}

