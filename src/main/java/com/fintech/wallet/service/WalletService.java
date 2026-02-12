package com.fintech.wallet.service;

import com.fintech.wallet.dto.BalanceResponse;
import com.fintech.wallet.dto.TopupRequest;
import com.fintech.wallet.dto.TopupResponse;
import com.fintech.wallet.dto.TransferRequest;
import com.fintech.wallet.dto.TransferResponse;
import com.fintech.wallet.entity.Transaction;
import com.fintech.wallet.entity.User;
import com.fintech.wallet.entity.Wallet;
import com.fintech.wallet.repository.TransactionRepository;
import com.fintech.wallet.repository.UserRepository;
import com.fintech.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fintech.wallet.dto.TransactionResponse;
import com.fintech.wallet.entity.Transaction;
import java.util.List;


@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public WalletService(
            WalletRepository walletRepository,
            UserRepository userRepository,
            TransactionRepository transactionRepository
    ) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TopupResponse topup(Long userId, TopupRequest request) {

        Long amount = request.getAmount();
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Long currentBalance = wallet.getAvailableBalance();
        if (currentBalance == null) currentBalance = 0L;

        Long newBalance = currentBalance + amount;

        wallet.setAvailableBalance(newBalance);
        walletRepository.save(wallet);

        // simpan transaksi TOPUP
        transactionRepository.save(
                new Transaction(userId, "TOPUP", amount, newBalance)
        );

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
    public List<TransactionResponse> getTransactions(Long userId) {

        return transactionRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(tx -> new TransactionResponse(
                        tx.getId(),
                        tx.getType(),
                        tx.getAmount(),
                        tx.getBalanceAfter(),
                        tx.getCreatedAt()
                ))
                .toList();
    }


    @Transactional
    public TransferResponse transfer(Long senderUserId, TransferRequest request) {

        Long amount = request.getAmount();
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        User receiver = userRepository.findByEmail(request.getToEmail())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (senderUserId.equals(receiver.getId())) {
            throw new RuntimeException("Cannot transfer to yourself");
        }

        Wallet senderWallet = walletRepository.findByUserId(senderUserId)
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        Wallet receiverWallet = walletRepository.findByUserId(receiver.getId())
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        Long senderBalance = senderWallet.getAvailableBalance();
        if (senderBalance == null) senderBalance = 0L;

        if (senderBalance < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        Long receiverBalance = receiverWallet.getAvailableBalance();
        if (receiverBalance == null) receiverBalance = 0L;

        // update saldo
        senderWallet.setAvailableBalance(senderBalance - amount);
        receiverWallet.setAvailableBalance(receiverBalance + amount);

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        // simpan transaksi sender (keluar)
        transactionRepository.save(
                new Transaction(senderUserId, "TRANSFER_OUT", amount, senderWallet.getAvailableBalance())
        );

        // simpan transaksi receiver (masuk)
        transactionRepository.save(
                new Transaction(receiver.getId(), "TRANSFER_IN", amount, receiverWallet.getAvailableBalance())
        );

        return new TransferResponse(
                "Transfer success",
                senderWallet.getAvailableBalance()
        );
    }
}
