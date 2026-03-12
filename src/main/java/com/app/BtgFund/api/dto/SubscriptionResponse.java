package com.app.BtgFund.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.app.BtgFund.domain.SubscriptionStatus;

public record SubscriptionResponse(
        String id,
        String fundId,
        String fundName,
        BigDecimal linkedAmount,
        SubscriptionStatus status,
        Instant createdAt,
        Instant canceledAt) {
}
