package com.app.BtgFund.api.dto;

import java.math.BigDecimal;

public record FundResponse(
        String id,
        String name,
        BigDecimal minimumAmount,
        String category) {
}
