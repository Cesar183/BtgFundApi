package com.app.BtgFund.api.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.app.BtgFund.domain.RoleName;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AuthResponse", description = "Respuesta de autenticacion")
public record AuthResponse(
        @Schema(description = "JWT de acceso", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token,
        @Schema(description = "Tipo de token", example = "Bearer")
        String tokenType,
        @Schema(description = "Correo del usuario autenticado", example = "ana@btg.com")
        String email,
        @Schema(description = "Roles asignados")
        Set<RoleName> roles,
        @Schema(description = "Saldo disponible actual", example = "500000")
        BigDecimal availableBalance) {
}
