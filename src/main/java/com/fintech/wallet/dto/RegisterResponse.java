package com.fintech.wallet.dto;

public class RegisterResponse {
    public Long userId;
    public String walletNumber;

    public RegisterResponse(Long userId, String walletNumber) {
        this.userId = userId;
        this.walletNumber = walletNumber;
    }
}
