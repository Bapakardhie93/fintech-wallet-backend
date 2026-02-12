package com.fintech.wallet.dto;

import java.time.LocalDateTime;

public class TransactionResponse {

    private Long id;
    private String type;
    private Long amount;
    private Long balanceAfter;
    private LocalDateTime createdAt;

    public TransactionResponse(Long id, String type, Long amount, Long balanceAfter, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getType() { return type; }
    public Long getAmount() { return amount; }
    public Long getBalanceAfter() { return balanceAfter; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
