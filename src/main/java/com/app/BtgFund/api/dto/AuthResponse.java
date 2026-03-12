package com.app.BtgFund.api.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.app.BtgFund.domain.RoleName;

public record AuthResponse(
        String token,
        String tokenType,
        String email,
        Set<RoleName> roles,
        BigDecimal availableBalance) {
}
