package com.fintech.wallet.service;

import com.fintech.wallet.dto.LoginRequest;
import com.fintech.wallet.dto.LoginResponse;
import com.fintech.wallet.dto.RegisterRequest;
import com.fintech.wallet.dto.RegisterResponse;
import com.fintech.wallet.entity.User;
import com.fintech.wallet.entity.Wallet;
import com.fintech.wallet.exception.EmailAlreadyRegisteredException;
import com.fintech.wallet.repository.UserRepository;
import com.fintech.wallet.repository.WalletRepository;
import com.fintech.wallet.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            WalletRepository walletRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        String email = request.getEmail().trim().toLowerCase();
        String fullName = request.getFullName().trim();
        String password = request.getPassword();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyRegisteredException("Email already registered");
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);

        // simpan password HASH (bukan plain)
        user.setPasswordHash(passwordEncoder.encode(password));

        userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setUserId(user.getId());
        wallet.setWalletNumber(generateWalletNumber());

        walletRepository.save(wallet);

        return new RegisterResponse(user.getId(), wallet.getWalletNumber());
    }

    public LoginResponse login(LoginRequest request) {

        String email = request.getEmail().trim().toLowerCase();
        String password = request.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        String token = jwtService.generateToken(user.getId(), user.getEmail());

        return new LoginResponse(token, user.getId(), wallet.getWalletNumber());
    }

    private String generateWalletNumber() {
        return "WALLET-" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12);
    }
}
