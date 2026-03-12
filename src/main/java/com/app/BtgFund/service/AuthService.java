package com.app.BtgFund.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.BtgFund.api.dto.AuthResponse;
import com.app.BtgFund.api.dto.LoginRequest;
import com.app.BtgFund.api.dto.RegisterRequest;
import com.app.BtgFund.common.BusinessException;
import com.app.BtgFund.domain.NotificationChannel;
import com.app.BtgFund.domain.RoleName;
import com.app.BtgFund.domain.UserAccount;
import com.app.BtgFund.repository.UserAccountRepository;
import com.app.BtgFund.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    public static final BigDecimal INITIAL_BALANCE = new BigDecimal("500000");

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        userAccountRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new BusinessException("Email is already in use");
        });

        UserAccount user = UserAccount.builder()
                .email(request.email())
                .fullName(request.fullName())
                .phone(request.phone())
            .preferredNotificationChannel(request.preferredNotificationChannel() == null
                ? NotificationChannel.EMAIL
                : request.preferredNotificationChannel())
                .passwordHash(passwordEncoder.encode(request.password()))
                .roles(Set.of(RoleName.ROLE_USER))
                .availableBalance(INITIAL_BALANCE)
                .createdAt(Instant.now())
                .build();

        userAccountRepository.save(user);

        UserDetails details = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(details);

        return new AuthResponse(token, "Bearer", user.getEmail(), user.getRoles(), user.getAvailableBalance());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        UserAccount user = userAccountRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("Invalid credentials"));

        UserDetails details = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(details);

        return new AuthResponse(token, "Bearer", user.getEmail(), user.getRoles(), user.getAvailableBalance());
    }
}
