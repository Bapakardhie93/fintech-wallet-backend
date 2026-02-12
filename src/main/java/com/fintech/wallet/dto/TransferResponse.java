package com.fintech.wallet.dto;

public class TransferResponse {

    private String message;
    private Long senderBalance;

    public TransferResponse(String message, Long senderBalance) {
        this.message = message;
        this.senderBalance = senderBalance;
    }

    public String getMessage() {
        return message;
    }

    public Long getSenderBalance() {
        return senderBalance;
    }
}
