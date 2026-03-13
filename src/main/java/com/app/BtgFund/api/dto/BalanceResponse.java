package com.app.BtgFund.api.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "BalanceResponse", description = "Saldo disponible del usuario")
public record BalanceResponse(BigDecimal availableBalance) {
}
