package com.fintech.wallet.dto;

import java.time.LocalDateTime;

public class TransactionResponse {

    private Long id;
    private String type;
    private Long amount;
    private Long balanceAfter;
    private LocalDateTime createdAt;

    // NEW
    private Long counterpartyUserId;
    private String counterpartyName;
    private String transferRef;

    // constructor lama (biar gak ngerusak)
    public TransactionResponse(Long id, String type, Long amount, Long balanceAfter, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.createdAt = createdAt;
    }

    // constructor baru (recommended)
    public TransactionResponse(
            Long id,
            String type,
            Long amount,
            Long balanceAfter,
            LocalDateTime createdAt,
            Long counterpartyUserId,
            String counterpartyName,
            String transferRef
    ) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.createdAt = createdAt;
        this.counterpartyUserId = counterpartyUserId;
        this.counterpartyName = counterpartyName;
        this.transferRef = transferRef;
    }

    public Long getId() { return id; }
    public String getType() { return type; }
    public Long getAmount() { return amount; }
    public Long getBalanceAfter() { return balanceAfter; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // NEW getter
    public Long getCounterpartyUserId() { return counterpartyUserId; }
    public String getCounterpartyName() { return counterpartyName; }
    public String getTransferRef() { return transferRef; }
}
