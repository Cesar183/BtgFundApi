package com.app.BtgFund.domain;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("transactions")
public class FundTransaction {

    @Id
    private String id;
    private String userId;
    private String fundId;
    private String fundName;
    private TransactionType type;
    private BigDecimal amount;
    private Instant createdAt;
    private NotificationChannel notificationChannel;
}
