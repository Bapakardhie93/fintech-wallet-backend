package com.fintech.wallet.dto;

public class LoginResponse {

    public String token;
    public Long userId;
    public String walletNumber;

    public LoginResponse(String token, Long userId, String walletNumber) {
        this.token = token;
        this.userId = userId;
        this.walletNumber = walletNumber;
    }
}
