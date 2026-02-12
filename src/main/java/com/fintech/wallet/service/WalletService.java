package com.fintech.wallet.service;

import com.fintech.wallet.dto.TopupRequest;
import com.fintech.wallet.dto.TopupResponse;
import com.fintech.wallet.entity.Wallet;
import com.fintech.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fintech.wallet.dto.BalanceResponse;


@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public TopupResponse topup(TopupRequest request) {

        Wallet wallet = walletRepository.findByUserId(request.userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Long currentBalance = wallet.getAvailableBalance();
        if (currentBalance == null) currentBalance = 0L;

        Long newBalance = currentBalance + request.amount;

        wallet.setAvailableBalance(newBalance);
        walletRepository.save(wallet);

        return new TopupResponse(wallet.getUserId(), wallet.getWalletNumber(), newBalance);

    }
    public BalanceResponse getBalance(Long userId) {

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        return new BalanceResponse(
                wallet.getUserId(),
                wallet.getWalletNumber(),
                wallet.getAvailableBalance()
        );
    }

}
