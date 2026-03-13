package com.app.BtgFund.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.app.BtgFund.domain.NotificationChannel;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RegisterRequest", description = "Datos para registrar un nuevo usuario")
public record RegisterRequest(
        @Schema(description = "Nombre completo", example = "Ana Perez")
        @NotBlank String fullName,
        @Schema(description = "Correo electronico", example = "ana@btg.com")
        @NotBlank @Email String email,
        @Schema(description = "Telefono de 10 digitos", example = "3001234567")
        @NotBlank @Pattern(regexp = "^[0-9]{10}$", message = "Phone must have 10 digits") String phone,
        @Schema(description = "Canal preferido de notificacion", example = "EMAIL")
        NotificationChannel preferredNotificationChannel,
        @Schema(description = "Contrasena del usuario", example = "StrongPass123")
        @NotBlank @Size(min = 8, max = 60) String password) {
}
