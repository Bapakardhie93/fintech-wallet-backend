package com.fintech.wallet.dto;

public class UserMeResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String walletNumber;

    public UserMeResponse(Long userId, String fullName, String email, String walletNumber) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.walletNumber = walletNumber;
    }

    public Long getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getWalletNumber() { return walletNumber; }
}
