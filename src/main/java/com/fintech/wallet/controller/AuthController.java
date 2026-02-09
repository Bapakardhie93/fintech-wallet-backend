package com.fintech.wallet.controller;

import com.fintech.wallet.dto.RegisterRequest;
import com.fintech.wallet.dto.RegisterResponse;
import com.fintech.wallet.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}
