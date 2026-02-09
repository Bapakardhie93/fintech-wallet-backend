package com.fintech.wallet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    @Size(min = 3, max = 120)
    public String fullName;

    @NotBlank
    @Email
    public String email;

    @NotBlank
    @Size(min = 6, max = 100)
    public String password;
}
