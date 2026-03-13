package com.app.BtgFund.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.app.BtgFund.domain.SubscriptionStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SubscriptionResponse", description = "Respuesta de suscripcion")
public record SubscriptionResponse(
        @Schema(description = "ID de la suscripcion", example = "67d2b7c733a54f73a22a9f0a")
        String id,
        @Schema(description = "ID del fondo", example = "1")
        String fundId,
        @Schema(description = "Nombre del fondo", example = "FPV_BTG_PACTUAL_RECAUDADORA")
        String fundName,
        @Schema(description = "Valor vinculado", example = "75000")
        BigDecimal linkedAmount,
        @Schema(description = "Estado de la suscripcion", example = "ACTIVE")
        SubscriptionStatus status,
        @Schema(description = "Fecha de creacion", example = "2026-03-12T00:00:00Z")
        Instant createdAt,
        @Schema(description = "Fecha de cancelacion", example = "2026-03-12T01:00:00Z")
        Instant canceledAt) {
}
