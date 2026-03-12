package com.app.BtgFund.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.BtgFund.common.BusinessException;
import com.app.BtgFund.domain.Fund;
import com.app.BtgFund.domain.NotificationChannel;
import com.app.BtgFund.domain.Subscription;
import com.app.BtgFund.domain.SubscriptionStatus;
import com.app.BtgFund.domain.UserAccount;
import com.app.BtgFund.repository.FundRepository;
import com.app.BtgFund.repository.FundTransactionRepository;
import com.app.BtgFund.repository.SubscriptionRepository;
import com.app.BtgFund.repository.UserAccountRepository;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private FundRepository fundRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private FundTransactionRepository fundTransactionRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private NotificationDispatcher notificationDispatcher;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private UserAccount user;
    private Fund fund;

    @BeforeEach
    void setUp() {
        user = UserAccount.builder()
                .id("user-1")
                .email("test@btg.com")
                .preferredNotificationChannel(NotificationChannel.EMAIL)
                .availableBalance(new BigDecimal("500000"))
                .build();

        fund = Fund.builder()
                .id("1")
                .name("FPV_BTG_PACTUAL_RECAUDADORA")
                .minimumAmount(new BigDecimal("75000"))
                .category("FPV")
                .build();
    }

    @Test
    void subscribeShouldDiscountBalanceAndCreateTransaction() {
        when(userAccountRepository.findById("user-1")).thenReturn(Optional.of(user));
        when(fundRepository.findById("1")).thenReturn(Optional.of(fund));
        when(subscriptionRepository.findByUserIdAndFundIdAndStatus("user-1", "1", SubscriptionStatus.ACTIVE))
                .thenReturn(Optional.empty());
        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> {
            Subscription s = invocation.getArgument(0);
            s.setId("sub-1");
            return s;
        });

        var response = subscriptionService.subscribe("user-1", "1");

        assertEquals("sub-1", response.id());
        assertEquals(new BigDecimal("425000"), user.getAvailableBalance());
        verify(fundTransactionRepository).save(any());
        verify(notificationDispatcher).send(any(), any());
    }

    @Test
    void subscribeShouldFailWhenNoBalance() {
        user.setAvailableBalance(new BigDecimal("1000"));

        when(userAccountRepository.findById("user-1")).thenReturn(Optional.of(user));
        when(fundRepository.findById("1")).thenReturn(Optional.of(fund));
        when(subscriptionRepository.findByUserIdAndFundIdAndStatus("user-1", "1", SubscriptionStatus.ACTIVE))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> subscriptionService.subscribe("user-1", "1"));

        assertEquals("No tiene saldo disponible para vincularse al fondo FPV_BTG_PACTUAL_RECAUDADORA",
                exception.getMessage());
    }

    @Test
    void cancelShouldReturnBalance() {
        Subscription subscription = Subscription.builder()
                .id("sub-1")
                .userId("user-1")
                .fundId("1")
                .fundName("FPV_BTG_PACTUAL_RECAUDADORA")
                .linkedAmount(new BigDecimal("75000"))
                .status(SubscriptionStatus.ACTIVE)
                .createdAt(Instant.now())
                .build();

        user.setAvailableBalance(new BigDecimal("425000"));

        when(userAccountRepository.findById("user-1")).thenReturn(Optional.of(user));
        when(subscriptionRepository.findByIdAndUserId("sub-1", "user-1")).thenReturn(Optional.of(subscription));

        var response = subscriptionService.cancel("user-1", "sub-1");

        assertEquals(SubscriptionStatus.CANCELED, response.status());
        assertEquals(new BigDecimal("500000"), user.getAvailableBalance());
        verify(fundTransactionRepository).save(any());
    }
}
