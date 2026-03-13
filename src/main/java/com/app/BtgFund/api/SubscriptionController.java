package com.app.BtgFund.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.BtgFund.api.dto.SubscriptionResponse;
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
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscriptions", description = "Apertura, cancelacion y consulta de suscripciones")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final AuthenticatedUserFacade authenticatedUserFacade;

    @PostMapping("/{fundId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Suscribirse a fondo", description = "Crea una suscripcion activa al fondo indicado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Suscripcion creada", content = @Content(schema = @Schema(implementation = SubscriptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Regla de negocio incumplida"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public SubscriptionResponse subscribe(@PathVariable String fundId) {
        return subscriptionService.subscribe(authenticatedUserFacade.currentUserId(), fundId);
    }

    @DeleteMapping("/{subscriptionId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Cancelar suscripcion", description = "Cancela una suscripcion activa y retorna saldo al usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Suscripcion cancelada", content = @Content(schema = @Schema(implementation = SubscriptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Suscripcion no encontrada"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public SubscriptionResponse cancel(@PathVariable String subscriptionId) {
        return subscriptionService.cancel(authenticatedUserFacade.currentUserId(), subscriptionId);
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Listar suscripciones activas", description = "Retorna suscripciones activas del usuario autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado activo", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SubscriptionResponse.class)))),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public List<SubscriptionResponse> activeSubscriptions() {
        return subscriptionService.getActiveSubscriptions(authenticatedUserFacade.currentUserId());
    }
}
