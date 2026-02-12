package com.fintech.wallet.controller;

import com.fintech.wallet.dto.TopupRequest;
import com.fintech.wallet.dto.TopupResponse;
import com.fintech.wallet.dto.BalanceResponse;
import com.fintech.wallet.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;



@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/topup")
    public TopupResponse topup(@RequestBody @Valid TopupRequest request) {
        return walletService.topup(request);
    }

    @GetMapping("/balance")
    public BalanceResponse balance(Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        return walletService.getBalance(userId);
    }


}
