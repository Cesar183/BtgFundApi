package com.app.BtgFund.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.BtgFund.api.dto.SubscriptionResponse;
import com.app.BtgFund.api.dto.TransactionResponse;
import com.app.BtgFund.common.BusinessException;
import com.app.BtgFund.common.ResourceNotFoundException;
import com.app.BtgFund.domain.FundTransaction;
import com.app.BtgFund.domain.Subscription;
import com.app.BtgFund.domain.SubscriptionStatus;
import com.app.BtgFund.domain.TransactionType;
import com.app.BtgFund.repository.FundRepository;
import com.app.BtgFund.repository.FundTransactionRepository;
import com.app.BtgFund.repository.SubscriptionRepository;
import com.app.BtgFund.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final FundRepository fundRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final FundTransactionRepository fundTransactionRepository;
    private final UserAccountRepository userAccountRepository;
    private final NotificationDispatcher notificationDispatcher;

    public SubscriptionResponse subscribe(String userId, String fundId) {
        var user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var fund = fundRepository.findById(fundId)
                .orElseThrow(() -> new ResourceNotFoundException("Fund not found"));

        subscriptionRepository.findByUserIdAndFundIdAndStatus(userId, fundId, SubscriptionStatus.ACTIVE)
                .ifPresent(existing -> {
                    throw new BusinessException("You already have an active subscription for this fund");
                });

        if (user.getAvailableBalance().compareTo(fund.getMinimumAmount()) < 0) {
            throw new BusinessException("No tiene saldo disponible para vincularse al fondo " + fund.getName());
        }

        user.setAvailableBalance(user.getAvailableBalance().subtract(fund.getMinimumAmount()));
        userAccountRepository.save(user);

        Subscription subscription = Subscription.builder()
                .userId(user.getId())
                .fundId(fund.getId())
                .fundName(fund.getName())
                .linkedAmount(fund.getMinimumAmount())
                .status(SubscriptionStatus.ACTIVE)
                .createdAt(Instant.now())
                .build();

        Subscription savedSubscription = subscriptionRepository.save(subscription);

        FundTransaction transaction = FundTransaction.builder()
                .id(UUID.randomUUID().toString())
                .userId(user.getId())
                .fundId(fund.getId())
                .fundName(fund.getName())
                .type(TransactionType.OPEN)
                .amount(fund.getMinimumAmount())
                .createdAt(Instant.now())
                .notificationChannel(user.getPreferredNotificationChannel())
                .build();

        fundTransactionRepository.save(transaction);
        notificationDispatcher.send(user,
                "Suscripcion exitosa al fondo " + fund.getName() + ". Transaccion: " + transaction.getId());

        return toSubscriptionResponse(savedSubscription);
    }

    public SubscriptionResponse cancel(String userId, String subscriptionId) {
        var user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Subscription subscription = subscriptionRepository.findByIdAndUserId(subscriptionId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        if (subscription.getStatus() == SubscriptionStatus.CANCELED) {
            throw new BusinessException("Subscription is already canceled");
        }

        subscription.setStatus(SubscriptionStatus.CANCELED);
        subscription.setCanceledAt(Instant.now());
        subscriptionRepository.save(subscription);

        user.setAvailableBalance(user.getAvailableBalance().add(subscription.getLinkedAmount()));
        userAccountRepository.save(user);

        FundTransaction transaction = FundTransaction.builder()
                .id(UUID.randomUUID().toString())
                .userId(user.getId())
                .fundId(subscription.getFundId())
                .fundName(subscription.getFundName())
                .type(TransactionType.CANCEL)
                .amount(subscription.getLinkedAmount())
                .createdAt(Instant.now())
                .notificationChannel(user.getPreferredNotificationChannel())
                .build();

        fundTransactionRepository.save(transaction);

        return toSubscriptionResponse(subscription);
    }

    public List<TransactionResponse> getTransactionHistory(String userId) {
        return fundTransactionRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(tx -> new TransactionResponse(
                        tx.getId(),
                        tx.getFundId(),
                        tx.getFundName(),
                        tx.getType(),
                        tx.getAmount(),
                        tx.getCreatedAt(),
                        tx.getNotificationChannel()))
                .toList();
    }

    public List<SubscriptionResponse> getActiveSubscriptions(String userId) {
        return subscriptionRepository.findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE).stream()
                .map(this::toSubscriptionResponse)
                .toList();
    }

    private SubscriptionResponse toSubscriptionResponse(Subscription subscription) {
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getFundId(),
                subscription.getFundName(),
                subscription.getLinkedAmount(),
                subscription.getStatus(),
                subscription.getCreatedAt(),
                subscription.getCanceledAt());
    }
}
