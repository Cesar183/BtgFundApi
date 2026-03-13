package com.app.BtgFund.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.app.BtgFund.domain.NotificationChannel;
import com.app.BtgFund.domain.TransactionType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TransactionResponse", description = "Movimiento de apertura o cancelacion")
public record TransactionResponse(
        @Schema(description = "ID unico de transaccion", example = "a8454a58-b52d-4ca0-9f81-e95fbce44f47")
        String id,
        @Schema(description = "ID del fondo", example = "1")
        String fundId,
        @Schema(description = "Nombre del fondo", example = "FPV_BTG_PACTUAL_RECAUDADORA")
        String fundName,
        @Schema(description = "Tipo de transaccion", example = "OPEN")
        TransactionType type,
        @Schema(description = "Monto de la transaccion", example = "75000")
        BigDecimal amount,
        @Schema(description = "Fecha del movimiento", example = "2026-03-12T00:00:00Z")
        Instant createdAt,
        @Schema(description = "Canal de notificacion aplicado", example = "EMAIL")
        NotificationChannel notificationChannel) {
}
