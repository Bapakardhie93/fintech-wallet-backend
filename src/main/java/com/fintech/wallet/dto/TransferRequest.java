package com.fintech.wallet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransferRequest {

    @NotBlank(message = "toEmail is required")
    @Email(message = "toEmail must be a valid email")
    private String toEmail;

    @NotNull(message = "amount is required")
    @Min(value = 1, message = "amount must be greater than 0")
    private Long amount;

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
