package com.fintech.wallet.service;

import com.fintech.wallet.dto.RegisterRequest;
import com.fintech.wallet.dto.RegisterResponse;
import com.fintech.wallet.entity.User;
import com.fintech.wallet.entity.Wallet;
import com.fintech.wallet.repository.UserRepository;
import com.fintech.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fintech.wallet.exception.EmailAlreadyRegisteredException;


import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public AuthService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email)) {
            throw new EmailAlreadyRegisteredException("Email already registered");
        }

        // NOTE: sementara password kita simpan plain dulu biar kamu paham flow.
        // Nanti step berikutnya kita ganti ke BCrypt (fintech style).
        User user = new User();
        user.setFullName(request.fullName);
        user.setEmail(request.email);
        user.setPasswordHash(request.password);

        userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setUserId(user.getId());
        wallet.setWalletNumber("WALLET-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12));

        walletRepository.save(wallet);

        return new RegisterResponse(user.getId(), wallet.getWalletNumber());
    }
}
