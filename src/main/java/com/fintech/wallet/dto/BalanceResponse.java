package com.fintech.wallet.dto;

public class BalanceResponse {

    public Long userId;
    public String walletNumber;
    public Long availableBalance;

    public BalanceResponse(Long userId, String walletNumber, Long availableBalance) {
        this.userId = userId;
        this.walletNumber = walletNumber;
        this.availableBalance = availableBalance;
    }
}
