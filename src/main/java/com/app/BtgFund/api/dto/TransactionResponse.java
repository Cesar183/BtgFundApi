package com.app.BtgFund.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.app.BtgFund.domain.NotificationChannel;
import com.app.BtgFund.domain.TransactionType;

public record TransactionResponse(
        String id,
        String fundId,
        String fundName,
        TransactionType type,
        BigDecimal amount,
        Instant createdAt,
        NotificationChannel notificationChannel) {
}
