package com.app.BtgFund.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.BtgFund.api.dto.RegisterRequest;
import com.app.BtgFund.common.BusinessException;
import com.app.BtgFund.domain.NotificationChannel;
import com.app.BtgFund.repository.UserAccountRepository;
import com.app.BtgFund.security.JwtService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerShouldCreateUserWithInitialBalance() {
        RegisterRequest request = new RegisterRequest(
                "Ana Perez",
                "ana@btg.com",
                "3001234567",
                NotificationChannel.EMAIL,
                "StrongPass123");

        when(userAccountRepository.findByEmail("ana@btg.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("StrongPass123")).thenReturn("hash");
        when(userDetailsService.loadUserByUsername("ana@btg.com"))
                .thenReturn(User.withUsername("ana@btg.com").password("hash").roles("USER").build());
        when(jwtService.generateToken(org.mockito.ArgumentMatchers.any())).thenReturn("token");

        var response = authService.register(request);

        assertEquals("token", response.token());
        assertEquals(AuthService.INITIAL_BALANCE, response.availableBalance());
        verify(userAccountRepository).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void registerShouldFailWhenEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest(
                "Ana Perez",
                "ana@btg.com",
                "3001234567",
                NotificationChannel.EMAIL,
                "StrongPass123");

        when(userAccountRepository.findByEmail("ana@btg.com"))
                .thenReturn(Optional.of(com.app.BtgFund.domain.UserAccount.builder().build()));

        assertThrows(BusinessException.class, () -> authService.register(request));
    }
}
