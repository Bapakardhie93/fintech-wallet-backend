package com.fintech.wallet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TopupRequest {

    @NotNull
    public Long userId;

    @NotNull
    @Min(1)
    public Long amount;
}
