package com.app.BtgFund.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.BtgFund.api.dto.TransactionResponse;
import com.app.BtgFund.security.AuthenticatedUserFacade;
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
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Historial transaccional de fondos")
/**
 * HTTP endpoints for transaction history.
 */
public class TransactionController {

    private final SubscriptionService subscriptionService;
    private final AuthenticatedUserFacade authenticatedUserFacade;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Consultar historial", description = "Retorna historial de aperturas y cancelaciones del usuario autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial encontrado", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionResponse.class)))),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    /**
     * Returns transaction history for current authenticated user.
     */
    public List<TransactionResponse> history() {
        return subscriptionService.getTransactionHistory(authenticatedUserFacade.currentUserId());
    }
}
