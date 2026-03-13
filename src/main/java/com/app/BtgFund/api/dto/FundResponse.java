package com.app.BtgFund.api.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FundResponse", description = "Informacion de un fondo")
public record FundResponse(
        @Schema(description = "ID del fondo", example = "1")
        String id,
        @Schema(description = "Nombre del fondo", example = "FPV_BTG_PACTUAL_RECAUDADORA")
        String name,
        @Schema(description = "Monto minimo de vinculacion", example = "75000")
        BigDecimal minimumAmount,
        @Schema(description = "Categoria del fondo", example = "FPV")
        String category) {
}
