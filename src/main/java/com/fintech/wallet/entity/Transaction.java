package com.fintech.wallet.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String type; // TOPUP, TRANSFER_OUT, TRANSFER_IN

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Long balanceAfter;

    // NEW
    @Column(name="counterparty_user_id")
    private Long counterpartyUserId;

    // NEW
    @Column(name="transfer_ref", length = 64)
    private String transferRef;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Transaction() {}

    // untuk TOPUP / transaksi yang tidak punya counterparty
    public Transaction(Long userId, String type, Long amount, Long balanceAfter) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.createdAt = LocalDateTime.now();
    }

    // untuk TRANSFER (punya counterparty + ref)
    public Transaction(Long userId, String type, Long amount, Long balanceAfter,
                       Long counterpartyUserId, String transferRef) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.counterpartyUserId = counterpartyUserId;
        this.transferRef = transferRef;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getType() { return type; }
    public Long getAmount() { return amount; }
    public Long getBalanceAfter() { return balanceAfter; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // NEW getter
    public Long getCounterpartyUserId() { return counterpartyUserId; }
    public String getTransferRef() { return transferRef; }
}
