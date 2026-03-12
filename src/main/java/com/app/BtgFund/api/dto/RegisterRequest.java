package com.app.BtgFund.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.app.BtgFund.domain.NotificationChannel;

public record RegisterRequest(
        @NotBlank String fullName,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^[0-9]{10}$", message = "Phone must have 10 digits") String phone,
        NotificationChannel preferredNotificationChannel,
        @NotBlank @Size(min = 8, max = 60) String password) {
}
