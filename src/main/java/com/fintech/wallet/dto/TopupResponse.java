package com.fintech.wallet.dto;

public class TopupResponse {

    public Long userId;
    public String walletNumber;
    public Long newBalance;

    public TopupResponse(Long userId, String walletNumber, Long newBalance) {
        this.userId = userId;
        this.walletNumber = walletNumber;
        this.newBalance = newBalance;
    }
}
