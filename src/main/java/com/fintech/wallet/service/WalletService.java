package com.fintech.wallet.service;

import com.fintech.wallet.dto.BalanceResponse;
import com.fintech.wallet.dto.TopupRequest;
import com.fintech.wallet.dto.TopupResponse;
import com.fintech.wallet.dto.TransferRequest;
import com.fintech.wallet.dto.TransferResponse;
import com.fintech.wallet.entity.User;
import com.fintech.wallet.entity.Wallet;
import com.fintech.wallet.repository.UserRepository;
import com.fintech.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TopupResponse topup(TopupRequest request) {

        Wallet wallet = walletRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Long currentBalance = wallet.getAvailableBalance();
        if (currentBalance == null) currentBalance = 0L;

        Long newBalance = currentBalance + request.getAmount();

        wallet.setAvailableBalance(newBalance);
        walletRepository.save(wallet);

        return new TopupResponse(
                wallet.getUserId(),
                wallet.getWalletNumber(),
                newBalance
        );
    }

    public BalanceResponse getBalance(Long userId) {

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Long balance = wallet.getAvailableBalance();
        if (balance == null) balance = 0L;

        return new BalanceResponse(
                wallet.getUserId(),
                wallet.getWalletNumber(),
                balance
        );
    }

    @Transactional
    public TransferResponse transfer(Long senderUserId, TransferRequest request) {

        Long amount = request.getAmount();
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        // cari receiver berdasarkan email
        User receiver = userRepository.findByEmail(request.getToEmail())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // tidak boleh transfer ke diri sendiri
        if (senderUserId.equals(receiver.getId())) {
            throw new RuntimeException("Cannot transfer to yourself");
        }

        Wallet senderWallet = walletRepository.findByUserId(senderUserId)
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        Wallet receiverWallet = walletRepository.findByUserId(receiver.getId())
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        // saldo sender
        Long senderBalance = senderWallet.getAvailableBalance();
        if (senderBalance == null) senderBalance = 0L;

        if (senderBalance < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // saldo receiver
        Long receiverBalance = receiverWallet.getAvailableBalance();
        if (receiverBalance == null) receiverBalance = 0L;

        // debit sender
        senderWallet.setAvailableBalance(senderBalance - amount);

        // credit receiver
        receiverWallet.setAvailableBalance(receiverBalance + amount);

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        return new TransferResponse(
                "Transfer success",
                senderWallet.getAvailableBalance()
        );
    }
}
