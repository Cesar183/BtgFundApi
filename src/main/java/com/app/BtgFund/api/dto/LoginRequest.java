package com.app.BtgFund.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginRequest", description = "Credenciales para iniciar sesion")
public record LoginRequest(
        @Schema(description = "Correo electronico", example = "ana@btg.com")
        @NotBlank @Email String email,
        @Schema(description = "Contrasena", example = "StrongPass123")
        @NotBlank String password) {
}
