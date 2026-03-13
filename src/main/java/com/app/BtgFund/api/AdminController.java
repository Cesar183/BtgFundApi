package com.app.BtgFund.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.BtgFund.api.dto.TransactionResponse;
import com.app.BtgFund.service.SubscriptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Operaciones administrativas")
/**
 * Administrative HTTP endpoints.
 */
public class AdminController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/users/{userId}/transactions")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Historial por usuario", description = "Consulta historial de transacciones por userId (solo rol ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial encontrado", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionResponse.class)))),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    /**
     * Returns transaction history for a specific user (ADMIN only).
     */
    public List<TransactionResponse> historyByUser(@PathVariable String userId) {
        return subscriptionService.getTransactionHistory(userId);
    }
}
