package com.fintech.wallet.controller;

import com.fintech.wallet.dto.BalanceResponse;
import com.fintech.wallet.dto.TopupRequest;
import com.fintech.wallet.dto.TopupResponse;
import com.fintech.wallet.dto.TransferRequest;
import com.fintech.wallet.dto.TransferResponse;
import com.fintech.wallet.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/transfer")
    public TransferResponse transfer(
            Authentication authentication,
            @RequestBody @Valid TransferRequest request
    ) {
        Long senderUserId = (Long) authentication.getPrincipal();
        return walletService.transfer(senderUserId, request);
    }
}
