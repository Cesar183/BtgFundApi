package com.app.BtgFund.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
@Document("users")
public class UserAccount {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String fullName;
    private String phone;
    private String passwordHash;
    private NotificationChannel preferredNotificationChannel;
    private BigDecimal availableBalance;
    private Set<RoleName> roles;
    private Instant createdAt;
}
